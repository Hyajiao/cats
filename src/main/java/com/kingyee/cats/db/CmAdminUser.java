package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CmAdminUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cm_admin_user")
public class CmAdminUser extends AbstractCmAdminUser implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CmAdminUser() {
	}

	/** full constructor */
	public CmAdminUser(String auShowName, Integer auSex, Integer auAge,
			String auTel, String auEmail, String auPostCode, String auAddress,
			String auUserName, String auPassword, Long auRole, String auZone,
			Integer auIsValid, Long auCreateDate, Long auCreateUserId,
			String auCreateUserName, Long auUpdateUserId,
			String auUpdateUserName, Long auUpdateDate) {
		super(auShowName, auSex, auAge, auTel, auEmail, auPostCode, auAddress,
				auUserName, auPassword, auRole, auZone, auIsValid,
				auCreateDate, auCreateUserId, auCreateUserName, auUpdateUserId,
				auUpdateUserName, auUpdateDate);
	}


    /**
     * 创建时间 字符串类型
     */
    private String auCreateDateStr;
    /**
     * 角色名称
     */
    private String roleName;

    @Transient
    public String getAuCreateDateStr() {
        auCreateDateStr = TimeUtil.longToString(this.getAuCreateDate(),TimeUtil.FORMAT_DATE);
        return auCreateDateStr;
    }
    public void setAuCreateDateStr(String auCreateDateStr) {
        this.auCreateDateStr = auCreateDateStr;
    }

    @Transient
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
