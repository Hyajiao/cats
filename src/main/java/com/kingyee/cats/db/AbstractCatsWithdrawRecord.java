package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

/**
 * AbstractCatsWithdrawRecord entity provides the base persistence definition of
 * the CatsWithdrawRecord entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsWithdrawRecord implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cwrId;
	private Long cwrCuId;
	private Integer cwrWithdrawMode;
	private Double cwrWithdrawMoney;
	private String cwrRealName;
	private String cwrBankName;
	private String cwrBankNo;
	private String cwrTelNo;
	private String cwrAlipayUserName;
	private Integer cwrStatus;
	private Long cwrWithdrawTime;
	private Long cwrRemitTime;

	// Constructors

	/** default constructor */
	public AbstractCatsWithdrawRecord() {
	}

	/** full constructor */
	public AbstractCatsWithdrawRecord(Long cwrCuId, Integer cwrWithdrawMode,
			Double cwrWithdrawMoney, String cwrRealName, String cwrBankName,
			String cwrBankNo, String cwrTelNo, String cwrAlipayUserName,
			Integer cwrStatus, Long cwrWithdrawTime, Long cwrRemitTime) {
		this.cwrCuId = cwrCuId;
		this.cwrWithdrawMode = cwrWithdrawMode;
		this.cwrWithdrawMoney = cwrWithdrawMoney;
		this.cwrRealName = cwrRealName;
		this.cwrBankName = cwrBankName;
		this.cwrBankNo = cwrBankNo;
		this.cwrTelNo = cwrTelNo;
		this.cwrAlipayUserName = cwrAlipayUserName;
		this.cwrStatus = cwrStatus;
		this.cwrWithdrawTime = cwrWithdrawTime;
		this.cwrRemitTime = cwrRemitTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cwr_id", unique = true, nullable = false)
	public Long getCwrId() {
		return this.cwrId;
	}

	public void setCwrId(Long cwrId) {
		this.cwrId = cwrId;
	}

	@Column(name = "cwr_cu_id")
	public Long getCwrCuId() {
		return this.cwrCuId;
	}

	public void setCwrCuId(Long cwrCuId) {
		this.cwrCuId = cwrCuId;
	}

	@Column(name = "cwr_withdraw_mode")
	public Integer getCwrWithdrawMode() {
		return this.cwrWithdrawMode;
	}

	public void setCwrWithdrawMode(Integer cwrWithdrawMode) {
		this.cwrWithdrawMode = cwrWithdrawMode;
	}

	@Column(name = "cwr_withdraw_money", precision = 10)
	public Double getCwrWithdrawMoney() {
		return this.cwrWithdrawMoney;
	}

	public void setCwrWithdrawMoney(Double cwrWithdrawMoney) {
		this.cwrWithdrawMoney = cwrWithdrawMoney;
	}

	@Column(name = "cwr_real_name", length = 128)
	public String getCwrRealName() {
		return this.cwrRealName;
	}

	public void setCwrRealName(String cwrRealName) {
		this.cwrRealName = cwrRealName;
	}

	@Column(name = "cwr_bank_name", length = 128)
	public String getCwrBankName() {
		return this.cwrBankName;
	}

	public void setCwrBankName(String cwrBankName) {
		this.cwrBankName = cwrBankName;
	}

	@Column(name = "cwr_bank_no", length = 128)
	public String getCwrBankNo() {
		return this.cwrBankNo;
	}

	public void setCwrBankNo(String cwrBankNo) {
		this.cwrBankNo = cwrBankNo;
	}

	@Column(name = "cwr_tel_no", length = 128)
	public String getCwrTelNo() {
		return this.cwrTelNo;
	}

	public void setCwrTelNo(String cwrTelNo) {
		this.cwrTelNo = cwrTelNo;
	}

	@Column(name = "cwr_alipay_user_name", length = 128)
	public String getCwrAlipayUserName() {
		return this.cwrAlipayUserName;
	}

	public void setCwrAlipayUserName(String cwrAlipayUserName) {
		this.cwrAlipayUserName = cwrAlipayUserName;
	}

	@Column(name = "cwr_status")
	public Integer getCwrStatus() {
		return this.cwrStatus;
	}

	public void setCwrStatus(Integer cwrStatus) {
		this.cwrStatus = cwrStatus;
	}

	@Column(name = "cwr_withdraw_time")
	public Long getCwrWithdrawTime() {
		return this.cwrWithdrawTime;
	}

	public void setCwrWithdrawTime(Long cwrWithdrawTime) {
		this.cwrWithdrawTime = cwrWithdrawTime;
	}

	@Column(name = "cwr_remit_time")
	public Long getCwrRemitTime() {
		return this.cwrRemitTime;
	}

	public void setCwrRemitTime(Long cwrRemitTime) {
		this.cwrRemitTime = cwrRemitTime;
	}

	private String cwrRemitTimeStr;

	@Transient
	public String getCwrRemitTimeStr() {
		cwrRemitTimeStr = TimeUtil.longToString(this.getCwrRemitTime(), TimeUtil.FORMAT_DATETIME);
		return cwrRemitTimeStr;
	}

	public void setCwrRemitTimeStr(String cwrRemitTimeStr) {
		this.cwrRemitTimeStr = cwrRemitTimeStr;
	}

	private String cwrWithdrawTimeStr;
	@Transient
	public String getCwrWithdrawTimeStr() {
		cwrWithdrawTimeStr = TimeUtil.longToString(this.getCwrWithdrawTime(), TimeUtil.FORMAT_DATETIME);
		return cwrWithdrawTimeStr;
	}
	public void setCwrWithdrawTimeStr(String cwrWithdrawTimeStr) {
		this.cwrWithdrawTimeStr = cwrWithdrawTimeStr;
	}

	private String tixianState;
	@Transient
	public String getTixianState() {
		if(StringUtils.isNotEmpty(this.getCwrBankName()) && StringUtils.isNotEmpty(this.getCwrBankNo())){
			tixianState = "银行卡";
		}else if(StringUtils.isNotEmpty(this.getCwrAlipayUserName())){
			tixianState = "支付宝";
		}
		return tixianState;
	}

	public void setTixianState(String tixianState) {
		this.tixianState = tixianState;
	}
}