package com.kingyee.cats.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CmShortUrl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cm_short_url")
public class CmShortUrl extends AbstractCmShortUrl implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CmShortUrl() {
	}

	/** full constructor */
	public CmShortUrl(String csuCode, String csuUrl, Integer csuHitCount,
			Long csuCreateTime, Long csuUpdateTime) {
		super(csuCode, csuUrl, csuHitCount, csuCreateTime, csuUpdateTime);
	}

}
