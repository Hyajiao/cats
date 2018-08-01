package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CmBills entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cm_bills")
public class CmBills extends AbstractCmBills implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CmBills() {
	}

	/** full constructor */
	public CmBills(Long cbCuId, Integer cbBizType, Long cbBizId,String cbBizName,
			Double cbTradeMoney, Long cbTradeTime) {
		super(cbCuId, cbBizType, cbBizId,cbBizName, cbTradeMoney, cbTradeTime);
	}


	private String cbTradeTimeStr;

	@Transient
    public String getCbTradeTimeStr() {
        cbTradeTimeStr = TimeUtil.longToString(this.getCbTradeTime(),TimeUtil.FORMAT_DATE);
        return cbTradeTimeStr;
    }

    public void setCbTradeTimeStr(String cbTradeTimeStr) {
        this.cbTradeTimeStr = cbTradeTimeStr;
    }
}
