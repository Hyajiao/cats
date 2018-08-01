package com.kingyee.cats.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CatsWithdrawRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_withdraw_record")
public class CatsWithdrawRecord extends AbstractCatsWithdrawRecord implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsWithdrawRecord() {
	}

	/** full constructor */
	public CatsWithdrawRecord(Long cwrCuId, Integer cwrWithdrawMode,
			Double cwrWithdrawMoney, String cwrRealName, String cwrBankName,
			String cwrBankNo, String cwrTelNo, String cwrAlipayUserName,
			Integer cwrStatus, Long cwrWithdrawTime, Long cwrRemitTime) {
		super(cwrCuId, cwrWithdrawMode, cwrWithdrawMoney, cwrRealName,
				cwrBankName, cwrBankNo, cwrTelNo, cwrAlipayUserName, cwrStatus,
				cwrWithdrawTime, cwrRemitTime);
	}

}
