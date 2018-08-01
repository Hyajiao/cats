package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCmUser entity provides the base persistence definition of the CmUser
 * entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCmUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cuId;
	private String cuType;
	private String cuUserName;
	private String cuPassword;
	private String cuNickName;
	private String cuRealName;
	private String cuSex;
	private Integer cuAge;
	private String cuHeadimg;
	private String cuTelNo;
	private String cuEmail;
	private String cuProvince;
	private String cuCity;
	private String cuHospital;
	private String cuDept;
	private String cuProfessional;
	private String cuPost;
	private String cuDeptTelNo;
	private String cuCertificateImg;
	private Integer cuIsAuthentication;
	private Long cuMedliveId;
	private String cuMedliveHeadImg;
	private Double cuBalance;
	private Double cuLockMoney;
	private Integer cuIsValid;
	private Long cuCreateTime;
	private Long cuUpdateTime;

	// Constructors

	/** default constructor */
	public AbstractCmUser() {
	}

	/** full constructor */
	public AbstractCmUser(String cuType, String cuUserName, String cuPassword,
			String cuNickName, String cuRealName, String cuSex, Integer cuAge,
			String cuHeadimg, String cuTelNo, String cuEmail,
			String cuProvince, String cuCity, String cuHospital, String cuDept,
			String cuProfessional, String cuPost, String cuDeptTelNo,
			String cuCertificateImg, Integer cuIsAuthentication,
			Long cuMedliveId, String cuMedliveHeadImg, Double cuBalance,
			Double cuLockMoney, Integer cuIsValid, Long cuCreateTime,
			Long cuUpdateTime) {
		this.cuType = cuType;
		this.cuUserName = cuUserName;
		this.cuPassword = cuPassword;
		this.cuNickName = cuNickName;
		this.cuRealName = cuRealName;
		this.cuSex = cuSex;
		this.cuAge = cuAge;
		this.cuHeadimg = cuHeadimg;
		this.cuTelNo = cuTelNo;
		this.cuEmail = cuEmail;
		this.cuProvince = cuProvince;
		this.cuCity = cuCity;
		this.cuHospital = cuHospital;
		this.cuDept = cuDept;
		this.cuProfessional = cuProfessional;
		this.cuPost = cuPost;
		this.cuDeptTelNo = cuDeptTelNo;
		this.cuCertificateImg = cuCertificateImg;
		this.cuIsAuthentication = cuIsAuthentication;
		this.cuMedliveId = cuMedliveId;
		this.cuMedliveHeadImg = cuMedliveHeadImg;
		this.cuBalance = cuBalance;
		this.cuLockMoney = cuLockMoney;
		this.cuIsValid = cuIsValid;
		this.cuCreateTime = cuCreateTime;
		this.cuUpdateTime = cuUpdateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cu_id", unique = true, nullable = false)
	public Long getCuId() {
		return this.cuId;
	}

	public void setCuId(Long cuId) {
		this.cuId = cuId;
	}

	@Column(name = "cu_type", length = 64)
	public String getCuType() {
		return this.cuType;
	}

	public void setCuType(String cuType) {
		this.cuType = cuType;
	}

	@Column(name = "cu_user_name", length = 128)
	public String getCuUserName() {
		return this.cuUserName;
	}

	public void setCuUserName(String cuUserName) {
		this.cuUserName = cuUserName;
	}

	@Column(name = "cu_password", length = 128)
	public String getCuPassword() {
		return this.cuPassword;
	}

	public void setCuPassword(String cuPassword) {
		this.cuPassword = cuPassword;
	}

	@Column(name = "cu_nick_name", length = 128)
	public String getCuNickName() {
		return this.cuNickName;
	}

	public void setCuNickName(String cuNickName) {
		this.cuNickName = cuNickName;
	}

	@Column(name = "cu_real_name", length = 128)
	public String getCuRealName() {
		return this.cuRealName;
	}

	public void setCuRealName(String cuRealName) {
		this.cuRealName = cuRealName;
	}

	@Column(name = "cu_sex", length = 64)
	public String getCuSex() {
		return this.cuSex;
	}

	public void setCuSex(String cuSex) {
		this.cuSex = cuSex;
	}

	@Column(name = "cu_age")
	public Integer getCuAge() {
		return this.cuAge;
	}

	public void setCuAge(Integer cuAge) {
		this.cuAge = cuAge;
	}

	@Column(name = "cu_headimg", length = 128)
	public String getCuHeadimg() {
		return this.cuHeadimg;
	}

	public void setCuHeadimg(String cuHeadimg) {
		this.cuHeadimg = cuHeadimg;
	}

	@Column(name = "cu_tel_no", length = 128)
	public String getCuTelNo() {
		return this.cuTelNo;
	}

	public void setCuTelNo(String cuTelNo) {
		this.cuTelNo = cuTelNo;
	}

	@Column(name = "cu_email", length = 128)
	public String getCuEmail() {
		return this.cuEmail;
	}

	public void setCuEmail(String cuEmail) {
		this.cuEmail = cuEmail;
	}

	@Column(name = "cu_province", length = 128)
	public String getCuProvince() {
		return this.cuProvince;
	}

	public void setCuProvince(String cuProvince) {
		this.cuProvince = cuProvince;
	}

	@Column(name = "cu_city", length = 128)
	public String getCuCity() {
		return this.cuCity;
	}

	public void setCuCity(String cuCity) {
		this.cuCity = cuCity;
	}

	@Column(name = "cu_hospital", length = 512)
	public String getCuHospital() {
		return this.cuHospital;
	}

	public void setCuHospital(String cuHospital) {
		this.cuHospital = cuHospital;
	}

	@Column(name = "cu_dept", length = 128)
	public String getCuDept() {
		return this.cuDept;
	}

	public void setCuDept(String cuDept) {
		this.cuDept = cuDept;
	}

	@Column(name = "cu_professional", length = 128)
	public String getCuProfessional() {
		return this.cuProfessional;
	}

	public void setCuProfessional(String cuProfessional) {
		this.cuProfessional = cuProfessional;
	}

	@Column(name = "cu_post", length = 128)
	public String getCuPost() {
		return this.cuPost;
	}

	public void setCuPost(String cuPost) {
		this.cuPost = cuPost;
	}

	@Column(name = "cu_dept_tel_no", length = 128)
	public String getCuDeptTelNo() {
		return this.cuDeptTelNo;
	}

	public void setCuDeptTelNo(String cuDeptTelNo) {
		this.cuDeptTelNo = cuDeptTelNo;
	}

	@Column(name = "cu_certificate_img", length = 128)
	public String getCuCertificateImg() {
		return this.cuCertificateImg;
	}

	public void setCuCertificateImg(String cuCertificateImg) {
		this.cuCertificateImg = cuCertificateImg;
	}

	@Column(name = "cu_is_authentication")
	public Integer getCuIsAuthentication() {
		return this.cuIsAuthentication;
	}

	public void setCuIsAuthentication(Integer cuIsAuthentication) {
		this.cuIsAuthentication = cuIsAuthentication;
	}

	@Column(name = "cu_medlive_id")
	public Long getCuMedliveId() {
		return this.cuMedliveId;
	}

	public void setCuMedliveId(Long cuMedliveId) {
		this.cuMedliveId = cuMedliveId;
	}

	@Column(name = "cu_medlive_head_img", length = 128)
	public String getCuMedliveHeadImg() {
		return this.cuMedliveHeadImg;
	}

	public void setCuMedliveHeadImg(String cuMedliveHeadImg) {
		this.cuMedliveHeadImg = cuMedliveHeadImg;
	}

	@Column(name = "cu_balance", precision = 10)
	public Double getCuBalance() {
		return this.cuBalance;
	}

	public void setCuBalance(Double cuBalance) {
		this.cuBalance = cuBalance;
	}

	@Column(name = "cu_lock_money", precision = 10)
	public Double getCuLockMoney() {
		return this.cuLockMoney;
	}

	public void setCuLockMoney(Double cuLockMoney) {
		this.cuLockMoney = cuLockMoney;
	}

	@Column(name = "cu_is_valid")
	public Integer getCuIsValid() {
		return this.cuIsValid;
	}

	public void setCuIsValid(Integer cuIsValid) {
		this.cuIsValid = cuIsValid;
	}

	@Column(name = "cu_create_time")
	public Long getCuCreateTime() {
		return this.cuCreateTime;
	}

	public void setCuCreateTime(Long cuCreateTime) {
		this.cuCreateTime = cuCreateTime;
	}

	@Column(name = "cu_update_time")
	public Long getCuUpdateTime() {
		return this.cuUpdateTime;
	}

	public void setCuUpdateTime(Long cuUpdateTime) {
		this.cuUpdateTime = cuUpdateTime;
	}

}