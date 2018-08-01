package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CatsSurveyProject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_survey_project")
public class CatsSurveyProject extends AbstractCatsSurveyProject implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsSurveyProject() {
	}

	/** full constructor */
	public CatsSurveyProject(Long cspCpId, Long cspCegId, String cspTitle,
			String cspType, Integer cspNum, Integer cspFinishedNum,
			Double cspUnitPrice, String cspDuration, Long cspStartDate,
			Long cspEndDate, String cspSurveyUrl, String cspMemo,
			String cspBrief, String cspSurveyReportPath, Integer cspIsValid,
			Long cspCreateTime, Long cspCreateUserId, String cspCreateUserName,
			Long cspUpdateTime, Long cspUpdateUserId, String cspUpdateUserName) {
		super(cspCpId, cspCegId, cspTitle, cspType, cspNum, cspFinishedNum,
				cspUnitPrice, cspDuration, cspStartDate, cspEndDate,
				cspSurveyUrl, cspMemo, cspBrief, cspSurveyReportPath,
				cspIsValid, cspCreateTime, cspCreateUserId, cspCreateUserName,
				cspUpdateTime, cspUpdateUserId, cspUpdateUserName);
	}


	private Integer cspUnitPriceInt;
	private String cspStartDateStr;
	private String cspEndDateStr;
	private String cspCreateTimeStr;
	//完成时间
    private String finishTimeStr;
    //是否发放奖励 0：否 1：是
    private Integer csfrHadIssueReward;
    //是否已参与 0 未参与 1 已参与
    private Integer finished;


    @Transient
    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    @Transient
    public Integer getCspUnitPriceInt() {
        if(this.getCspUnitPrice() != null){
            cspUnitPriceInt = this.getCspUnitPrice().intValue();
        }
        return cspUnitPriceInt;
    }

    public void setCspUnitPriceInt(Integer cspUnitPriceInt) {
        this.cspUnitPriceInt = cspUnitPriceInt;
    }

    @Transient
    public String getCspStartDateStr() {
        cspStartDateStr = TimeUtil.longToString(this.getCspStartDate(),TimeUtil.FORMAT_DATE);
        return cspStartDateStr;
    }

    public void setCspStartDateStr(String cspStartDateStr) {
        this.cspStartDateStr = cspStartDateStr;
        this.setCspStartDate(TimeUtil.stringToLong(this.cspStartDateStr, TimeUtil.FORMAT_DATE));
    }
    @Transient
    public String getCspEndDateStr() {
        cspEndDateStr = TimeUtil.longToString(this.getCspEndDate(),TimeUtil.FORMAT_DATE);
        return cspEndDateStr;
    }

    public void setCspEndDateStr(String cspEndDateStr) {
        this.cspEndDateStr = cspEndDateStr;
        this.setCspEndDate(TimeUtil.stringToLong(this.cspEndDateStr, TimeUtil.FORMAT_DATE));
    }
    @Transient
    public String getCspCreateTimeStr() {
        cspCreateTimeStr = TimeUtil.longToString(this.getCspCreateTime(),TimeUtil.FORMAT_DATE);
        return cspCreateTimeStr;
    }

    public void setCspCreateTimeStr(String cspCreateTimeStr) {
        this.cspCreateTimeStr = cspCreateTimeStr;
            }

    @Transient
    public String getFinishTimeStr() {
        return finishTimeStr;
    }

    public void setFinishTimeStr(String finishTimeStr) {
        this.finishTimeStr = finishTimeStr;
    }

    @Transient
    public Integer getCsfrHadIssueReward() {
        return csfrHadIssueReward;
    }

    public void setCsfrHadIssueReward(Integer csfrHadIssueReward) {
        this.csfrHadIssueReward = csfrHadIssueReward;
    }
}
