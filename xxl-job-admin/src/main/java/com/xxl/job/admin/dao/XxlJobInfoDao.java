package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.beetl.utils.CustomCondition;
import com.xxl.job.admin.beetl.utils.CustomQuery;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;
import org.beetl.sql.core.query.Query;

import java.util.List;

/**
 * 任务信息
 *
 * @author Mars
 * @date 2019/10/23
 */
public interface XxlJobInfoDao extends BaseMapper<XxlJobInfo> {

    /**
     * 分页查询
     * @param pageQuery
     * @param jobGroup
     * @param triggerStatus
     * @param jobDesc
     * @param executorHandler
     * @param author
     * @return
     */
    default PageQuery<XxlJobInfo> pageQuery(PageQuery<XxlJobInfo> pageQuery, long jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {
        return createLambdaQuery().andEq(XxlJobInfo::getJobGroup, CustomQuery.conditionNumber(jobGroup, CustomCondition.GT, 0))
                .andEq(XxlJobInfo::getTriggerStatus, CustomQuery.conditionNumber(triggerStatus, CustomCondition.GTE, 0))
                .andLike(XxlJobInfo::getJobDesc, CustomQuery.filterLikeEmpty(jobDesc))
                .andLike(XxlJobInfo::getExecutorHandler, CustomQuery.filterLikeEmpty(executorHandler))
                .andLike(XxlJobInfo::getAuthor, CustomQuery.filterLikeEmpty(author))
                .page(pageQuery.getPageNumber(),pageQuery.getPageSize());
    }

    default long countByJobGroup(long jobGroup) {
        return createLambdaQuery().andEq(XxlJobInfo::getJobGroup, jobGroup)
                .count();

    }

    /**
     * 根据jobGroup获取任务信息
     * @param jobGroup
     * @return
     */
    default List<XxlJobInfo> getJobsByGroup(long jobGroup) {
        return createLambdaQuery().andEq(XxlJobInfo::getJobGroup, jobGroup).select();
    }


    default List<XxlJobInfo> scheduleJobQuery(long maxNextTime) {
        return createLambdaQuery().andEq(XxlJobInfo::getTriggerStatus, 1).andLessEq(XxlJobInfo::getTriggerNextTime,maxNextTime).select();
    }

    default int scheduleUpdate(XxlJobInfo xxlJobInfo) {
        return createLambdaQuery().andEq(XxlJobInfo::getId, xxlJobInfo.getId()).update(xxlJobInfo);

    }



}
