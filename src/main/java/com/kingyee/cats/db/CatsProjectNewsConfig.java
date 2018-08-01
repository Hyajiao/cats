package com.kingyee.cats.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CatsProjectNewsConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_project_news_config")
public class CatsProjectNewsConfig extends AbstractCatsProjectNewsConfig
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsProjectNewsConfig() {
	}

	/** full constructor */
	public CatsProjectNewsConfig(Long cpncCpId, String cpncKeywords,
			Long cpncCreateTime, Long cpncCreateUserId,
			String cpncCreateUserName, Long cpncUpdateTime,
			Long cpncUpdateUserId, String cpncUpdateUserName) {
		super(cpncCpId, cpncKeywords, cpncCreateTime, cpncCreateUserId,
				cpncCreateUserName, cpncUpdateTime, cpncUpdateUserId,
				cpncUpdateUserName);
	}

}
