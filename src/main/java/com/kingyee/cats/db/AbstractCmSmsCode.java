package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCmSmsCode entity provides the base persistence definition of the
 * CmSmsCode entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCmSmsCode implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cscId;
	private String cscTelNo;
	private Long cscUserId;
	private String cscType;
	private String cscCode;
	private Long cscInvalidDate;
	private String cscIpAddress;
	private Integer cscIsUsed;
	private Long cscCreateDate;

	// Constructors

	/** default constructor */
	public AbstractCmSmsCode() {
	}

	/** full constructor */
	public AbstractCmSmsCode(String cscTelNo, Long cscUserId, String cscType,
			String cscCode, Long cscInvalidDate, String cscIpAddress,
			Integer cscIsUsed, Long cscCreateDate) {
		this.cscTelNo = cscTelNo;
		this.cscUserId = cscUserId;
		this.cscType = cscType;
		this.cscCode = cscCode;
		this.cscInvalidDate = cscInvalidDate;
		this.cscIpAddress = cscIpAddress;
		this.cscIsUsed = cscIsUsed;
		this.cscCreateDate = cscCreateDate;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "csc_id", unique = true, nullable = false)
	public Long getCscId() {
		return this.cscId;
	}

	public void setCscId(Long cscId) {
		this.cscId = cscId;
	}

	@Column(name = "csc_tel_no", length = 128)
	public String getCscTelNo() {
		return this.cscTelNo;
	}

	public void setCscTelNo(String cscTelNo) {
		this.cscTelNo = cscTelNo;
	}

	@Column(name = "csc_user_id")
	public Long getCscUserId() {
		return this.cscUserId;
	}

	public void setCscUserId(Long cscUserId) {
		this.cscUserId = cscUserId;
	}

	@Column(name = "csc_type", length = 64)
	public String getCscType() {
		return this.cscType;
	}

	public void setCscType(String cscType) {
		this.cscType = cscType;
	}

	@Column(name = "csc_code", length = 128)
	public String getCscCode() {
		return this.cscCode;
	}

	public void setCscCode(String cscCode) {
		this.cscCode = cscCode;
	}

	@Column(name = "csc_invalid_date")
	public Long getCscInvalidDate() {
		return this.cscInvalidDate;
	}

	public void setCscInvalidDate(Long cscInvalidDate) {
		this.cscInvalidDate = cscInvalidDate;
	}

	@Column(name = "csc_ip_address", length = 128)
	public String getCscIpAddress() {
		return this.cscIpAddress;
	}

	public void setCscIpAddress(String cscIpAddress) {
		this.cscIpAddress = cscIpAddress;
	}

	@Column(name = "csc_is_used")
	public Integer getCscIsUsed() {
		return this.cscIsUsed;
	}

	public void setCscIsUsed(Integer cscIsUsed) {
		this.cscIsUsed = cscIsUsed;
	}

	@Column(name = "csc_create_date")
	public Long getCscCreateDate() {
		return this.cscCreateDate;
	}

	public void setCscCreateDate(Long cscCreateDate) {
		this.cscCreateDate = cscCreateDate;
	}

}