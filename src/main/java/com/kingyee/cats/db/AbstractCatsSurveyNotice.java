package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsSurveyNotice entity provides the base persistence definition of
 * the CatsSurveyNotice entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsSurveyNotice implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long csnId;
	private Long csnCspId;
	private Integer csnSendType;
	private Integer csnSendNum;
	private Integer csnSuccessNum;
	private Integer csnSendStatus;
	private Long csnCreateUserId;
	private String csnCreateUserName;
	private Long csnCreateTime;

	// Constructors

	/** default constructor */
	public AbstractCatsSurveyNotice() {
	}

	/** full constructor */
	public AbstractCatsSurveyNotice(Long csnCspId, Integer csnSendType,
			Integer csnSendNum, Integer csnSuccessNum, Integer csnSendStatus,
			Long csnCreateUserId, String csnCreateUserName, Long csnCreateTime) {
		this.csnCspId = csnCspId;
		this.csnSendType = csnSendType;
		this.csnSendNum = csnSendNum;
		this.csnSuccessNum = csnSuccessNum;
		this.csnSendStatus = csnSendStatus;
		this.csnCreateUserId = csnCreateUserId;
		this.csnCreateUserName = csnCreateUserName;
		this.csnCreateTime = csnCreateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "csn_id", unique = true, nullable = false)
	public Long getCsnId() {
		return this.csnId;
	}

	public void setCsnId(Long csnId) {
		this.csnId = csnId;
	}

	@Column(name = "csn_csp_id")
	public Long getCsnCspId() {
		return this.csnCspId;
	}

	public void setCsnCspId(Long csnCspId) {
		this.csnCspId = csnCspId;
	}

	@Column(name = "csn_send_type")
	public Integer getCsnSendType() {
		return this.csnSendType;
	}

	public void setCsnSendType(Integer csnSendType) {
		this.csnSendType = csnSendType;
	}

	@Column(name = "csn_send_num")
	public Integer getCsnSendNum() {
		return this.csnSendNum;
	}

	public void setCsnSendNum(Integer csnSendNum) {
		this.csnSendNum = csnSendNum;
	}

	@Column(name = "csn_success_num")
	public Integer getCsnSuccessNum() {
		return this.csnSuccessNum;
	}

	public void setCsnSuccessNum(Integer csnSuccessNum) {
		this.csnSuccessNum = csnSuccessNum;
	}

	@Column(name = "csn_send_status")
	public Integer getCsnSendStatus() {
		return this.csnSendStatus;
	}

	public void setCsnSendStatus(Integer csnSendStatus) {
		this.csnSendStatus = csnSendStatus;
	}

	@Column(name = "csn_create_user_id")
	public Long getCsnCreateUserId() {
		return this.csnCreateUserId;
	}

	public void setCsnCreateUserId(Long csnCreateUserId) {
		this.csnCreateUserId = csnCreateUserId;
	}

	@Column(name = "csn_create_user_name", length = 128)
	public String getCsnCreateUserName() {
		return this.csnCreateUserName;
	}

	public void setCsnCreateUserName(String csnCreateUserName) {
		this.csnCreateUserName = csnCreateUserName;
	}

	@Column(name = "csn_create_time")
	public Long getCsnCreateTime() {
		return this.csnCreateTime;
	}

	public void setCsnCreateTime(Long csnCreateTime) {
		this.csnCreateTime = csnCreateTime;
	}

}