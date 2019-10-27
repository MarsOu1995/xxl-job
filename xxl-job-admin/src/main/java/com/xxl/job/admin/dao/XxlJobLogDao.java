package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.core.model.result.TriggerCountByDay;
import com.xxl.job.admin.beetl.utils.CustomCondition;
import com.xxl.job.admin.beetl.utils.CustomQuery;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * job log
 * @author Mars
 * @date 2019/10/24
 */
@SqlResource("xxlJobLog")
public interface XxlJobLogDao extends BaseMapper<XxlJobLog> {

	PageQuery<XxlJobLog> pageList(PageQuery pageQuery,
								  @Param("jobGroup") long jobGroup,
								  @Param("jobId") long jobId,
								  @Param("triggerTimeStart") Date triggerTimeStart,
								  @Param("triggerTimeEnd") Date triggerTimeEnd,
								  @Param("logStatus") int logStatus);

	int updateTriggerInfo(XxlJobLog xxlJobLog);

	int updateHandleInfo(XxlJobLog xxlJobLog);

	default long triggerCountByHandleCode(int handleCode) {
		return createLambdaQuery().andEq(XxlJobLog::getHandleCode, CustomQuery.conditionNumber(handleCode, CustomCondition.GT, 0)).count();
	}

	int clearLog(@Param("jobGroup") long jobGroup,
				 @Param("jobId") long jobId,
				 @Param("clearBeforeTime") Date clearBeforeTime,
				 @Param("clearBeforeNum") int clearBeforeNum);

	List<TriggerCountByDay> triggerCountByDay(@Param("from") Date from,
											  @Param("to") Date to);

	List<Long> findFailJobLogIds(int pagesize);

	int updateAlarmStatus(@Param("logId") long logId,
								 @Param("oldAlarmStatus") int oldAlarmStatus,
								 @Param("newAlarmStatus") int newAlarmStatus);

}
