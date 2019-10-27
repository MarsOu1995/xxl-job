pageList
===
* 分页查询
    select
        @pageTag(){
            #use("cols")#
        @}
    from xxl_job_log
    where #use("pageCondition")#
    
    @pageIgnoreTag(){
        ORDER BY trigger_time DESC
    @}



cols
===
    id,
    job_group,
    job_id,
    executor_address,
    executor_handler,
    executor_param,
    executor_sharding_param,
    executor_fail_retry_count,
    trigger_time,
    trigger_code,
    trigger_msg,
    handle_time,
    handle_code,
    handle_msg,
    alarm_status

pageCondition
===
    1=1
    @if( jobId == 0 && jobGroup > 0){
        AND job_group = #jobGroup#
    @}
    @if( jobId > 0){
        AND job_id = #jobId#
    @}
    @if( triggerTimeStart != null){
        AND trigger_time >= #triggerTimeStart#
    @}
    @if( triggerTimeEnd != null){
        AND trigger_time <= #triggerTimeEnd#
    @}
    @if( logStatus == 1){
        AND handle_code = 200
    @}
    @if( logStatus == 2){
        AND (trigger_code NOT IN (0, 200) OR handle_code NOT IN (0, 200))
    @}
    @if( logStatus == 3){
        AND trigger_code = 200
        AND handle_code = 0
    @}

updateTriggerInfo
===
* 更新触发执行日志

    UPDATE xxl_job_log
        SET
            trigger_time = #triggerTime#,
            trigger_code = #triggerCode#,
            trigger_msg = #triggerMsg#,
            executor_address = #executorAddress#,
            executor_handler =#executorHandler#,
            executor_param = #executorParam#,
            executor_sharding_param = #executorShardingParam#,
            executor_fail_retry_count = #executorFailRetryCount#
        WHERE  id = #id#

updateHandleInfo
===
* 更新Handler日志
    UPDATE xxl_job_log
		SET 
			handle_time = #handleTime#, 
			handle_code = #handleCode#,
			handle_msg = #handleMsg# 
		WHERE  id = #id#
		
	
clearLog
===
* 清除日志
    delete from xxl_job_log
        where 1=1
        @if(jobGroup > 0){
            AND job_group = #jobGroup#
        @}
        @if(!isEmpty(clearBeforeTime)){
            AND trigger_time <= #clearBeforeTime#
        @}
        @if(clearBeforeNum > 0){
            AND id NOT in(
                @if(db.type() == "mysql"){
                    #use("mysqlCleanLog")#
                @}
                @if(db.type() == "oracle"){
                    #use("oracleCleanLog")#
                @}
                
            )
        @}
        
mysqlCleanLog
===
    SELECT id from (
        SELECT id FROM xxl_job_log AS t
        where 1=1
        @if(jobGroup > 0){
            AND job_group = #jobGroup#
        @}
        @if(jobId > 0){
            AND t.job_id = #jobId#
        @}
        ORDER BY t.trigger_time desc
        LIMIT 0, #clearBeforeNum#
    ) t1
        
oracleCleanLog
===
    SELECT id FROM(
        SELECT id FROM(
            SELECT id FROM xxl_job_log AS t
            where 1=1
            @if(jobGroup > 0){
                AND job_group = #jobGroup#
            @}
            @if(jobId > 0){
                AND t.job_id = #jobId#
            @}
            ORDER BY t.trigger_time desc
        ) t1
        where  rownum <= #clearBeforeNum#
    ) t2

triggerCountByDay
===
* 统计
    SELECT
        triggerDay,
        COUNT(handle_code) triggerDayCount,
        SUM(CASE WHEN (trigger_code in (0, 200) and handle_code = 0) then 1 else 0 end) as triggerDayCountRunning,
        SUM(CASE WHEN handle_code = 200 then 1 else 0 end) as triggerDayCountSuc
    FROM (
        SELECT 
            @if(db.type()=='oracle'){
                #use("oracleDate")#
            @}else if(db.type()=='mysql'){
                #use("mysqlDate")#
            @}
            as triggerDay,handle_code,trigger_code
            FROM
            xxl_job_log
        WHERE trigger_time BETWEEN #from# and #to#
    ) t1
    GROUP BY triggerDay
    ORDER BY triggerDay

mysqlDate
===
    DATE_FORMAT(trigger_time,'%Y-%m-%d')
    
oracleDate
===
    to_char(trigger_time,'YYYY-MM-DD')

findFailJobLogIds
===
* 查找要告警的日志
    SELECT 
    #page("id")# 
    FROM xxl_job_log
    WHERE not (
        (trigger_code in (0, 200) and handle_code = 0)
        OR
        (handle_code = 200)
    )
    AND alarm_status = 0
    ORDER BY id ASC

updateAlarmStatus
===
* 更新告警状态
    UPDATE xxl_job_log
		SET
			alarm_status = #newAlarmStatus#
		WHERE id= #logId# AND alarm_status = #oldAlarmStatus#
	