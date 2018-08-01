package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsExpertGroupDetail entity provides the base persistence definition
 * of the CatsExpertGroupDetail entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsExpertGroupDetail implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cegdId;
	private Long cegdCegId;
	private Long cegdMedliveId;
	private String cegdRealName;
	private String cegdSex;
	private String cegdHospital;
	private String cegdDept;
	private String cegdProfessional;
	private String cegdTelNo;
	private String cegdEmail;
	private String cegdAlipayRealName;
	private String cegdAlipayUserName;
	private String cegdBankRealName;
	private String cegdBankName;
	private String cegdBankNo;
	private Long cegdCreateTime;
	private Long cegdCreateUserId;
	private String cegdCreateUserName;
	private Long cegdUpdateTime;
	private Long cegdUpdateUserId;
	private String cegdUpdateUserName;

	// Constructors

	/** default constructor */
	public AbstractCatsExpertGroupDetail() {
	}

	/** full constructor */
	public AbstractCatsExpertGroupDetail(Long cegdCegId, Long cegdMedliveId,
			String cegdRealName, String cegdSex, String cegdHospital,
			String cegdDept, String cegdProfessional, String cegdTelNo,
			String cegdEmail, String cegdAlipayRealName,
			String cegdAlipayUserName, String cegdBankRealName,
			String cegdBankName, String cegdBankNo, Long cegdCreateTime,
			Long cegdCreateUserId, String cegdCreateUserName,
			Long cegdUpdateTime, Long cegdUpdateUserId,
			String cegdUpdateUserName) {
		this.cegdCegId = cegdCegId;
		this.cegdMedliveId = cegdMedliveId;
		this.cegdRealName = cegdRealName;
		this.cegdSex = cegdSex;
		this.cegdHospital = cegdHospital;
		this.cegdDept = cegdDept;
		this.cegdProfessional = cegdProfessional;
		this.cegdTelNo = cegdTelNo;
		this.cegdEmail = cegdEmail;
		this.cegdAlipayRealName = cegdAlipayRealName;
		this.cegdAlipayUserName = cegdAlipayUserName;
		this.cegdBankRealName = cegdBankRealName;
		this.cegdBankName = cegdBankName;
		this.cegdBankNo = cegdBankNo;
		this.cegdCreateTime = cegdCreateTime;
		this.cegdCreateUserId = cegdCreateUserId;
		this.cegdCreateUserName = cegdCreateUserName;
		this.cegdUpdateTime = cegdUpdateTime;
		this.cegdUpdateUserId = cegdUpdateUserId;
		this.cegdUpdateUserName = cegdUpdateUserName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cegd_id", unique = true, nullable = false)
	public Long getCegdId() {
		return this.cegdId;
	}

	public void setCegdId(Long cegdId) {
		this.cegdId = cegdId;
	}

	@Column(name = "cegd_ceg_id")
	public Long getCegdCegId() {
		return this.cegdCegId;
	}

	public void setCegdCegId(Long cegdCegId) {
		this.cegdCegId = cegdCegId;
	}

	@Column(name = "cegd_medlive_id")
	public Long getCegdMedliveId() {
		return this.cegdMedliveId;
	}

	public void setCegdMedliveId(Long cegdMedliveId) {
		this.cegdMedliveId = cegdMedliveId;
	}

	@Column(name = "cegd_real_name", length = 128)
	public String getCegdRealName() {
		return this.cegdRealName;
	}

	public void setCegdRealName(String cegdRealName) {
		this.cegdRealName = cegdRealName;
	}

	@Column(name = "cegd_sex", length = 128)
	public String getCegdSex() {
		return this.cegdSex;
	}

	public void setCegdSex(String cegdSex) {
		this.cegdSex = cegdSex;
	}

	@Column(name = "cegd_hospital", length = 128)
	public String getCegdHospital() {
		return this.cegdHospital;
	}

	public void setCegdHospital(String cegdHospital) {
		this.cegdHospital = cegdHospital;
	}

	@Column(name = "cegd_dept", length = 128)
	public String getCegdDept() {
		return this.cegdDept;
	}

	public void setCegdDept(String cegdDept) {
		this.cegdDept = cegdDept;
	}

	@Column(name = "cegd_professional", length = 128)
	public String getCegdProfessional() {
		return this.cegdProfessional;
	}

	public void setCegdProfessional(String cegdProfessional) {
		this.cegdProfessional = cegdProfessional;
	}

	@Column(name = "cegd_tel_no", length = 128)
	public String getCegdTelNo() {
		return this.cegdTelNo;
	}

	public void setCegdTelNo(String cegdTelNo) {
		this.cegdTelNo = cegdTelNo;
	}

	@Column(name = "cegd_email", length = 128)
	public String getCegdEmail() {
		return this.cegdEmail;
	}

	public void setCegdEmail(String cegdEmail) {
		this.cegdEmail = cegdEmail;
	}

	@Column(name = "cegd_alipay_real_name", length = 128)
	public String getCegdAlipayRealName() {
		return this.cegdAlipayRealName;
	}

	public void setCegdAlipayRealName(String cegdAlipayRealName) {
		this.cegdAlipayRealName = cegdAlipayRealName;
	}

	@Column(name = "cegd_alipay_user_name", length = 128)
	public String getCegdAlipayUserName() {
		return this.cegdAlipayUserName;
	}

	public void setCegdAlipayUserName(String cegdAlipayUserName) {
		this.cegdAlipayUserName = cegdAlipayUserName;
	}

	@Column(name = "cegd_bank_real_name", length = 128)
	public String getCegdBankRealName() {
		return this.cegdBankRealName;
	}

	public void setCegdBankRealName(String cegdBankRealName) {
		this.cegdBankRealName = cegdBankRealName;
	}

	@Column(name = "cegd_bank_name", length = 128)
	public String getCegdBankName() {
		return this.cegdBankName;
	}

	public void setCegdBankName(String cegdBankName) {
		this.cegdBankName = cegdBankName;
	}

	@Column(name = "cegd_bank_no", length = 128)
	public String getCegdBankNo() {
		return this.cegdBankNo;
	}

	public void setCegdBankNo(String cegdBankNo) {
		this.cegdBankNo = cegdBankNo;
	}

	@Column(name = "cegd_create_time")
	public Long getCegdCreateTime() {
		return this.cegdCreateTime;
	}

	public void setCegdCreateTime(Long cegdCreateTime) {
		this.cegdCreateTime = cegdCreateTime;
	}

	@Column(name = "cegd_create_user_id")
	public Long getCegdCreateUserId() {
		return this.cegdCreateUserId;
	}

	public void setCegdCreateUserId(Long cegdCreateUserId) {
		this.cegdCreateUserId = cegdCreateUserId;
	}

	@Column(name = "cegd_create_user_name", length = 128)
	public String getCegdCreateUserName() {
		return this.cegdCreateUserName;
	}

	public void setCegdCreateUserName(String cegdCreateUserName) {
		this.cegdCreateUserName = cegdCreateUserName;
	}

	@Column(name = "cegd_update_time")
	public Long getCegdUpdateTime() {
		return this.cegdUpdateTime;
	}

	public void setCegdUpdateTime(Long cegdUpdateTime) {
		this.cegdUpdateTime = cegdUpdateTime;
	}

	@Column(name = "cegd_update_user_id")
	public Long getCegdUpdateUserId() {
		return this.cegdUpdateUserId;
	}

	public void setCegdUpdateUserId(Long cegdUpdateUserId) {
		this.cegdUpdateUserId = cegdUpdateUserId;
	}

	@Column(name = "cegd_update_user_name", length = 128)
	public String getCegdUpdateUserName() {
		return this.cegdUpdateUserName;
	}

	public void setCegdUpdateUserName(String cegdUpdateUserName) {
		this.cegdUpdateUserName = cegdUpdateUserName;
	}

}