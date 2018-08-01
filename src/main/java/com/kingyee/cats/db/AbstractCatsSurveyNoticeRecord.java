package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsSurveyNoticeRecord entity provides the base persistence
 * definition of the CatsSurveyNoticeRecord entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsSurveyNoticeRecord implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long csnrId;
	private Long csnrCsnId;
	private Long csnrCegdId;
	private Long csnrCuId;
	private Integer csnrSendType;
	private Integer csnrSendStatus;
	private Long csnrSendTime;

	// Constructors

	/** default constructor */
	public AbstractCatsSurveyNoticeRecord() {
	}

	/** full constructor */
	public AbstractCatsSurveyNoticeRecord(Long csnrCsnId, Long csnrCegdId,
			Long csnrCuId, Integer csnrSendType, Integer csnrSendStatus,
			Long csnrSendTime) {
		this.csnrCsnId = csnrCsnId;
		this.csnrCegdId = csnrCegdId;
		this.csnrCuId = csnrCuId;
		this.csnrSendType = csnrSendType;
		this.csnrSendStatus = csnrSendStatus;
		this.csnrSendTime = csnrSendTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "csnr_id", unique = true, nullable = false)
	public Long getCsnrId() {
		return this.csnrId;
	}

	public void setCsnrId(Long csnrId) {
		this.csnrId = csnrId;
	}

	@Column(name = "csnr_csn_id")
	public Long getCsnrCsnId() {
		return this.csnrCsnId;
	}

	public void setCsnrCsnId(Long csnrCsnId) {
		this.csnrCsnId = csnrCsnId;
	}

	@Column(name = "csnr_cegd_id")
	public Long getCsnrCegdId() {
		return this.csnrCegdId;
	}

	public void setCsnrCegdId(Long csnrCegdId) {
		this.csnrCegdId = csnrCegdId;
	}

	@Column(name = "csnr_cu_id")
	public Long getCsnrCuId() {
		return this.csnrCuId;
	}

	public void setCsnrCuId(Long csnrCuId) {
		this.csnrCuId = csnrCuId;
	}

	@Column(name = "csnr_send_type")
	public Integer getCsnrSendType() {
		return this.csnrSendType;
	}

	public void setCsnrSendType(Integer csnrSendType) {
		this.csnrSendType = csnrSendType;
	}

	@Column(name = "csnr_send_status")
	public Integer getCsnrSendStatus() {
		return this.csnrSendStatus;
	}

	public void setCsnrSendStatus(Integer csnrSendStatus) {
		this.csnrSendStatus = csnrSendStatus;
	}

	@Column(name = "csnr_send_time")
	public Long getCsnrSendTime() {
		return this.csnrSendTime;
	}

	public void setCsnrSendTime(Long csnrSendTime) {
		this.csnrSendTime = csnrSendTime;
	}

}