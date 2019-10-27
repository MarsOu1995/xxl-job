package com.xxl.job.admin.beetl.dbtype;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * beetlsql 当前数据库类型
 *
 * @author Mars
 * @date 2019/10/24
 */
public class DbTypeFunction implements Function {
    @Override
    public Object call(Object[] objects, Context context) {
        DbType dbType = CurrentDbType.getDbType();
        return dbType.toString().toLowerCase();
    }
}
