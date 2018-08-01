package com.kingyee.cats.common.security;

import com.kingyee.cats.db.CmAdminUser;

import java.io.Serializable;

public class AdminUserModel implements Serializable{
	private Long id;
	private String name;
	private String showName;
	private Long role;

	private CmAdminUser user;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowName() { return showName; }
	public void setShowName(String showName) { this.showName = showName; }
	public Long getRole() { return role; }
	public void setRole(Long role) { this.role = role; }

    public CmAdminUser getUser() {
        return user;
    }

    public void setUser(CmAdminUser user) {
        this.user = user;
    }
}
