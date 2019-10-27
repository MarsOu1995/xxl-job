package com.xxl.job.admin.core.model.result;

import java.io.Serializable;

/**
 * TriggerCountByDay
 *
 * @author Mars
 * @date 2019/10/25
 */
public class TriggerCountByDay implements Serializable {
    private String triggerDay;
    private Integer triggerDayCount;
    private Integer triggerDayCountRunning;
    private Integer triggerDayCountSuc;

    public String getTriggerDay() {
        return triggerDay;
    }

    public void setTriggerDay(String triggerDay) {
        this.triggerDay = triggerDay;
    }

    public Integer getTriggerDayCount() {
        return triggerDayCount;
    }

    public void setTriggerDayCount(Integer triggerDayCount) {
        this.triggerDayCount = triggerDayCount;
    }

    public Integer getTriggerDayCountRunning() {
        return triggerDayCountRunning;
    }

    public void setTriggerDayCountRunning(Integer triggerDayCountRunning) {
        this.triggerDayCountRunning = triggerDayCountRunning;
    }

    public Integer getTriggerDayCountSuc() {
        return triggerDayCountSuc;
    }

    public void setTriggerDayCountSuc(Integer triggerDayCountSuc) {
        this.triggerDayCountSuc = triggerDayCountSuc;
    }
}
