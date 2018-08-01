package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsSurveyFinishRecord entity provides the base persistence
 * definition of the CatsSurveyFinishRecord entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsSurveyFinishRecord implements
		java.io.Serializable {

	// Fields

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long csfrId;
	private Long csfrCspId;
	private Long csfrMatchFormId;
	private Long csfrCegdId;
	private Long csfrCuId;
	private Integer csfrIsFirstAnswer;
	private Integer csfrHadIssueReward;
	private Long csfrFinishTime;
	private Long csfrRewardIssueUserId;
	private String csfrRewardIssueUserName;
	private Long csfrRewardIssueTime;

	// Constructors

	/** default constructor */
	public AbstractCatsSurveyFinishRecord() {
	}

	/** full constructor */
	public AbstractCatsSurveyFinishRecord(Long csfrCspId, Long csfrMatchFormId,
			Long csfrCegdId, Long csfrCuId, Integer csfrIsFirstAnswer,
			Integer csfrHadIssueReward, Long csfrFinishTime,
			Long csfrRewardIssueUserId, String csfrRewardIssueUserName,
			Long csfrRewardIssueTime) {
		this.csfrCspId = csfrCspId;
		this.csfrMatchFormId = csfrMatchFormId;
		this.csfrCegdId = csfrCegdId;
		this.csfrCuId = csfrCuId;
		this.csfrIsFirstAnswer = csfrIsFirstAnswer;
		this.csfrHadIssueReward = csfrHadIssueReward;
		this.csfrFinishTime = csfrFinishTime;
		this.csfrRewardIssueUserId = csfrRewardIssueUserId;
		this.csfrRewardIssueUserName = csfrRewardIssueUserName;
		this.csfrRewardIssueTime = csfrRewardIssueTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "csfr_id", unique = true, nullable = false)
	public Long getCsfrId() {
		return this.csfrId;
	}

	public void setCsfrId(Long csfrId) {
		this.csfrId = csfrId;
	}

	@Column(name = "csfr_csp_id")
	public Long getCsfrCspId() {
		return this.csfrCspId;
	}

	public void setCsfrCspId(Long csfrCspId) {
		this.csfrCspId = csfrCspId;
	}

	@Column(name = "csfr_match_form_id")
	public Long getCsfrMatchFormId() {
		return this.csfrMatchFormId;
	}

	public void setCsfrMatchFormId(Long csfrMatchFormId) {
		this.csfrMatchFormId = csfrMatchFormId;
	}

	@Column(name = "csfr_cegd_id")
	public Long getCsfrCegdId() {
		return this.csfrCegdId;
	}

	public void setCsfrCegdId(Long csfrCegdId) {
		this.csfrCegdId = csfrCegdId;
	}

	@Column(name = "csfr_cu_id")
	public Long getCsfrCuId() {
		return this.csfrCuId;
	}

	public void setCsfrCuId(Long csfrCuId) {
		this.csfrCuId = csfrCuId;
	}

	@Column(name = "csfr_is_first_answer")
	public Integer getCsfrIsFirstAnswer() {
		return this.csfrIsFirstAnswer;
	}

	public void setCsfrIsFirstAnswer(Integer csfrIsFirstAnswer) {
		this.csfrIsFirstAnswer = csfrIsFirstAnswer;
	}

	@Column(name = "csfr_had_issue_reward")
	public Integer getCsfrHadIssueReward() {
		return this.csfrHadIssueReward;
	}

	public void setCsfrHadIssueReward(Integer csfrHadIssueReward) {
		this.csfrHadIssueReward = csfrHadIssueReward;
	}

	@Column(name = "csfr_finish_time")
	public Long getCsfrFinishTime() {
		return this.csfrFinishTime;
	}

	public void setCsfrFinishTime(Long csfrFinishTime) {
		this.csfrFinishTime = csfrFinishTime;
	}

	@Column(name = "csfr_reward_issue_user_id")
	public Long getCsfrRewardIssueUserId() {
		return this.csfrRewardIssueUserId;
	}

	public void setCsfrRewardIssueUserId(Long csfrRewardIssueUserId) {
		this.csfrRewardIssueUserId = csfrRewardIssueUserId;
	}

	@Column(name = "csfr_reward_issue_user_name", length = 128)
	public String getCsfrRewardIssueUserName() {
		return this.csfrRewardIssueUserName;
	}

	public void setCsfrRewardIssueUserName(String csfrRewardIssueUserName) {
		this.csfrRewardIssueUserName = csfrRewardIssueUserName;
	}

	@Column(name = "csfr_reward_issue_time")
	public Long getCsfrRewardIssueTime() {
		return this.csfrRewardIssueTime;
	}

	public void setCsfrRewardIssueTime(Long csfrRewardIssueTime) {
		this.csfrRewardIssueTime = csfrRewardIssueTime;
	}

}