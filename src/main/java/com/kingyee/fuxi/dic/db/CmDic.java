package com.kingyee.fuxi.dic.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CmDic entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cm_dic")
public class CmDic implements java.io.Serializable {

	// Fields

	private Long cdId;
	private String cdType;
	private String cdDesc;
	private String cdKey;
	private String cdValue;
	private String cdMemo;
	private Integer cdSort;
	private Integer cdIsValid;
	private Long cdCreateUserId;
	private String cdCreateUserName;
	private Long cdCreateDate;
	private Long cdUpdateUserId;
	private String cdUpdateUserName;
	private Long cdUpdateDate;

	// Constructors

	/** default constructor */
	public CmDic() {
	}

	/** full constructor */
	public CmDic(String cdType, String cdDesc, String cdKey, String cdValue,
			String cdMemo, Integer cdSort, Integer cdIsValid,
			Long cdCreateUserId, String cdCreateUserName, Long cdCreateDate,
			Long cdUpdateUserId, String cdUpdateUserName, Long cdUpdateDate) {
		this.cdType = cdType;
		this.cdDesc = cdDesc;
		this.cdKey = cdKey;
		this.cdValue = cdValue;
		this.cdMemo = cdMemo;
		this.cdSort = cdSort;
		this.cdIsValid = cdIsValid;
		this.cdCreateUserId = cdCreateUserId;
		this.cdCreateUserName = cdCreateUserName;
		this.cdCreateDate = cdCreateDate;
		this.cdUpdateUserId = cdUpdateUserId;
		this.cdUpdateUserName = cdUpdateUserName;
		this.cdUpdateDate = cdUpdateDate;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cd_id", unique = true, nullable = false)
	public Long getCdId() {
		return this.cdId;
	}

	public void setCdId(Long cdId) {
		this.cdId = cdId;
	}

	@Column(name = "cd_type", length = 64)
	public String getCdType() {
		return this.cdType;
	}

	public void setCdType(String cdType) {
		this.cdType = cdType;
	}

	@Column(name = "cd_desc", length = 128)
	public String getCdDesc() {
		return this.cdDesc;
	}

	public void setCdDesc(String cdDesc) {
		this.cdDesc = cdDesc;
	}

	@Column(name = "cd_key", length = 128)
	public String getCdKey() {
		return this.cdKey;
	}

	public void setCdKey(String cdKey) {
		this.cdKey = cdKey;
	}

	@Column(name = "cd_value", length = 128)
	public String getCdValue() {
		return this.cdValue;
	}

	public void setCdValue(String cdValue) {
		this.cdValue = cdValue;
	}

	@Column(name = "cd_memo", length = 1204)
	public String getCdMemo() {
		return this.cdMemo;
	}

	public void setCdMemo(String cdMemo) {
		this.cdMemo = cdMemo;
	}

	@Column(name = "cd_sort")
	public Integer getCdSort() {
		return this.cdSort;
	}

	public void setCdSort(Integer cdSort) {
		this.cdSort = cdSort;
	}

	@Column(name = "cd_is_valid")
	public Integer getCdIsValid() {
		return this.cdIsValid;
	}

	public void setCdIsValid(Integer cdIsValid) {
		this.cdIsValid = cdIsValid;
	}

	@Column(name = "cd_create_user_id")
	public Long getCdCreateUserId() {
		return this.cdCreateUserId;
	}

	public void setCdCreateUserId(Long cdCreateUserId) {
		this.cdCreateUserId = cdCreateUserId;
	}

	@Column(name = "cd_create_user_name", length = 128)
	public String getCdCreateUserName() {
		return this.cdCreateUserName;
	}

	public void setCdCreateUserName(String cdCreateUserName) {
		this.cdCreateUserName = cdCreateUserName;
	}

	@Column(name = "cd_create_date")
	public Long getCdCreateDate() {
		return this.cdCreateDate;
	}

	public void setCdCreateDate(Long cdCreateDate) {
		this.cdCreateDate = cdCreateDate;
	}

	@Column(name = "cd_update_user_id")
	public Long getCdUpdateUserId() {
		return this.cdUpdateUserId;
	}

	public void setCdUpdateUserId(Long cdUpdateUserId) {
		this.cdUpdateUserId = cdUpdateUserId;
	}

	@Column(name = "cd_update_user_name", length = 128)
	public String getCdUpdateUserName() {
		return this.cdUpdateUserName;
	}

	public void setCdUpdateUserName(String cdUpdateUserName) {
		this.cdUpdateUserName = cdUpdateUserName;
	}

	@Column(name = "cd_update_date")
	public Long getCdUpdateDate() {
		return this.cdUpdateDate;
	}

	public void setCdUpdateDate(Long cdUpdateDate) {
		this.cdUpdateDate = cdUpdateDate;
	}

}