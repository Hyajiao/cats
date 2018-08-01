package com.kingyee.cats.bean;

import com.kingyee.common.util.TimeUtil;

public class FinanceDetailModel{
	//交易类目
	private String tradeCategory;
	//交易金额
	private Double money;
	//提现账号
	private String cardNo;
	//提现方式
	private String cardType;
	//交易时间
	private Long time;
	//交易时间
	private String timeStr;

	public String getTradeCategory() {
		return tradeCategory;
	}

	public void setTradeCategory(String tradeCategory) {
		this.tradeCategory = tradeCategory;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
		this.setTimeStr(TimeUtil.longToString(time,TimeUtil.FORMAT_DATETIME));
	}

	public String getTimeStr() {
		timeStr =  TimeUtil.longToString(this.getTime(),TimeUtil.FORMAT_DATETIME);
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
}
