package com.xxl.job.admin.core.model;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * xxl-job log, used to track trigger process
 * @author xuxueli  2015-12-19 23:19:09
 */
@Table(name = "xxl_job_log")
@Getter
@Setter
public class XxlJobLog {

	@AutoID
//	@AssignID("snowLong")
	@SeqID(name = "XXL_JOB_LOG_SEQ")
	private Long id;
	
	// job info
	private Long jobGroup;
	private Long jobId;

	// execute info
	private String executorAddress;
	private String executorHandler;
	private String executorParam;
	private String executorShardingParam;
	private int executorFailRetryCount;
	
	// trigger info
	private Date triggerTime;
	private int triggerCode;
	private String triggerMsg;
	
	// handle info
	private Date handleTime;
	private int handleCode;
	private String handleMsg;

	// alarm info
	private int alarmStatus;

}
