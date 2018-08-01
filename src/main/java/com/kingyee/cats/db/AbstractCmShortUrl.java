package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCmShortUrl entity provides the base persistence definition of the
 * CmShortUrl entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCmShortUrl implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long csuId;
	private String csuCode;
	private String csuUrl;
	private Integer csuHitCount;
	private Long csuCreateTime;
	private Long csuUpdateTime;

	// Constructors

	/** default constructor */
	public AbstractCmShortUrl() {
	}

	/** full constructor */
	public AbstractCmShortUrl(String csuCode, String csuUrl,
			Integer csuHitCount, Long csuCreateTime, Long csuUpdateTime) {
		this.csuCode = csuCode;
		this.csuUrl = csuUrl;
		this.csuHitCount = csuHitCount;
		this.csuCreateTime = csuCreateTime;
		this.csuUpdateTime = csuUpdateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "csu_id", unique = true, nullable = false)
	public Long getCsuId() {
		return this.csuId;
	}

	public void setCsuId(Long csuId) {
		this.csuId = csuId;
	}

	@Column(name = "csu_code", length = 128)
	public String getCsuCode() {
		return this.csuCode;
	}

	public void setCsuCode(String csuCode) {
		this.csuCode = csuCode;
	}

	@Column(name = "csu_url", length = 1024)
	public String getCsuUrl() {
		return this.csuUrl;
	}

	public void setCsuUrl(String csuUrl) {
		this.csuUrl = csuUrl;
	}

	@Column(name = "csu_hit_count")
	public Integer getCsuHitCount() {
		return this.csuHitCount;
	}

	public void setCsuHitCount(Integer csuHitCount) {
		this.csuHitCount = csuHitCount;
	}

	@Column(name = "csu_create_time")
	public Long getCsuCreateTime() {
		return this.csuCreateTime;
	}

	public void setCsuCreateTime(Long csuCreateTime) {
		this.csuCreateTime = csuCreateTime;
	}

	@Column(name = "csu_update_time")
	public Long getCsuUpdateTime() {
		return this.csuUpdateTime;
	}

	public void setCsuUpdateTime(Long csuUpdateTime) {
		this.csuUpdateTime = csuUpdateTime;
	}

}