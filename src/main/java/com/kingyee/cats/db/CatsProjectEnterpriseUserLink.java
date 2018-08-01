package com.kingyee.cats.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CatsProjectEnterpriseUserLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_project_enterprise_user_link")
public class CatsProjectEnterpriseUserLink extends
		AbstractCatsProjectEnterpriseUserLink implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsProjectEnterpriseUserLink() {
	}

	/** full constructor */
	public CatsProjectEnterpriseUserLink(Long cpeulCpId, Long cpeulCauId,
			Long cpeulCreateTime, Long cpeulCreateUserId,
			String cpeulCreateUserName, Long cpeulUpdateTime,
			Long cpeulUpdateUserId, String cpeulUpdateUserName) {
		super(cpeulCpId, cpeulCauId, cpeulCreateTime, cpeulCreateUserId,
				cpeulCreateUserName, cpeulUpdateTime, cpeulUpdateUserId,
				cpeulUpdateUserName);
	}

}
