package com.xxl.job.admin.core.model;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.*;

import java.util.Date;

/**
 * Created by xuxueli on 16/9/30.
 */
@Table(name = "xxl_job_registry")
@Getter
@Setter
public class XxlJobRegistry {

    @AutoID
//    @AssignID("snowLong")
    @SeqID(name = "XXL_JOB_REGISTRY_SEQ")
    private Long id;
    @UpdateIgnore
    private String registryGroup;
    @UpdateIgnore
    private String registryKey;
    @UpdateIgnore
    private String registryValue;
    @UpdateTime
    private Date updateTime;
}
