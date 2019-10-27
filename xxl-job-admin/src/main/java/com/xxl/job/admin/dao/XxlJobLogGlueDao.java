package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobLogGlue;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * job log for glue
 * @author Mars
 * @date 2019/10/25
 */
@SqlResource("xxlJobLogglue")
public interface XxlJobLogGlueDao extends BaseMapper<XxlJobLogGlue> {

	default List<XxlJobLogGlue> findByJobId(long jobId) {
        return createLambdaQuery().andEq(XxlJobLogGlue::getJobId, jobId).desc(XxlJobLogGlue::getId).select();
    }

	int removeOld(@Param("jobId") long jobId, @Param("limit") int limit);

	default int deleteByJobId(long jobId) {
		return createLambdaQuery().andEq(XxlJobLogGlue::getJobId, jobId).delete();
	}
	
}
