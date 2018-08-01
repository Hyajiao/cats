package com.kingyee.fuxi.security.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AuthRoleRes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth_role_res")
public class AuthRoleRes extends AbstractAuthRoleRes implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AuthRoleRes() {
	}

	/** full constructor */
	public AuthRoleRes(Long arrRoleId, Long arrModule, Long arrRes) {
		super(arrRoleId, arrModule, arrRes);
	}

}
