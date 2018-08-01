package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCmBills entity provides the base persistence definition of the
 * CmBills entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCmBills implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cbId;
	private Long cbCuId;
	private Integer cbBizType;
	private Long cbBizId;
	private String cbBizName;
	private Double cbTradeMoney;
	private Long cbTradeTime;

	// Constructors

	/** default constructor */
	public AbstractCmBills() {
	}

	/** full constructor */
	public AbstractCmBills(Long cbCuId, Integer cbBizType, Long cbBizId,String cbBizName,
			Double cbTradeMoney, Long cbTradeTime) {
		this.cbCuId = cbCuId;
		this.cbBizType = cbBizType;
		this.cbBizId = cbBizId;
		this.cbBizName = cbBizName;
		this.cbTradeMoney = cbTradeMoney;
		this.cbTradeTime = cbTradeTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cb_id", unique = true, nullable = false)
	public Long getCbId() {
		return this.cbId;
	}

	public void setCbId(Long cbId) {
		this.cbId = cbId;
	}

	@Column(name = "cb_cu_id")
	public Long getCbCuId() {
		return this.cbCuId;
	}

	public void setCbCuId(Long cbCuId) {
		this.cbCuId = cbCuId;
	}

	@Column(name = "cb_biz_type")
	public Integer getCbBizType() {
		return this.cbBizType;
	}

	public void setCbBizType(Integer cbBizType) {
		this.cbBizType = cbBizType;
	}

	@Column(name = "cb_biz_id")
	public Long getCbBizId() {
		return this.cbBizId;
	}

	public void setCbBizId(Long cbBizId) {
		this.cbBizId = cbBizId;
	}

	@Column(name = "cb_biz_name", length = 512)
	public String getCbBizName() {
		return cbBizName;
	}

	public void setCbBizName(String cbBizName) {
		this.cbBizName = cbBizName;
	}

	@Column(name = "cb_trade_money", precision = 10)
	public Double getCbTradeMoney() {
		return this.cbTradeMoney;
	}

	public void setCbTradeMoney(Double cbTradeMoney) {
		this.cbTradeMoney = cbTradeMoney;
	}

	@Column(name = "cb_trade_time")
	public Long getCbTradeTime() {
		return this.cbTradeTime;
	}

	public void setCbTradeTime(Long cbTradeTime) {
		this.cbTradeTime = cbTradeTime;
	}

}