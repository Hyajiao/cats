package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCatsSurveyProject entity provides the base persistence definition of
 * the CatsSurveyProject entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCatsSurveyProject implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cspId;
	private Long cspCpId;
	private Long cspCegId;
	private String cspTitle;
	private String cspType;
	private Integer cspNum;
	private Integer cspFinishedNum;
	private Double cspUnitPrice;
	private String cspDuration;
	private Long cspStartDate;
	private Long cspEndDate;
	private String cspSurveyUrl;
	private String cspMemo;
	private String cspBrief;
	private String cspSurveyReportPath;
	private Integer cspIsValid;
	private Long cspCreateTime;
	private Long cspCreateUserId;
	private String cspCreateUserName;
	private Long cspUpdateTime;
	private Long cspUpdateUserId;
	private String cspUpdateUserName;

	// Constructors

	/** default constructor */
	public AbstractCatsSurveyProject() {
	}

	/** full constructor */
	public AbstractCatsSurveyProject(Long cspCpId, Long cspCegId,
			String cspTitle, String cspType, Integer cspNum,
			Integer cspFinishedNum, Double cspUnitPrice, String cspDuration,
			Long cspStartDate, Long cspEndDate, String cspSurveyUrl,
			String cspMemo, String cspBrief, String cspSurveyReportPath,
			Integer cspIsValid, Long cspCreateTime, Long cspCreateUserId,
			String cspCreateUserName, Long cspUpdateTime, Long cspUpdateUserId,
			String cspUpdateUserName) {
		this.cspCpId = cspCpId;
		this.cspCegId = cspCegId;
		this.cspTitle = cspTitle;
		this.cspType = cspType;
		this.cspNum = cspNum;
		this.cspFinishedNum = cspFinishedNum;
		this.cspUnitPrice = cspUnitPrice;
		this.cspDuration = cspDuration;
		this.cspStartDate = cspStartDate;
		this.cspEndDate = cspEndDate;
		this.cspSurveyUrl = cspSurveyUrl;
		this.cspMemo = cspMemo;
		this.cspBrief = cspBrief;
		this.cspSurveyReportPath = cspSurveyReportPath;
		this.cspIsValid = cspIsValid;
		this.cspCreateTime = cspCreateTime;
		this.cspCreateUserId = cspCreateUserId;
		this.cspCreateUserName = cspCreateUserName;
		this.cspUpdateTime = cspUpdateTime;
		this.cspUpdateUserId = cspUpdateUserId;
		this.cspUpdateUserName = cspUpdateUserName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "csp_id", unique = true, nullable = false)
	public Long getCspId() {
		return this.cspId;
	}

	public void setCspId(Long cspId) {
		this.cspId = cspId;
	}

	@Column(name = "csp_cp_id")
	public Long getCspCpId() {
		return this.cspCpId;
	}

	public void setCspCpId(Long cspCpId) {
		this.cspCpId = cspCpId;
	}

	@Column(name = "csp_ceg_id")
	public Long getCspCegId() {
		return this.cspCegId;
	}

	public void setCspCegId(Long cspCegId) {
		this.cspCegId = cspCegId;
	}

	@Column(name = "csp_title", length = 128)
	public String getCspTitle() {
		return this.cspTitle;
	}

	public void setCspTitle(String cspTitle) {
		this.cspTitle = cspTitle;
	}

	@Column(name = "csp_type", length = 128)
	public String getCspType() {
		return this.cspType;
	}

	public void setCspType(String cspType) {
		this.cspType = cspType;
	}

	@Column(name = "csp_num")
	public Integer getCspNum() {
		return this.cspNum;
	}

	public void setCspNum(Integer cspNum) {
		this.cspNum = cspNum;
	}

	@Column(name = "csp_finished_num")
	public Integer getCspFinishedNum() {
		return this.cspFinishedNum;
	}

	public void setCspFinishedNum(Integer cspFinishedNum) {
		this.cspFinishedNum = cspFinishedNum;
	}

	@Column(name = "csp_unit_price", precision = 10)
	public Double getCspUnitPrice() {
		return this.cspUnitPrice;
	}

	public void setCspUnitPrice(Double cspUnitPrice) {
		this.cspUnitPrice = cspUnitPrice;
	}

	@Column(name = "csp_duration", length = 128)
	public String getCspDuration() {
		return this.cspDuration;
	}

	public void setCspDuration(String cspDuration) {
		this.cspDuration = cspDuration;
	}

	@Column(name = "csp_start_date")
	public Long getCspStartDate() {
		return this.cspStartDate;
	}

	public void setCspStartDate(Long cspStartDate) {
		this.cspStartDate = cspStartDate;
	}

	@Column(name = "csp_end_date")
	public Long getCspEndDate() {
		return this.cspEndDate;
	}

	public void setCspEndDate(Long cspEndDate) {
		this.cspEndDate = cspEndDate;
	}

	@Column(name = "csp_survey_url", length = 512)
	public String getCspSurveyUrl() {
		return this.cspSurveyUrl;
	}

	public void setCspSurveyUrl(String cspSurveyUrl) {
		this.cspSurveyUrl = cspSurveyUrl;
	}

	@Column(name = "csp_memo", length = 1024)
	public String getCspMemo() {
		return this.cspMemo;
	}

	public void setCspMemo(String cspMemo) {
		this.cspMemo = cspMemo;
	}

	@Column(name = "csp_brief", length = 32767)
	public String getCspBrief() {
		return this.cspBrief;
	}

	public void setCspBrief(String cspBrief) {
		this.cspBrief = cspBrief;
	}

	@Column(name = "csp_survey_report_path", length = 128)
	public String getCspSurveyReportPath() {
		return this.cspSurveyReportPath;
	}

	public void setCspSurveyReportPath(String cspSurveyReportPath) {
		this.cspSurveyReportPath = cspSurveyReportPath;
	}

	@Column(name = "csp_is_valid")
	public Integer getCspIsValid() {
		return this.cspIsValid;
	}

	public void setCspIsValid(Integer cspIsValid) {
		this.cspIsValid = cspIsValid;
	}

	@Column(name = "csp_create_time")
	public Long getCspCreateTime() {
		return this.cspCreateTime;
	}

	public void setCspCreateTime(Long cspCreateTime) {
		this.cspCreateTime = cspCreateTime;
	}

	@Column(name = "csp_create_user_id")
	public Long getCspCreateUserId() {
		return this.cspCreateUserId;
	}

	public void setCspCreateUserId(Long cspCreateUserId) {
		this.cspCreateUserId = cspCreateUserId;
	}

	@Column(name = "csp_create_user_name", length = 128)
	public String getCspCreateUserName() {
		return this.cspCreateUserName;
	}

	public void setCspCreateUserName(String cspCreateUserName) {
		this.cspCreateUserName = cspCreateUserName;
	}

	@Column(name = "csp_update_time")
	public Long getCspUpdateTime() {
		return this.cspUpdateTime;
	}

	public void setCspUpdateTime(Long cspUpdateTime) {
		this.cspUpdateTime = cspUpdateTime;
	}

	@Column(name = "csp_update_user_id")
	public Long getCspUpdateUserId() {
		return this.cspUpdateUserId;
	}

	public void setCspUpdateUserId(Long cspUpdateUserId) {
		this.cspUpdateUserId = cspUpdateUserId;
	}

	@Column(name = "csp_update_user_name", length = 128)
	public String getCspUpdateUserName() {
		return this.cspUpdateUserName;
	}

	public void setCspUpdateUserName(String cspUpdateUserName) {
		this.cspUpdateUserName = cspUpdateUserName;
	}

}