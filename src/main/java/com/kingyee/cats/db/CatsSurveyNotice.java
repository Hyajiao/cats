package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CatsSurveyNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_survey_notice")
public class CatsSurveyNotice extends AbstractCatsSurveyNotice implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsSurveyNotice() {
	}

	/** full constructor */
	public CatsSurveyNotice(Long csnCspId, Integer csnSendType,
			Integer csnSendNum, Integer csnSuccessNum, Integer csnSendStatus,
			Long csnCreateUserId, String csnCreateUserName, Long csnCreateTime) {
		super(csnCspId, csnSendType, csnSendNum, csnSuccessNum, csnSendStatus,
				csnCreateUserId, csnCreateUserName, csnCreateTime);
	}


    private String csnCreateTimeStr;
    @Transient
    public String getCsnCreateTimeStr() {
        csnCreateTimeStr = TimeUtil.longToString(this.getCsnCreateTime(),TimeUtil.FORMAT_DATE);
        return csnCreateTimeStr;
    }

    public void setCsnCreateTimeStr(String csnCreateTimeStr) {
        this.csnCreateTimeStr = csnCreateTimeStr;
    }
}
