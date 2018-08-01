package com.kingyee.fuxi.security.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractAuthRole entity provides the base persistence definition of the
 * AuthRole entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractAuthRole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long aroId;
	private String aroName;
	private String aroNameEn;

	// Constructors

	/** default constructor */
	public AbstractAuthRole() {
	}

	/** full constructor */
	public AbstractAuthRole(String aroName, String aroNameEn) {
		this.aroName = aroName;
		this.aroNameEn = aroNameEn;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "aro_id", unique = true, nullable = false)
	public Long getAroId() {
		return this.aroId;
	}

	public void setAroId(Long aroId) {
		this.aroId = aroId;
	}

	@Column(name = "aro_name", length = 128)
	public String getAroName() {
		return this.aroName;
	}

	public void setAroName(String aroName) {
		this.aroName = aroName;
	}

	@Column(name = "aro_name_en", length = 128)
	public String getAroNameEn() {
		return this.aroNameEn;
	}

	public void setAroNameEn(String aroNameEn) {
		this.aroNameEn = aroNameEn;
	}

}