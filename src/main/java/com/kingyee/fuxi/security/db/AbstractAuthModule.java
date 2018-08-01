package com.kingyee.fuxi.security.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractAuthModule entity provides the base persistence definition of the
 * AuthModule entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractAuthModule implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long amId;
	private String amName;

	// Constructors

	/** default constructor */
	public AbstractAuthModule() {
	}

	/** full constructor */
	public AbstractAuthModule(String amName) {
		this.amName = amName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "am_id", unique = true, nullable = false)
	public Long getAmId() {
		return this.amId;
	}

	public void setAmId(Long amId) {
		this.amId = amId;
	}

	@Column(name = "am_name", length = 128)
	public String getAmName() {
		return this.amName;
	}

	public void setAmName(String amName) {
		this.amName = amName;
	}

}