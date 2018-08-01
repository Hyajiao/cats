package com.kingyee.fuxi.security.model;

import com.kingyee.fuxi.security.db.AuthResource;

/**
 * 功能：新增一个是否选中权限资源的字段
 * @author 周振平
 * @createTime May 5, 2011 11:06:20 AM
 */
public class RoleResourceBean extends AuthResource {

	private static final long serialVersionUID = 1994882535944425331L;
	
	/** 对应的角色是否拥有该权限资源 */
	private String checked;

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}
}
