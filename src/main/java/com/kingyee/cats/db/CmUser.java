package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CmUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cm_user")
public class CmUser extends AbstractCmUser implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CmUser() {
	}

	/** full constructor */
	public CmUser(String cuType, String cuUserName, String cuPassword,
			String cuNickName, String cuRealName, String cuSex, Integer cuAge,
			String cuHeadimg, String cuTelNo, String cuEmail,
			String cuProvince, String cuCity, String cuHospital, String cuDept,
			String cuProfessional, String cuPost, String cuDeptTelNo,
			String cuCertificateImg, Integer cuIsAuthentication,
			Long cuMedliveId, String cuMedliveHeadImg, Double cuBalance,
			Double cuLockMoney, Integer cuIsValid, Long cuCreateTime,
			Long cuUpdateTime) {
		super(cuType, cuUserName, cuPassword, cuNickName, cuRealName, cuSex,
				cuAge, cuHeadimg, cuTelNo, cuEmail, cuProvince, cuCity,
				cuHospital, cuDept, cuProfessional, cuPost, cuDeptTelNo,
				cuCertificateImg, cuIsAuthentication, cuMedliveId,
				cuMedliveHeadImg, cuBalance, cuLockMoney, cuIsValid,
				cuCreateTime, cuUpdateTime);
	}

    // ================= 自己添加 ==================

    private String cuCreateTimeStr;
    private String cuUpdateTimeStr;

    @Transient
    public String getCuCreateTimeStr() {
        cuCreateTimeStr = TimeUtil.longToString(getCuCreateTime(), TimeUtil.FORMAT_DATETIME_FULL);
        return cuCreateTimeStr;
    }

    public void setCuCreateTimeStr(String cuCreateTimeStr) {
        this.cuCreateTimeStr = cuCreateTimeStr;
    }

    @Transient
    public String getCuUpdateTimeStr() {
        cuUpdateTimeStr = TimeUtil.longToString(getCuUpdateTime(), TimeUtil.FORMAT_DATETIME_FULL);
        return cuUpdateTimeStr;
    }

    public void setCuUpdateTimeStr(String cuUpdateTimeStr) {
        this.cuUpdateTimeStr = cuUpdateTimeStr;
    }
}
