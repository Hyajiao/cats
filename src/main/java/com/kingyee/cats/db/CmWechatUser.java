package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CmWechatUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cm_wechat_user")
public class CmWechatUser extends AbstractCmWechatUser implements
		java.io.Serializable {

    // Constructors

	/** default constructor */
	public CmWechatUser() {
	}

	/** full constructor */
	public CmWechatUser(Long cwuCuId, Integer cwuSubscribe, String cwuOpenid,
			String cwuNickname, String cwuSex, String cwuCity,
			String cwuCountry, String cwuProvince, String cwuLanguage,
			String cwuHeadimgurl, Long cwuSubscribeTime, String cwuUnionid,
			String cwuRemark, String cwuGroupid, String cwuTagidList,
			String cwuHeadimg, Integer cwuScore, Integer cwuIsValid,
			Long cwuCreateTime, Long cwuUpdateTime) {
		super(cwuCuId, cwuSubscribe, cwuOpenid, cwuNickname, cwuSex, cwuCity,
				cwuCountry, cwuProvince, cwuLanguage, cwuHeadimgurl,
				cwuSubscribeTime, cwuUnionid, cwuRemark, cwuGroupid,
				cwuTagidList, cwuHeadimg, cwuScore, cwuIsValid, cwuCreateTime,
				cwuUpdateTime);
	}

	private String cwuSubscribeTimeStr;

    @Transient
    public String getCwuSubscribeTimeStr() {
        if(cwuSubscribeTimeStr == null && getCwuSubscribeTime() != null){
            cwuSubscribeTimeStr = TimeUtil.longToString(this.getCwuSubscribeTime()*1000, TimeUtil.FORMAT_DATE);
        }
        return cwuSubscribeTimeStr;
    }

    public void setCwuSubscribeTimeStr(String cwuSubscribeTimeStr) {
        this.cwuSubscribeTimeStr = cwuSubscribeTimeStr;
    }

}
