package com.kingyee.cats.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CatsPayeeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_payee_info")
public class CatsPayeeInfo extends AbstractCatsPayeeInfo implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsPayeeInfo() {
	}

	/** full constructor */
	public CatsPayeeInfo(Long cpiCuId, Integer cpiWithdrawMode,
			String cpiRealName, String cpiBankName, String cpiBankNo,
			String cpiTelNo, String cpiAlipayUserName, Integer cpiIsDefault,
			Long cpiCreateTime, Long cpiUpdateTime) {
		super(cpiCuId, cpiWithdrawMode, cpiRealName, cpiBankName, cpiBankNo,
				cpiTelNo, cpiAlipayUserName, cpiIsDefault, cpiCreateTime,
				cpiUpdateTime);
	}

}
