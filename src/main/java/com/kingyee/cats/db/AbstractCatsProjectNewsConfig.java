package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsProjectNewsConfig entity provides the base persistence definition
 * of the CatsProjectNewsConfig entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsProjectNewsConfig implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cpncId;
	private Long cpncCpId;
	private String cpncKeywords;
	private Long cpncCreateTime;
	private Long cpncCreateUserId;
	private String cpncCreateUserName;
	private Long cpncUpdateTime;
	private Long cpncUpdateUserId;
	private String cpncUpdateUserName;

	// Constructors

	/** default constructor */
	public AbstractCatsProjectNewsConfig() {
	}

	/** full constructor */
	public AbstractCatsProjectNewsConfig(Long cpncCpId, String cpncKeywords,
			Long cpncCreateTime, Long cpncCreateUserId,
			String cpncCreateUserName, Long cpncUpdateTime,
			Long cpncUpdateUserId, String cpncUpdateUserName) {
		this.cpncCpId = cpncCpId;
		this.cpncKeywords = cpncKeywords;
		this.cpncCreateTime = cpncCreateTime;
		this.cpncCreateUserId = cpncCreateUserId;
		this.cpncCreateUserName = cpncCreateUserName;
		this.cpncUpdateTime = cpncUpdateTime;
		this.cpncUpdateUserId = cpncUpdateUserId;
		this.cpncUpdateUserName = cpncUpdateUserName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cpnc_id", unique = true, nullable = false)
	public Long getCpncId() {
		return this.cpncId;
	}

	public void setCpncId(Long cpncId) {
		this.cpncId = cpncId;
	}

	@Column(name = "cpnc_cp_id")
	public Long getCpncCpId() {
		return this.cpncCpId;
	}

	public void setCpncCpId(Long cpncCpId) {
		this.cpncCpId = cpncCpId;
	}

	@Column(name = "cpnc_keywords", length = 512)
	public String getCpncKeywords() {
		return this.cpncKeywords;
	}

	public void setCpncKeywords(String cpncKeywords) {
		this.cpncKeywords = cpncKeywords;
	}

	@Column(name = "cpnc_create_time")
	public Long getCpncCreateTime() {
		return this.cpncCreateTime;
	}

	public void setCpncCreateTime(Long cpncCreateTime) {
		this.cpncCreateTime = cpncCreateTime;
	}

	@Column(name = "cpnc_create_user_id")
	public Long getCpncCreateUserId() {
		return this.cpncCreateUserId;
	}

	public void setCpncCreateUserId(Long cpncCreateUserId) {
		this.cpncCreateUserId = cpncCreateUserId;
	}

	@Column(name = "cpnc_create_user_name", length = 128)
	public String getCpncCreateUserName() {
		return this.cpncCreateUserName;
	}

	public void setCpncCreateUserName(String cpncCreateUserName) {
		this.cpncCreateUserName = cpncCreateUserName;
	}

	@Column(name = "cpnc_update_time")
	public Long getCpncUpdateTime() {
		return this.cpncUpdateTime;
	}

	public void setCpncUpdateTime(Long cpncUpdateTime) {
		this.cpncUpdateTime = cpncUpdateTime;
	}

	@Column(name = "cpnc_update_user_id")
	public Long getCpncUpdateUserId() {
		return this.cpncUpdateUserId;
	}

	public void setCpncUpdateUserId(Long cpncUpdateUserId) {
		this.cpncUpdateUserId = cpncUpdateUserId;
	}

	@Column(name = "cpnc_update_user_name", length = 128)
	public String getCpncUpdateUserName() {
		return this.cpncUpdateUserName;
	}

	public void setCpncUpdateUserName(String cpncUpdateUserName) {
		this.cpncUpdateUserName = cpncUpdateUserName;
	}

}