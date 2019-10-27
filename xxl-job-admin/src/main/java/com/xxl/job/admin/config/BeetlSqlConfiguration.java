package com.xxl.job.admin.config;

import com.xxl.job.admin.beetl.dbtype.CurrentDbType;
import com.xxl.job.admin.beetl.utils.SnowFlake;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.function.Consumer;

/**
 * beetlsql配置
 *
 * @author Mars
 * @date 2019/10/23
 */
@Configuration
public class BeetlSqlConfiguration {

    @Bean(name="datasource")
    public DataSource datasource(Environment env) {
        //数据源
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("spring.datasource.url"));
        ds.setUsername(env.getProperty("spring.datasource.username"));
        ds.setPassword(env.getProperty("spring.datasource.password"));
        ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        //可选设置
        Convert convert = new Convert();
        convert.withNotNullLong(ds::setConnectionTimeout, env.getProperty("spring.datasource.hikari.connection-timeout"))
                .withNotNullInt(ds::setMinimumIdle, env.getProperty("spring.datasource.hikari.minimum-idle"))
                .withNotNullInt(ds::setMaximumPoolSize, env.getProperty("spring.datasource.hikari.maximum-pool-size"))
                .withNotNullLong(ds::setIdleTimeout, env.getProperty("spring.datasource.hikari.idle-timeout"))
                .withNotNullLong(ds::setMaxLifetime, env.getProperty("spring.datasource.hikari.max-lifetime"))
                .withNotNullBoolean(ds::setAutoCommit, env.getProperty("spring.datasource.hikari.auto-commit"));

        //设置当前数据库类型
        CurrentDbType.setDbType(env.getProperty("beetlsql.dbStyle"));
        return ds;
    }

    /**
     * 转换器
     */
    protected static class Convert {
        Convert withNotNullInt(Consumer<Integer> consumer, String value) {
            if (!StringUtils.isEmpty(value)) {
                consumer.accept(Integer.parseInt(value));
            }
            return this;
        }

        Convert withNotNullLong(Consumer<Long> consumer, String value) {
            if (!StringUtils.isEmpty(value)) {
                consumer.accept(Long.parseLong(value));
            }
            return this;
        }

        Convert withNotNullBoolean(Consumer<Boolean> consumer, String value) {
            if (!StringUtils.isEmpty(value)) {
                consumer.accept(Boolean.parseBoolean(value));
            }
            return this;
        }
    }




}
