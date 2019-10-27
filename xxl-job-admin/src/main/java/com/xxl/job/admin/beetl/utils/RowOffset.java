package com.xxl.job.admin.beetl.utils;

/**
 * RowOffset
 *
 * @author Mars
 * @date 2019/10/24
 */
public class RowOffset {

    public static long start(long offset, boolean isStartZero) {
        if (isStartZero) {
            return offset += 1;
        }
        return offset;
    }

    public static long start(long offset) {
        return offset += 1;
    }
}
