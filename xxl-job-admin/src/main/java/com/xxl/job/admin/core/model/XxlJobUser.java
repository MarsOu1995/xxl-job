package com.xxl.job.admin.core.model;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.annotatoin.UpdateIgnore;
import org.springframework.util.StringUtils;

/**
 * @author xuxueli 2019-05-04 16:43:12
 */
@Table(name = "xxl_job_user")
@Getter
@Setter
public class XxlJobUser {
	@AutoID
//	@AssignID("snowLong")
	@SeqID(name = "XXL_JOB_USER_SEQ")
	private Long id;
	@UpdateIgnore
	private String username;		// 账号
	private String password;		// 密码
	private Integer role;				// 角色：0-普通用户、1-管理员
	private String permission;	// 权限：执行器ID列表，多个逗号分割

	// plugin
	public boolean validPermission(long jobGroup){
		if (this.role == 1) {
			return true;
		} else {
			if (StringUtils.hasText(this.permission)) {
				for (String permissionItem : this.permission.split(",")) {
					if (String.valueOf(jobGroup).equals(permissionItem)) {
						return true;
					}
				}
			}
			return false;
		}

	}

}
