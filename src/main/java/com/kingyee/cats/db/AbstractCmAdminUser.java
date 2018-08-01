package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCmAdminUser entity provides the base persistence definition of the
 * CmAdminUser entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCmAdminUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long auId;
	private String auShowName;
	private Integer auSex;
	private Integer auAge;
	private String auTel;
	private String auEmail;
	private String auPostCode;
	private String auAddress;
	private String auUserName;
	private String auPassword;
	private Long auRole;
	private String auZone;
	private Integer auIsValid;
	private Long auCreateDate;
	private Long auCreateUserId;
	private String auCreateUserName;
	private Long auUpdateUserId;
	private String auUpdateUserName;
	private Long auUpdateDate;

	// Constructors

	/** default constructor */
	public AbstractCmAdminUser() {
	}

	/** full constructor */
	public AbstractCmAdminUser(String auShowName, Integer auSex, Integer auAge,
			String auTel, String auEmail, String auPostCode, String auAddress,
			String auUserName, String auPassword, Long auRole, String auZone,
			Integer auIsValid, Long auCreateDate, Long auCreateUserId,
			String auCreateUserName, Long auUpdateUserId,
			String auUpdateUserName, Long auUpdateDate) {
		this.auShowName = auShowName;
		this.auSex = auSex;
		this.auAge = auAge;
		this.auTel = auTel;
		this.auEmail = auEmail;
		this.auPostCode = auPostCode;
		this.auAddress = auAddress;
		this.auUserName = auUserName;
		this.auPassword = auPassword;
		this.auRole = auRole;
		this.auZone = auZone;
		this.auIsValid = auIsValid;
		this.auCreateDate = auCreateDate;
		this.auCreateUserId = auCreateUserId;
		this.auCreateUserName = auCreateUserName;
		this.auUpdateUserId = auUpdateUserId;
		this.auUpdateUserName = auUpdateUserName;
		this.auUpdateDate = auUpdateDate;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "au_id", unique = true, nullable = false)
	public Long getAuId() {
		return this.auId;
	}

	public void setAuId(Long auId) {
		this.auId = auId;
	}

	@Column(name = "au_show_name", length = 128)
	public String getAuShowName() {
		return this.auShowName;
	}

	public void setAuShowName(String auShowName) {
		this.auShowName = auShowName;
	}

	@Column(name = "au_sex")
	public Integer getAuSex() {
		return this.auSex;
	}

	public void setAuSex(Integer auSex) {
		this.auSex = auSex;
	}

	@Column(name = "au_age")
	public Integer getAuAge() {
		return this.auAge;
	}

	public void setAuAge(Integer auAge) {
		this.auAge = auAge;
	}

	@Column(name = "au_tel", length = 128)
	public String getAuTel() {
		return this.auTel;
	}

	public void setAuTel(String auTel) {
		this.auTel = auTel;
	}

	@Column(name = "au_email", length = 128)
	public String getAuEmail() {
		return this.auEmail;
	}

	public void setAuEmail(String auEmail) {
		this.auEmail = auEmail;
	}

	@Column(name = "au_post_code", length = 128)
	public String getAuPostCode() {
		return this.auPostCode;
	}

	public void setAuPostCode(String auPostCode) {
		this.auPostCode = auPostCode;
	}

	@Column(name = "au_address", length = 128)
	public String getAuAddress() {
		return this.auAddress;
	}

	public void setAuAddress(String auAddress) {
		this.auAddress = auAddress;
	}

	@Column(name = "au_user_name", length = 128)
	public String getAuUserName() {
		return this.auUserName;
	}

	public void setAuUserName(String auUserName) {
		this.auUserName = auUserName;
	}

	@Column(name = "au_password", length = 128)
	public String getAuPassword() {
		return this.auPassword;
	}

	public void setAuPassword(String auPassword) {
		this.auPassword = auPassword;
	}

	@Column(name = "au_role")
	public Long getAuRole() {
		return this.auRole;
	}

	public void setAuRole(Long auRole) {
		this.auRole = auRole;
	}

	@Column(name = "au_zone", length = 128)
	public String getAuZone() {
		return this.auZone;
	}

	public void setAuZone(String auZone) {
		this.auZone = auZone;
	}

	@Column(name = "au_is_valid")
	public Integer getAuIsValid() {
		return this.auIsValid;
	}

	public void setAuIsValid(Integer auIsValid) {
		this.auIsValid = auIsValid;
	}

	@Column(name = "au_create_date")
	public Long getAuCreateDate() {
		return this.auCreateDate;
	}

	public void setAuCreateDate(Long auCreateDate) {
		this.auCreateDate = auCreateDate;
	}

	@Column(name = "au_create_user_id")
	public Long getAuCreateUserId() {
		return this.auCreateUserId;
	}

	public void setAuCreateUserId(Long auCreateUserId) {
		this.auCreateUserId = auCreateUserId;
	}

	@Column(name = "au_create_user_name", length = 128)
	public String getAuCreateUserName() {
		return this.auCreateUserName;
	}

	public void setAuCreateUserName(String auCreateUserName) {
		this.auCreateUserName = auCreateUserName;
	}

	@Column(name = "au_update_user_id")
	public Long getAuUpdateUserId() {
		return this.auUpdateUserId;
	}

	public void setAuUpdateUserId(Long auUpdateUserId) {
		this.auUpdateUserId = auUpdateUserId;
	}

	@Column(name = "au_update_user_name", length = 128)
	public String getAuUpdateUserName() {
		return this.auUpdateUserName;
	}

	public void setAuUpdateUserName(String auUpdateUserName) {
		this.auUpdateUserName = auUpdateUserName;
	}

	@Column(name = "au_update_date")
	public Long getAuUpdateDate() {
		return this.auUpdateDate;
	}

	public void setAuUpdateDate(Long auUpdateDate) {
		this.auUpdateDate = auUpdateDate;
	}

}