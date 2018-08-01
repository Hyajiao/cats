package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsProjectEnterpriseUserLink entity provides the base persistence
 * definition of the CatsProjectEnterpriseUserLink entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsProjectEnterpriseUserLink implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cpeulId;
	private Long cpeulCpId;
	private Long cpeulCauId;
	private Long cpeulCreateTime;
	private Long cpeulCreateUserId;
	private String cpeulCreateUserName;
	private Long cpeulUpdateTime;
	private Long cpeulUpdateUserId;
	private String cpeulUpdateUserName;

	// Constructors

	/** default constructor */
	public AbstractCatsProjectEnterpriseUserLink() {
	}

	/** full constructor */
	public AbstractCatsProjectEnterpriseUserLink(Long cpeulCpId,
			Long cpeulCauId, Long cpeulCreateTime, Long cpeulCreateUserId,
			String cpeulCreateUserName, Long cpeulUpdateTime,
			Long cpeulUpdateUserId, String cpeulUpdateUserName) {
		this.cpeulCpId = cpeulCpId;
		this.cpeulCauId = cpeulCauId;
		this.cpeulCreateTime = cpeulCreateTime;
		this.cpeulCreateUserId = cpeulCreateUserId;
		this.cpeulCreateUserName = cpeulCreateUserName;
		this.cpeulUpdateTime = cpeulUpdateTime;
		this.cpeulUpdateUserId = cpeulUpdateUserId;
		this.cpeulUpdateUserName = cpeulUpdateUserName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cpeul_id", unique = true, nullable = false)
	public Long getCpeulId() {
		return this.cpeulId;
	}

	public void setCpeulId(Long cpeulId) {
		this.cpeulId = cpeulId;
	}

	@Column(name = "cpeul_cp_id")
	public Long getCpeulCpId() {
		return this.cpeulCpId;
	}

	public void setCpeulCpId(Long cpeulCpId) {
		this.cpeulCpId = cpeulCpId;
	}

	@Column(name = "cpeul_cau_id")
	public Long getCpeulCauId() {
		return this.cpeulCauId;
	}

	public void setCpeulCauId(Long cpeulCauId) {
		this.cpeulCauId = cpeulCauId;
	}

	@Column(name = "cpeul_create_time")
	public Long getCpeulCreateTime() {
		return this.cpeulCreateTime;
	}

	public void setCpeulCreateTime(Long cpeulCreateTime) {
		this.cpeulCreateTime = cpeulCreateTime;
	}

	@Column(name = "cpeul_create_user_id")
	public Long getCpeulCreateUserId() {
		return this.cpeulCreateUserId;
	}

	public void setCpeulCreateUserId(Long cpeulCreateUserId) {
		this.cpeulCreateUserId = cpeulCreateUserId;
	}

	@Column(name = "cpeul_create_user_name", length = 128)
	public String getCpeulCreateUserName() {
		return this.cpeulCreateUserName;
	}

	public void setCpeulCreateUserName(String cpeulCreateUserName) {
		this.cpeulCreateUserName = cpeulCreateUserName;
	}

	@Column(name = "cpeul_update_time")
	public Long getCpeulUpdateTime() {
		return this.cpeulUpdateTime;
	}

	public void setCpeulUpdateTime(Long cpeulUpdateTime) {
		this.cpeulUpdateTime = cpeulUpdateTime;
	}

	@Column(name = "cpeul_update_user_id")
	public Long getCpeulUpdateUserId() {
		return this.cpeulUpdateUserId;
	}

	public void setCpeulUpdateUserId(Long cpeulUpdateUserId) {
		this.cpeulUpdateUserId = cpeulUpdateUserId;
	}

	@Column(name = "cpeul_update_user_name", length = 128)
	public String getCpeulUpdateUserName() {
		return this.cpeulUpdateUserName;
	}

	public void setCpeulUpdateUserName(String cpeulUpdateUserName) {
		this.cpeulUpdateUserName = cpeulUpdateUserName;
	}

}