package com.xxl.job.admin.core.model;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.annotatoin.UpdateTime;

import java.util.Date;

/**
 * xxl-job log for glue, used to track job code process
 * @author xuxueli 2016-5-19 17:57:46
 */
@Table(name = "xxl_job_logglue")
@Getter
@Setter
public class XxlJobLogGlue {
	@AutoID
//	@AssignID("snowLong")
	@SeqID(name = "XXL_JOB_LOGGLUE_SEQ")
	private Long id;
	private Long jobId;				// 任务主键ID
	private String glueType;		// GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum
	private String glueSource;
	private String glueRemark;
	private Date addTime;
	@UpdateTime
	private Date updateTime;
}
