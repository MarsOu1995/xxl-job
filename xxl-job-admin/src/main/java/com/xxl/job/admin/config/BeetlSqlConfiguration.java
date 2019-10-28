package com.xxl.job.admin.config;

import com.xxl.job.admin.beetl.dbtype.CurrentDbType;
import com.xxl.job.admin.beetl.utils.SnowFlake;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariDataSource dataSource(DataSourceProperties properties,Environment env) {
        HikariDataSource dataSource =properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        CurrentDbType.setDbType(env.getProperty("beetlsql.dbStyle"));
        return dataSource;
    }

}
