package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobGroup;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * XxlJobGroupDao
 *
 * @author Mars
 * @date 2019/10/25
 */
public interface XxlJobGroupDao extends BaseMapper<XxlJobGroup> {

    default int save(XxlJobGroup xxlJobGroup) {
        return createLambdaQuery().insert(xxlJobGroup);
    }


    default List<XxlJobGroup> findAll() {
        return createLambdaQuery().asc(XxlJobGroup::getOrder).select();
    }

    default List<XxlJobGroup> findByAddressType(int addressType) {
        return createLambdaQuery().andEq(XxlJobGroup::getAddressType, addressType).asc(XxlJobGroup::getOrder).select();
    }




}
