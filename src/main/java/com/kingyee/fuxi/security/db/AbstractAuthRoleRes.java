package com.kingyee.fuxi.security.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractAuthRoleRes entity provides the base persistence definition of the
 * AuthRoleRes entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractAuthRoleRes implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long arrId;
	private Long arrRoleId;
	private Long arrModule;
	private Long arrRes;

	// Constructors

	/** default constructor */
	public AbstractAuthRoleRes() {
	}

	/** full constructor */
	public AbstractAuthRoleRes(Long arrRoleId, Long arrModule, Long arrRes) {
		this.arrRoleId = arrRoleId;
		this.arrModule = arrModule;
		this.arrRes = arrRes;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "arr_id", unique = true, nullable = false)
	public Long getArrId() {
		return this.arrId;
	}

	public void setArrId(Long arrId) {
		this.arrId = arrId;
	}

	@Column(name = "arr_role_id")
	public Long getArrRoleId() {
		return this.arrRoleId;
	}

	public void setArrRoleId(Long arrRoleId) {
		this.arrRoleId = arrRoleId;
	}

	@Column(name = "arr_module")
	public Long getArrModule() {
		return this.arrModule;
	}

	public void setArrModule(Long arrModule) {
		this.arrModule = arrModule;
	}

	@Column(name = "arr_res")
	public Long getArrRes() {
		return this.arrRes;
	}

	public void setArrRes(Long arrRes) {
		this.arrRes = arrRes;
	}

}