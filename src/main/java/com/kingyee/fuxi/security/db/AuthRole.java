package com.kingyee.fuxi.security.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AuthRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth_role")
public class AuthRole extends AbstractAuthRole implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public AuthRole() {
	}

	/** full constructor */
	public AuthRole(String aroName, String aroNameEn) {
		super(aroName, aroNameEn);
	}

}
