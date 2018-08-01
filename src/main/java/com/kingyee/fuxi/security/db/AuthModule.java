package com.kingyee.fuxi.security.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AuthModule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth_module")
public class AuthModule extends AbstractAuthModule implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AuthModule() {
	}

	/** full constructor */
	public AuthModule(String amName) {
		super(amName);
	}

}
