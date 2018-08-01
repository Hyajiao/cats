package com.kingyee.cats.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCmWechatUser entity provides the base persistence definition of the
 * CmWechatUser entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCmWechatUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cwuId;
	private Long cwuCuId;
	private Integer cwuSubscribe;
	private String cwuOpenid;
	private String cwuNickname;
	private String cwuSex;
	private String cwuCity;
	private String cwuCountry;
	private String cwuProvince;
	private String cwuLanguage;
	private String cwuHeadimgurl;
	private Long cwuSubscribeTime;
	private String cwuUnionid;
	private String cwuRemark;
	private String cwuGroupid;
	private String cwuTagidList;
	private String cwuHeadimg;
	private Integer cwuScore;
	private Integer cwuIsValid;
	private Long cwuCreateTime;
	private Long cwuUpdateTime;

	// Constructors

	/** default constructor */
	public AbstractCmWechatUser() {
	}

	/** full constructor */
	public AbstractCmWechatUser(Long cwuCuId, Integer cwuSubscribe,
			String cwuOpenid, String cwuNickname, String cwuSex,
			String cwuCity, String cwuCountry, String cwuProvince,
			String cwuLanguage, String cwuHeadimgurl, Long cwuSubscribeTime,
			String cwuUnionid, String cwuRemark, String cwuGroupid,
			String cwuTagidList, String cwuHeadimg, Integer cwuScore,
			Integer cwuIsValid, Long cwuCreateTime, Long cwuUpdateTime) {
		this.cwuCuId = cwuCuId;
		this.cwuSubscribe = cwuSubscribe;
		this.cwuOpenid = cwuOpenid;
		this.cwuNickname = cwuNickname;
		this.cwuSex = cwuSex;
		this.cwuCity = cwuCity;
		this.cwuCountry = cwuCountry;
		this.cwuProvince = cwuProvince;
		this.cwuLanguage = cwuLanguage;
		this.cwuHeadimgurl = cwuHeadimgurl;
		this.cwuSubscribeTime = cwuSubscribeTime;
		this.cwuUnionid = cwuUnionid;
		this.cwuRemark = cwuRemark;
		this.cwuGroupid = cwuGroupid;
		this.cwuTagidList = cwuTagidList;
		this.cwuHeadimg = cwuHeadimg;
		this.cwuScore = cwuScore;
		this.cwuIsValid = cwuIsValid;
		this.cwuCreateTime = cwuCreateTime;
		this.cwuUpdateTime = cwuUpdateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "cwu_id", unique = true, nullable = false)
	public Long getCwuId() {
		return this.cwuId;
	}

	public void setCwuId(Long cwuId) {
		this.cwuId = cwuId;
	}

	@Column(name = "cwu_cu_id")
	public Long getCwuCuId() {
		return this.cwuCuId;
	}

	public void setCwuCuId(Long cwuCuId) {
		this.cwuCuId = cwuCuId;
	}

	@Column(name = "cwu_subscribe")
	public Integer getCwuSubscribe() {
		return this.cwuSubscribe;
	}

	public void setCwuSubscribe(Integer cwuSubscribe) {
		this.cwuSubscribe = cwuSubscribe;
	}

	@Column(name = "cwu_openid", length = 128)
	public String getCwuOpenid() {
		return this.cwuOpenid;
	}

	public void setCwuOpenid(String cwuOpenid) {
		this.cwuOpenid = cwuOpenid;
	}

	@Column(name = "cwu_nickname", length = 128)
	public String getCwuNickname() {
		return this.cwuNickname;
	}

	public void setCwuNickname(String cwuNickname) {
		this.cwuNickname = cwuNickname;
	}

	@Column(name = "cwu_sex", length = 128)
	public String getCwuSex() {
		return this.cwuSex;
	}

	public void setCwuSex(String cwuSex) {
		this.cwuSex = cwuSex;
	}

	@Column(name = "cwu_city", length = 128)
	public String getCwuCity() {
		return this.cwuCity;
	}

	public void setCwuCity(String cwuCity) {
		this.cwuCity = cwuCity;
	}

	@Column(name = "cwu_country", length = 128)
	public String getCwuCountry() {
		return this.cwuCountry;
	}

	public void setCwuCountry(String cwuCountry) {
		this.cwuCountry = cwuCountry;
	}

	@Column(name = "cwu_province", length = 128)
	public String getCwuProvince() {
		return this.cwuProvince;
	}

	public void setCwuProvince(String cwuProvince) {
		this.cwuProvince = cwuProvince;
	}

	@Column(name = "cwu_language", length = 128)
	public String getCwuLanguage() {
		return this.cwuLanguage;
	}

	public void setCwuLanguage(String cwuLanguage) {
		this.cwuLanguage = cwuLanguage;
	}

	@Column(name = "cwu_headimgurl", length = 512)
	public String getCwuHeadimgurl() {
		return this.cwuHeadimgurl;
	}

	public void setCwuHeadimgurl(String cwuHeadimgurl) {
		this.cwuHeadimgurl = cwuHeadimgurl;
	}

	@Column(name = "cwu_subscribe_time")
	public Long getCwuSubscribeTime() {
		return this.cwuSubscribeTime;
	}

	public void setCwuSubscribeTime(Long cwuSubscribeTime) {
		this.cwuSubscribeTime = cwuSubscribeTime;
	}

	@Column(name = "cwu_unionid", length = 128)
	public String getCwuUnionid() {
		return this.cwuUnionid;
	}

	public void setCwuUnionid(String cwuUnionid) {
		this.cwuUnionid = cwuUnionid;
	}

	@Column(name = "cwu_remark", length = 128)
	public String getCwuRemark() {
		return this.cwuRemark;
	}

	public void setCwuRemark(String cwuRemark) {
		this.cwuRemark = cwuRemark;
	}

	@Column(name = "cwu_groupid", length = 128)
	public String getCwuGroupid() {
		return this.cwuGroupid;
	}

	public void setCwuGroupid(String cwuGroupid) {
		this.cwuGroupid = cwuGroupid;
	}

	@Column(name = "cwu_tagid_list", length = 1024)
	public String getCwuTagidList() {
		return this.cwuTagidList;
	}

	public void setCwuTagidList(String cwuTagidList) {
		this.cwuTagidList = cwuTagidList;
	}

	@Column(name = "cwu_headimg", length = 128)
	public String getCwuHeadimg() {
		return this.cwuHeadimg;
	}

	public void setCwuHeadimg(String cwuHeadimg) {
		this.cwuHeadimg = cwuHeadimg;
	}

	@Column(name = "cwu_score")
	public Integer getCwuScore() {
		return this.cwuScore;
	}

	public void setCwuScore(Integer cwuScore) {
		this.cwuScore = cwuScore;
	}

	@Column(name = "cwu_is_valid")
	public Integer getCwuIsValid() {
		return this.cwuIsValid;
	}

	public void setCwuIsValid(Integer cwuIsValid) {
		this.cwuIsValid = cwuIsValid;
	}

	@Column(name = "cwu_create_time")
	public Long getCwuCreateTime() {
		return this.cwuCreateTime;
	}

	public void setCwuCreateTime(Long cwuCreateTime) {
		this.cwuCreateTime = cwuCreateTime;
	}

	@Column(name = "cwu_update_time")
	public Long getCwuUpdateTime() {
		return this.cwuUpdateTime;
	}

	public void setCwuUpdateTime(Long cwuUpdateTime) {
		this.cwuUpdateTime = cwuUpdateTime;
	}

}