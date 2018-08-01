package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsProjectLiteratureConfig entity provides the base persistence
 * definition of the CatsProjectLiteratureConfig entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsProjectLiteratureConfig implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cplcId;
	private Long cplcCpId;
	private String cplcTitle;
	private String cplcAuthor;
	private String cplcJour;
	private String cplcKeyword;
	private String cplcAbstract;
	private String cplcPublishDateStart;
	private String cplcPublishDateEnd;
	private Long cplcCreateTime;
	private Long cplcCreateUserId;
	private String cplcCreateUserName;
	private Long cplcUpdateTime;
	private Long cplcUpdateUserId;
	private String cplcUpdateUserName;

	// Constructors

	/** default constructor */
	public AbstractCatsProjectLiteratureConfig() {
	}

	/** full constructor */
	public AbstractCatsProjectLiteratureConfig(Long cplcCpId, String cplcTitle,
			String cplcAuthor, String cplcJour, String cplcKeyword,
			String cplcAbstract, String cplcPublishDateStart,
			String cplcPublishDateEnd, Long cplcCreateTime,
			Long cplcCreateUserId, String cplcCreateUserName,
			Long cplcUpdateTime, Long cplcUpdateUserId,
			String cplcUpdateUserName) {
		this.cplcCpId = cplcCpId;
		this.cplcTitle = cplcTitle;
		this.cplcAuthor = cplcAuthor;
		this.cplcJour = cplcJour;
		this.cplcKeyword = cplcKeyword;
		this.cplcAbstract = cplcAbstract;
		this.cplcPublishDateStart = cplcPublishDateStart;
		this.cplcPublishDateEnd = cplcPublishDateEnd;
		this.cplcCreateTime = cplcCreateTime;
		this.cplcCreateUserId = cplcCreateUserId;
		this.cplcCreateUserName = cplcCreateUserName;
		this.cplcUpdateTime = cplcUpdateTime;
		this.cplcUpdateUserId = cplcUpdateUserId;
		this.cplcUpdateUserName = cplcUpdateUserName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cplc_id", unique = true, nullable = false)
	public Long getCplcId() {
		return this.cplcId;
	}

	public void setCplcId(Long cplcId) {
		this.cplcId = cplcId;
	}

	@Column(name = "cplc_cp_id")
	public Long getCplcCpId() {
		return this.cplcCpId;
	}

	public void setCplcCpId(Long cplcCpId) {
		this.cplcCpId = cplcCpId;
	}

	@Column(name = "cplc_title", length = 128)
	public String getCplcTitle() {
		return this.cplcTitle;
	}

	public void setCplcTitle(String cplcTitle) {
		this.cplcTitle = cplcTitle;
	}

	@Column(name = "cplc_author", length = 128)
	public String getCplcAuthor() {
		return this.cplcAuthor;
	}

	public void setCplcAuthor(String cplcAuthor) {
		this.cplcAuthor = cplcAuthor;
	}

	@Column(name = "cplc_jour", length = 128)
	public String getCplcJour() {
		return this.cplcJour;
	}

	public void setCplcJour(String cplcJour) {
		this.cplcJour = cplcJour;
	}

	@Column(name = "cplc_keyword", length = 128)
	public String getCplcKeyword() {
		return this.cplcKeyword;
	}

	public void setCplcKeyword(String cplcKeyword) {
		this.cplcKeyword = cplcKeyword;
	}

	@Column(name = "cplc_abstract", length = 128)
	public String getCplcAbstract() {
		return this.cplcAbstract;
	}

	public void setCplcAbstract(String cplcAbstract) {
		this.cplcAbstract = cplcAbstract;
	}

	@Column(name = "cplc_publish_date_start", length = 128)
	public String getCplcPublishDateStart() {
		return this.cplcPublishDateStart;
	}

	public void setCplcPublishDateStart(String cplcPublishDateStart) {
		this.cplcPublishDateStart = cplcPublishDateStart;
	}

	@Column(name = "cplc_publish_date_end", length = 128)
	public String getCplcPublishDateEnd() {
		return this.cplcPublishDateEnd;
	}

	public void setCplcPublishDateEnd(String cplcPublishDateEnd) {
		this.cplcPublishDateEnd = cplcPublishDateEnd;
	}

	@Column(name = "cplc_create_time")
	public Long getCplcCreateTime() {
		return this.cplcCreateTime;
	}

	public void setCplcCreateTime(Long cplcCreateTime) {
		this.cplcCreateTime = cplcCreateTime;
	}

	@Column(name = "cplc_create_user_id")
	public Long getCplcCreateUserId() {
		return this.cplcCreateUserId;
	}

	public void setCplcCreateUserId(Long cplcCreateUserId) {
		this.cplcCreateUserId = cplcCreateUserId;
	}

	@Column(name = "cplc_create_user_name", length = 128)
	public String getCplcCreateUserName() {
		return this.cplcCreateUserName;
	}

	public void setCplcCreateUserName(String cplcCreateUserName) {
		this.cplcCreateUserName = cplcCreateUserName;
	}

	@Column(name = "cplc_update_time")
	public Long getCplcUpdateTime() {
		return this.cplcUpdateTime;
	}

	public void setCplcUpdateTime(Long cplcUpdateTime) {
		this.cplcUpdateTime = cplcUpdateTime;
	}

	@Column(name = "cplc_update_user_id")
	public Long getCplcUpdateUserId() {
		return this.cplcUpdateUserId;
	}

	public void setCplcUpdateUserId(Long cplcUpdateUserId) {
		this.cplcUpdateUserId = cplcUpdateUserId;
	}

	@Column(name = "cplc_update_user_name", length = 128)
	public String getCplcUpdateUserName() {
		return this.cplcUpdateUserName;
	}

	public void setCplcUpdateUserName(String cplcUpdateUserName) {
		this.cplcUpdateUserName = cplcUpdateUserName;
	}

}