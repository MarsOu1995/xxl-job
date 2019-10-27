package com.xxl.job.admin.config;

import com.xxl.job.admin.beetl.utils.SnowFlake;
import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import org.beetl.sql.core.IDAutoGen;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局ID
 *
 * @author Mars
 * @date 2019/10/26
 */
@Component
@AutoConfigureBefore(XxlJobAdminConfig.class)
public class IdConfiguration implements InitializingBean {

    @Autowired
    private SQLManager sqlManager;

    @Value("${snowflake.centerId:1}")
    private long dataCenterId;

    @Value("${snowflake.machineId:1}")
    private long machineId;

    @Value("${snowflake.timestamp:-1}")
    private long timestamp;

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化全局ID
        SnowFlake.getInstance(dataCenterId, machineId, timestamp);
        IDAutoGen snowStr= new IDAutoGen() {

            private SnowFlake sf= SnowFlake.getInstance();
            @Override
            public Object nextID(String params) {
                return sf.nextId()+"";
            }
        };

        IDAutoGen snowLong= new IDAutoGen() {
            private SnowFlake sf= SnowFlake.getInstance();
            @Override
            public Object nextID(String params) {
                return sf.nextId();
            }
        };
        sqlManager.addIdAutonGen("snowStr", snowStr);
        sqlManager.addIdAutonGen("snowLong", snowLong);
    }
}
