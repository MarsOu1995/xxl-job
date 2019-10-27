package com.xxl.job.admin.core.model;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.ColumnIgnore;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Table(name = "xxl_job_group")
@Getter
@Setter
public class XxlJobGroup {

    @AutoID
//    @AssignID("snowLong")
    @SeqID(name = "XXL_JOB_GROUP_SEQ")
    private Long id;
    private String appName;
    private String title;
    @Column(name = "sort")
    private Integer order;
    private Integer addressType;        // 执行器地址类型：0=自动注册、1=手动录入
    private String addressList;     // 执行器地址列表，多地址逗号分隔(手动录入)

    // registry list
    @ColumnIgnore(insert = true,update = true)
    private List<String> registryList;  // 执行器地址列表(系统注册)

    public List<String> getRegistryList() {
        if (addressList!=null && addressList.trim().length()>0) {
            registryList = new ArrayList<String>(Arrays.asList(addressList.split(",")));
        }
        return registryList;
    }


}
