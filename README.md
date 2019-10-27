1.快速切换开始（mysql/oracle）
======

#### 1.1 application.properties

```properties
### 将此处改为使用的驱动
spring.datasource.driver-class-name=

### beetlsql
#此处选择切换的数据库类型 MysqlStyle/OracleStyle
beetlsql.dbStyle=org.beetl.sql.core.db.OracleStyle
#是否打印sql日志和实时刷新sql文件
beetl-beetlsql.dev=true
#sql文件所在位置
beetlsql.sqlPath=/beetl-sql
```


2.改造说明
======

+ jdk改为1.8，现在只支持1.8以上，因为用到了函数式变成
+ 去除Mybatis作为持久层框架，换成Beetlsql
+ 连接池使用HirkariCp
+ 兼容mysql和oracle，已测试，切换后功能无问题
+ 所有ID改为Long类型
+ 大部分sql改为orm自适应

3.SQL兼容
=====

因为有些sql用orm框架不是太好写，所以要用到模版。
sql模版存放在```beetlsql.sqlPath=/beetl-sql```下

```java
@SqlResource("xxlJobLog")
public interface XxlJobLogDao extends BaseMapper<XxlJobLog> {
    int clearLog(@Param("jobGroup") long jobGroup,
				 @Param("jobId") long jobId,
				 @Param("clearBeforeTime") Date clearBeforeTime,
				 @Param("clearBeforeNum") int clearBeforeNum);
}

```
@sqlResource中的值就是指向resources/beetl-sql/下的xxlJobLig.md
接口法与sql.md中的标题名一致,如下clearLog：

```markdown
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
                @if(db.dbType() == "mysql"){
                    #use("mysqlCleanLog")#
                @}
                @if(db.dbType() == "oracle"){
                    #use("oracleCleanLog")#
                @}
                
            )
        @}
        
mysqlCleanLog
===
* mysql兼容片段
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
* oracle兼容片段
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
```
此sql目前只做了mysql和oracle的兼容，根据#db.dbType()#获取当前数据库类型，然后#use("XXX")#选择对应sql片段拼接。
若是需要再做其他数据库兼容，只需改动/beetl-sql下2个文件，检查其中的sql是否满足所换的数据库既可。
（需要改动的sql不会很多，就两条:xxlJobLog.clearLog和xxlJobLogglue.removeOld）
