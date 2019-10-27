package com.xxl.job.admin.beetl.utils;

import org.beetl.sql.core.engine.PageQuery;

/**
 * 行数转页数
 *
 * @author Mars
 * @date 2019/10/24
 */
public class RowPageQuery<T> extends PageQuery<T> {
    public RowPageQuery(long offset, long pageSize) {
        this.pageNumber = (long) Math.floor(offset / pageSize) + 1;
        this.pageSize= pageSize;
    }
}
