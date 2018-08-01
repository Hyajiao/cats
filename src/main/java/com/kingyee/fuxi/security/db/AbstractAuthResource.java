package com.kingyee.fuxi.security.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractAuthResource entity provides the base persistence definition of the
 * AuthResource entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractAuthResource implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long arId;
	private Long arModule;
	private String arPermission;
	private String arName;
	private String arDescription;
	private String arUrl;

	// Constructors

	/** default constructor */
	public AbstractAuthResource() {
	}

	/** full constructor */
	public AbstractAuthResource(Long arModule, String arPermission,
			String arName, String arDescription, String arUrl) {
		this.arModule = arModule;
		this.arPermission = arPermission;
		this.arName = arName;
		this.arDescription = arDescription;
		this.arUrl = arUrl;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ar_id", unique = true, nullable = false)
	public Long getArId() {
		return this.arId;
	}

	public void setArId(Long arId) {
		this.arId = arId;
	}

	@Column(name = "ar_module")
	public Long getArModule() {
		return this.arModule;
	}

	public void setArModule(Long arModule) {
		this.arModule = arModule;
	}

	@Column(name = "ar_permission", length = 128)
	public String getArPermission() {
		return this.arPermission;
	}

	public void setArPermission(String arPermission) {
		this.arPermission = arPermission;
	}

	@Column(name = "ar_name", length = 128)
	public String getArName() {
		return this.arName;
	}

	public void setArName(String arName) {
		this.arName = arName;
	}

	@Column(name = "ar_description", length = 128)
	public String getArDescription() {
		return this.arDescription;
	}

	public void setArDescription(String arDescription) {
		this.arDescription = arDescription;
	}

	@Column(name = "ar_url", length = 128)
	public String getArUrl() {
		return this.arUrl;
	}

	public void setArUrl(String arUrl) {
		this.arUrl = arUrl;
	}

}