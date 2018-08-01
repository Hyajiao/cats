package com.kingyee.cats.db;

import com.kingyee.common.util.TimeUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CatsExpertGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_expert_group")
public class CatsExpertGroup extends AbstractCatsExpertGroup implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsExpertGroup() {
	}

	/** full constructor */
	public CatsExpertGroup(String cegExpertGroupName, Integer cegExpertNum,
			String cegMemo, Integer cegIsValid, Long cegCreateTime,
			Long cegCreateUserId, String cegCreateUserName, Long cegUpdateTime,
			Long cegUpdateUserId, String cegUpdateUserName) {
		super(cegExpertGroupName, cegExpertNum, cegMemo, cegIsValid,
				cegCreateTime, cegCreateUserId, cegCreateUserName,
				cegUpdateTime, cegUpdateUserId, cegUpdateUserName);
	}


	private String cegCreateTimeStr;

	@Transient
    public String getCegCreateTimeStr() {
        cegCreateTimeStr = TimeUtil.longToString(this.getCegCreateTime(), TimeUtil.FORMAT_DATE);
        return cegCreateTimeStr;
    }

    public void setCegCreateTimeStr(String cegCreateTimeStr) {
        this.cegCreateTimeStr = cegCreateTimeStr;
    }
}
