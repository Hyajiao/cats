package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsExpertGroup entity provides the base persistence definition of
 * the CatsExpertGroup entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsExpertGroup implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cegId;
	private String cegExpertGroupName;
	private Integer cegExpertNum;
	private String cegMemo;
	private Integer cegIsValid;
	private Long cegCreateTime;
	private Long cegCreateUserId;
	private String cegCreateUserName;
	private Long cegUpdateTime;
	private Long cegUpdateUserId;
	private String cegUpdateUserName;

	// Constructors

	/** default constructor */
	public AbstractCatsExpertGroup() {
	}

	/** full constructor */
	public AbstractCatsExpertGroup(String cegExpertGroupName,
			Integer cegExpertNum, String cegMemo, Integer cegIsValid,
			Long cegCreateTime, Long cegCreateUserId, String cegCreateUserName,
			Long cegUpdateTime, Long cegUpdateUserId, String cegUpdateUserName) {
		this.cegExpertGroupName = cegExpertGroupName;
		this.cegExpertNum = cegExpertNum;
		this.cegMemo = cegMemo;
		this.cegIsValid = cegIsValid;
		this.cegCreateTime = cegCreateTime;
		this.cegCreateUserId = cegCreateUserId;
		this.cegCreateUserName = cegCreateUserName;
		this.cegUpdateTime = cegUpdateTime;
		this.cegUpdateUserId = cegUpdateUserId;
		this.cegUpdateUserName = cegUpdateUserName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ceg_id", unique = true, nullable = false)
	public Long getCegId() {
		return this.cegId;
	}

	public void setCegId(Long cegId) {
		this.cegId = cegId;
	}

	@Column(name = "ceg_expert_group_name", length = 128)
	public String getCegExpertGroupName() {
		return this.cegExpertGroupName;
	}

	public void setCegExpertGroupName(String cegExpertGroupName) {
		this.cegExpertGroupName = cegExpertGroupName;
	}

	@Column(name = "ceg_expert_num")
	public Integer getCegExpertNum() {
		return this.cegExpertNum;
	}

	public void setCegExpertNum(Integer cegExpertNum) {
		this.cegExpertNum = cegExpertNum;
	}

	@Column(name = "ceg_memo", length = 1024)
	public String getCegMemo() {
		return this.cegMemo;
	}

	public void setCegMemo(String cegMemo) {
		this.cegMemo = cegMemo;
	}

	@Column(name = "ceg_is_valid")
	public Integer getCegIsValid() {
		return this.cegIsValid;
	}

	public void setCegIsValid(Integer cegIsValid) {
		this.cegIsValid = cegIsValid;
	}

	@Column(name = "ceg_create_time")
	public Long getCegCreateTime() {
		return this.cegCreateTime;
	}

	public void setCegCreateTime(Long cegCreateTime) {
		this.cegCreateTime = cegCreateTime;
	}

	@Column(name = "ceg_create_user_id")
	public Long getCegCreateUserId() {
		return this.cegCreateUserId;
	}

	public void setCegCreateUserId(Long cegCreateUserId) {
		this.cegCreateUserId = cegCreateUserId;
	}

	@Column(name = "ceg_create_user_name", length = 128)
	public String getCegCreateUserName() {
		return this.cegCreateUserName;
	}

	public void setCegCreateUserName(String cegCreateUserName) {
		this.cegCreateUserName = cegCreateUserName;
	}

	@Column(name = "ceg_update_time")
	public Long getCegUpdateTime() {
		return this.cegUpdateTime;
	}

	public void setCegUpdateTime(Long cegUpdateTime) {
		this.cegUpdateTime = cegUpdateTime;
	}

	@Column(name = "ceg_update_user_id")
	public Long getCegUpdateUserId() {
		return this.cegUpdateUserId;
	}

	public void setCegUpdateUserId(Long cegUpdateUserId) {
		this.cegUpdateUserId = cegUpdateUserId;
	}

	@Column(name = "ceg_update_user_name", length = 128)
	public String getCegUpdateUserName() {
		return this.cegUpdateUserName;
	}

	public void setCegUpdateUserName(String cegUpdateUserName) {
		this.cegUpdateUserName = cegUpdateUserName;
	}

}