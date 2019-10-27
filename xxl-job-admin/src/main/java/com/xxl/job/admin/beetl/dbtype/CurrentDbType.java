package com.xxl.job.admin.beetl.dbtype;

import org.beetl.sql.core.db.*;
import org.springframework.util.StringUtils;

/**
 * 当前系统使用的数据库类型
 *
 * @author Mars
 * @date 2019/10/24
 */
public class CurrentDbType {

    private static DbType dbType = DbType.MYSQL;

    public static DbType getDbType() {
        return dbType;
    }

    public static void setDbType(String type) {
        if (StringUtils.isEmpty(type)) {
            return;
        }

        Class<?> clazz = null;
        try {
            clazz = Class.forName(type);
        } catch (ClassNotFoundException e) {
        }

        if (clazz == MySqlStyle.class) {
            CurrentDbType.dbType = DbType.MYSQL;
        } else if (clazz == OracleStyle.class) {
            CurrentDbType.dbType = DbType.ORACLE;
        } else if (clazz == PostgresStyle.class) {
            CurrentDbType.dbType = DbType.POSTGRES;
        } else if (clazz == H2Style.class) {
            CurrentDbType.dbType = DbType.H2;
        } else if (clazz == SqlServerStyle.class) {
            CurrentDbType.dbType = DbType.SQLSERVER;
        } else if (clazz == SQLiteStyle.class) {
            CurrentDbType.dbType = DbType.SQLSERVER;
        }

    }
}
