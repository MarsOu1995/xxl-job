package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobRegistry;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * XxlJobRegistryBeetDao
 *
 * @author Mars
 * @date 2019/10/25
 */
public interface XxlJobRegistryDao extends BaseMapper<XxlJobRegistry> {
    default List<Long> findDead(int timeout) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,-timeout);
        return createLambdaQuery()
                .andLess(XxlJobRegistry::getUpdateTime, calendar.getTime())
                .select(Long.class, "id");
    }

    default int removeDead(List<Long> ids) {
        return createLambdaQuery().andIn(XxlJobRegistry::getId, ids).delete();
    }

    default List<XxlJobRegistry> findAll(int timeout) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,-timeout);
        return createLambdaQuery().andGreat(XxlJobRegistry::getUpdateTime, calendar.getTime()).select();
    }

    default int registryUpdate(String registryGroup, String registryKey, String registryValue) {

        return createLambdaQuery().andEq(XxlJobRegistry::getRegistryGroup, registryGroup)
                .andEq(XxlJobRegistry::getRegistryKey, registryKey)
                .andEq(XxlJobRegistry::getRegistryValue, registryValue)
                .updateSelective(new XxlJobRegistry());
    }

    default int registrySave(String registryGroup, String registryKey, String registryValue) {
        XxlJobRegistry xxlJobRegistry = new XxlJobRegistry();
        xxlJobRegistry.setRegistryGroup(registryGroup);
        xxlJobRegistry.setRegistryKey(registryKey);
        xxlJobRegistry.setRegistryValue(registryValue);
        return createLambdaQuery().insert(xxlJobRegistry);
    }

    default int registryDelete(String registryGroup, String registryKey, String registryValue) {
        return createLambdaQuery().andEq(XxlJobRegistry::getRegistryGroup, registryGroup)
                .andEq(XxlJobRegistry::getRegistryKey, registryKey)
                .andEq(XxlJobRegistry::getRegistryValue, registryValue)
                .delete();
    }





}

