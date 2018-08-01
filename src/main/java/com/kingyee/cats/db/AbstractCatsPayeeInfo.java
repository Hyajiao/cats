package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsPayeeInfo entity provides the base persistence definition of the
 * CatsPayeeInfo entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsPayeeInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cpiId;
	private Long cpiCuId;
	private Integer cpiWithdrawMode;
	private String cpiRealName;
	private String cpiBankName;
	private String cpiBankNo;
	private String cpiTelNo;
	private String cpiAlipayUserName;
	private Integer cpiIsDefault;
	private Long cpiCreateTime;
	private Long cpiUpdateTime;

	// Constructors

	/** default constructor */
	public AbstractCatsPayeeInfo() {
	}

	/** full constructor */
	public AbstractCatsPayeeInfo(Long cpiCuId, Integer cpiWithdrawMode,
			String cpiRealName, String cpiBankName, String cpiBankNo,
			String cpiTelNo, String cpiAlipayUserName, Integer cpiIsDefault,
			Long cpiCreateTime, Long cpiUpdateTime) {
		this.cpiCuId = cpiCuId;
		this.cpiWithdrawMode = cpiWithdrawMode;
		this.cpiRealName = cpiRealName;
		this.cpiBankName = cpiBankName;
		this.cpiBankNo = cpiBankNo;
		this.cpiTelNo = cpiTelNo;
		this.cpiAlipayUserName = cpiAlipayUserName;
		this.cpiIsDefault = cpiIsDefault;
		this.cpiCreateTime = cpiCreateTime;
		this.cpiUpdateTime = cpiUpdateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cpi_id", unique = true, nullable = false)
	public Long getCpiId() {
		return this.cpiId;
	}

	public void setCpiId(Long cpiId) {
		this.cpiId = cpiId;
	}

	@Column(name = "cpi_cu_id")
	public Long getCpiCuId() {
		return this.cpiCuId;
	}

	public void setCpiCuId(Long cpiCuId) {
		this.cpiCuId = cpiCuId;
	}

	@Column(name = "cpi_withdraw_mode")
	public Integer getCpiWithdrawMode() {
		return this.cpiWithdrawMode;
	}

	public void setCpiWithdrawMode(Integer cpiWithdrawMode) {
		this.cpiWithdrawMode = cpiWithdrawMode;
	}

	@Column(name = "cpi_real_name", length = 128)
	public String getCpiRealName() {
		return this.cpiRealName;
	}

	public void setCpiRealName(String cpiRealName) {
		this.cpiRealName = cpiRealName;
	}

	@Column(name = "cpi_bank_name", length = 128)
	public String getCpiBankName() {
		return this.cpiBankName;
	}

	public void setCpiBankName(String cpiBankName) {
		this.cpiBankName = cpiBankName;
	}

	@Column(name = "cpi_bank_no", length = 128)
	public String getCpiBankNo() {
		return this.cpiBankNo;
	}

	public void setCpiBankNo(String cpiBankNo) {
		this.cpiBankNo = cpiBankNo;
	}

	@Column(name = "cpi_tel_no", length = 128)
	public String getCpiTelNo() {
		return this.cpiTelNo;
	}

	public void setCpiTelNo(String cpiTelNo) {
		this.cpiTelNo = cpiTelNo;
	}

	@Column(name = "cpi_alipay_user_name", length = 128)
	public String getCpiAlipayUserName() {
		return this.cpiAlipayUserName;
	}

	public void setCpiAlipayUserName(String cpiAlipayUserName) {
		this.cpiAlipayUserName = cpiAlipayUserName;
	}

	@Column(name = "cpi_is_default")
	public Integer getCpiIsDefault() {
		return this.cpiIsDefault;
	}

	public void setCpiIsDefault(Integer cpiIsDefault) {
		this.cpiIsDefault = cpiIsDefault;
	}

	@Column(name = "cpi_create_time")
	public Long getCpiCreateTime() {
		return this.cpiCreateTime;
	}

	public void setCpiCreateTime(Long cpiCreateTime) {
		this.cpiCreateTime = cpiCreateTime;
	}

	@Column(name = "cpi_update_time")
	public Long getCpiUpdateTime() {
		return this.cpiUpdateTime;
	}

	public void setCpiUpdateTime(Long cpiUpdateTime) {
		this.cpiUpdateTime = cpiUpdateTime;
	}

}