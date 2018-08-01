package com.kingyee.cats.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CmSmsCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cm_sms_code")
public class CmSmsCode extends AbstractCmSmsCode implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CmSmsCode() {
	}

	/** full constructor */
	public CmSmsCode(String cscTelNo, Long cscUserId, String cscType,
			String cscCode, Long cscInvalidDate, String cscIpAddress,
			Integer cscIsUsed, Long cscCreateDate) {
		super(cscTelNo, cscUserId, cscType, cscCode, cscInvalidDate,
				cscIpAddress, cscIsUsed, cscCreateDate);
	}

}
