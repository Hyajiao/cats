package com.kingyee.fuxi.security.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AuthResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth_resource")
public class AuthResource extends AbstractAuthResource implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AuthResource() {
	}

	/** full constructor */
	public AuthResource(Long arModule, String arPermission, String arName,
			String arDescription, String arUrl) {
		super(arModule, arPermission, arName, arDescription, arUrl);
	}

}
