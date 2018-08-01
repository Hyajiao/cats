package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsProject entity provides the base persistence definition of the
 * CatsProject entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsProject implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cpId;
	private String cpProjectName;
	private String cpCompanyName;
	private String cpProjectMemo;
	private Long cpStartTime;
	private Long cpEndTime;
	private Integer cpIsValid;
	private Long cpCreateTime;
	private Long cpCreateUserId;
	private String cpCreateUserName;
	private Long cpUpdateTime;
	private Long cpUpdateUserId;
	private String cpUpdateUserName;

	// Constructors

	/** default constructor */
	public AbstractCatsProject() {
	}

	/** full constructor */
	public AbstractCatsProject(String cpProjectName, String cpCompanyName,
			String cpProjectMemo, Long cpStartTime, Long cpEndTime,
			Integer cpIsValid, Long cpCreateTime, Long cpCreateUserId,
			String cpCreateUserName, Long cpUpdateTime, Long cpUpdateUserId,
			String cpUpdateUserName) {
		this.cpProjectName = cpProjectName;
		this.cpCompanyName = cpCompanyName;
		this.cpProjectMemo = cpProjectMemo;
		this.cpStartTime = cpStartTime;
		this.cpEndTime = cpEndTime;
		this.cpIsValid = cpIsValid;
		this.cpCreateTime = cpCreateTime;
		this.cpCreateUserId = cpCreateUserId;
		this.cpCreateUserName = cpCreateUserName;
		this.cpUpdateTime = cpUpdateTime;
		this.cpUpdateUserId = cpUpdateUserId;
		this.cpUpdateUserName = cpUpdateUserName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cp_id", unique = true, nullable = false)
	public Long getCpId() {
		return this.cpId;
	}

	public void setCpId(Long cpId) {
		this.cpId = cpId;
	}

	@Column(name = "cp_project_name", length = 128)
	public String getCpProjectName() {
		return this.cpProjectName;
	}

	public void setCpProjectName(String cpProjectName) {
		this.cpProjectName = cpProjectName;
	}

	@Column(name = "cp_company_name", length = 128)
	public String getCpCompanyName() {
		return this.cpCompanyName;
	}

	public void setCpCompanyName(String cpCompanyName) {
		this.cpCompanyName = cpCompanyName;
	}

	@Column(name = "cp_project_memo", length = 512)
	public String getCpProjectMemo() {
		return this.cpProjectMemo;
	}

	public void setCpProjectMemo(String cpProjectMemo) {
		this.cpProjectMemo = cpProjectMemo;
	}

	@Column(name = "cp_start_time")
	public Long getCpStartTime() {
		return this.cpStartTime;
	}

	public void setCpStartTime(Long cpStartTime) {
		this.cpStartTime = cpStartTime;
	}

	@Column(name = "cp_end_time")
	public Long getCpEndTime() {
		return this.cpEndTime;
	}

	public void setCpEndTime(Long cpEndTime) {
		this.cpEndTime = cpEndTime;
	}

	@Column(name = "cp_is_valid")
	public Integer getCpIsValid() {
		return this.cpIsValid;
	}

	public void setCpIsValid(Integer cpIsValid) {
		this.cpIsValid = cpIsValid;
	}

	@Column(name = "cp_create_time")
	public Long getCpCreateTime() {
		return this.cpCreateTime;
	}

	public void setCpCreateTime(Long cpCreateTime) {
		this.cpCreateTime = cpCreateTime;
	}

	@Column(name = "cp_create_user_id")
	public Long getCpCreateUserId() {
		return this.cpCreateUserId;
	}

	public void setCpCreateUserId(Long cpCreateUserId) {
		this.cpCreateUserId = cpCreateUserId;
	}

	@Column(name = "cp_create_user_name", length = 128)
	public String getCpCreateUserName() {
		return this.cpCreateUserName;
	}

	public void setCpCreateUserName(String cpCreateUserName) {
		this.cpCreateUserName = cpCreateUserName;
	}

	@Column(name = "cp_update_time")
	public Long getCpUpdateTime() {
		return this.cpUpdateTime;
	}

	public void setCpUpdateTime(Long cpUpdateTime) {
		this.cpUpdateTime = cpUpdateTime;
	}

	@Column(name = "cp_update_user_id")
	public Long getCpUpdateUserId() {
		return this.cpUpdateUserId;
	}

	public void setCpUpdateUserId(Long cpUpdateUserId) {
		this.cpUpdateUserId = cpUpdateUserId;
	}

	@Column(name = "cp_update_user_name", length = 128)
	public String getCpUpdateUserName() {
		return this.cpUpdateUserName;
	}

	public void setCpUpdateUserName(String cpUpdateUserName) {
		this.cpUpdateUserName = cpUpdateUserName;
	}

}