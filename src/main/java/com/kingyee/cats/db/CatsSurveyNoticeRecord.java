package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CatsSurveyNoticeRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_survey_notice_record")
public class CatsSurveyNoticeRecord extends AbstractCatsSurveyNoticeRecord
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsSurveyNoticeRecord() {
	}

	/** full constructor */
	public CatsSurveyNoticeRecord(Long csnrCsnId, Long csnrCegdId,
			Long csnrCuId, Integer csnrSendType, Integer csnrSendStatus,
			Long csnrSendTime) {
		super(csnrCsnId, csnrCegdId, csnrCuId, csnrSendType, csnrSendStatus,
				csnrSendTime);
	}


	private String csnrSendTimeStr;

	@Transient
    public String getCsnrSendTimeStr() {
        csnrSendTimeStr = TimeUtil.longToString(this.getCsnrSendTime(),TimeUtil.FORMAT_DATETIME);
        return csnrSendTimeStr;
    }

    public void setCsnrSendTimeStr(String csnrSendTimeStr) {
        this.csnrSendTimeStr = csnrSendTimeStr;
    }
}
