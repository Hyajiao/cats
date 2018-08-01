package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CatsProject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_project")
public class CatsProject extends AbstractCatsProject implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsProject() {
	}

	/** full constructor */
	public CatsProject(String cpProjectName, String cpCompanyName,
			String cpProjectMemo, Long cpStartTime, Long cpEndTime,
			Integer cpIsValid, Long cpCreateTime, Long cpCreateUserId,
			String cpCreateUserName, Long cpUpdateTime, Long cpUpdateUserId,
			String cpUpdateUserName) {
		super(cpProjectName, cpCompanyName, cpProjectMemo, cpStartTime,
				cpEndTime, cpIsValid, cpCreateTime, cpCreateUserId,
				cpCreateUserName, cpUpdateTime, cpUpdateUserId,
				cpUpdateUserName);
	}

    /**
     * 创建时间 字符串类型
     */
    private String cpStartTimeStr;
    private String cpEndTimeStr;
    private String cpCreateTimeStr;

    @Transient
    public String getCpStartTimeStr() {
        cpStartTimeStr = TimeUtil.longToString(this.getCpStartTime(), TimeUtil.FORMAT_DATE);
        return cpStartTimeStr;
    }

    public void setCpStartTimeStr(String cpStartTimeStr) {
        this.cpStartTimeStr = cpStartTimeStr;
        this.setCpStartTime(TimeUtil.stringToLong(this.cpStartTimeStr, TimeUtil.FORMAT_DATE));
    }

    @Transient
    public String getCpEndTimeStr() {
        cpEndTimeStr = TimeUtil.longToString(this.getCpEndTime(), TimeUtil.FORMAT_DATE);
        return cpEndTimeStr;
    }

    public void setCpEndTimeStr(String cpEndTimeStr) {
        this.cpEndTimeStr = cpEndTimeStr;
        this.setCpEndTime(TimeUtil.stringToLong(this.cpEndTimeStr, TimeUtil.FORMAT_DATE));
    }
    @Transient
    public String getCpCreateTimeStr() {
        cpCreateTimeStr = TimeUtil.longToString(this.getCpCreateTime(), TimeUtil.FORMAT_DATE);
        return cpCreateTimeStr;
    }

    public void setCpCreateTimeStr(String cpCreateTimeStr) {
        this.cpCreateTimeStr = cpCreateTimeStr;
    }
}
