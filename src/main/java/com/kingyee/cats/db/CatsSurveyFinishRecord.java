package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CatsSurveyFinishRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_survey_finish_record")
public class CatsSurveyFinishRecord extends AbstractCatsSurveyFinishRecord
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsSurveyFinishRecord() {
	}

	/** full constructor */
	public CatsSurveyFinishRecord(Long csfrCspId, Long csfrMatchFormId,
			Long csfrCegdId, Long csfrCuId, Integer csfrIsFirstAnswer,
			Integer csfrHadIssueReward, Long csfrFinishTime,
			Long csfrRewardIssueUserId, String csfrRewardIssueUserName,
			Long csfrRewardIssueTime) {
		super(csfrCspId, csfrMatchFormId, csfrCegdId, csfrCuId,
				csfrIsFirstAnswer, csfrHadIssueReward, csfrFinishTime,
				csfrRewardIssueUserId, csfrRewardIssueUserName,
				csfrRewardIssueTime);
	}


	private String csfrFinishTimeStr;

    @Transient
    public String getCsfrFinishTimeStr() {
        csfrFinishTimeStr = TimeUtil.longToString(this.getCsfrFinishTime(), TimeUtil.FORMAT_DATE);
        return csfrFinishTimeStr;
    }

    public void setCsfrFinishTimeStr(String csfrFinishTimeStr) {
        this.csfrFinishTimeStr = csfrFinishTimeStr;
    }
}
