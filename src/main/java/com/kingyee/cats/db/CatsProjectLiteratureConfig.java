package com.kingyee.cats.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CatsProjectLiteratureConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_project_literature_config")
public class CatsProjectLiteratureConfig extends
		AbstractCatsProjectLiteratureConfig implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsProjectLiteratureConfig() {
	}

	/** full constructor */
	public CatsProjectLiteratureConfig(Long cplcCpId, String cplcTitle,
			String cplcAuthor, String cplcJour, String cplcKeyword,
			String cplcAbstract, String cplcPublishDateStart,
			String cplcPublishDateEnd, Long cplcCreateTime,
			Long cplcCreateUserId, String cplcCreateUserName,
			Long cplcUpdateTime, Long cplcUpdateUserId,
			String cplcUpdateUserName) {
		super(cplcCpId, cplcTitle, cplcAuthor, cplcJour, cplcKeyword,
				cplcAbstract, cplcPublishDateStart, cplcPublishDateEnd,
				cplcCreateTime, cplcCreateUserId, cplcCreateUserName,
				cplcUpdateTime, cplcUpdateUserId, cplcUpdateUserName);
	}

}
