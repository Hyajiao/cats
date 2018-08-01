package com.kingyee.cats.db;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CatsExpertGroupDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cats_expert_group_detail")
public class CatsExpertGroupDetail extends AbstractCatsExpertGroupDetail
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public CatsExpertGroupDetail() {
	}

	/** full constructor */
	public CatsExpertGroupDetail(Long cegdCegId, Long cegdMedliveId,
			String cegdRealName, String cegdSex, String cegdHospital,
			String cegdDept, String cegdProfessional, String cegdTelNo,
			String cegdEmail, String cegdAlipayRealName,
			String cegdAlipayUserName, String cegdBankRealName,
			String cegdBankName, String cegdBankNo, Long cegdCreateTime,
			Long cegdCreateUserId, String cegdCreateUserName,
			Long cegdUpdateTime, Long cegdUpdateUserId,
			String cegdUpdateUserName) {
		super(cegdCegId, cegdMedliveId, cegdRealName, cegdSex, cegdHospital,
				cegdDept, cegdProfessional, cegdTelNo, cegdEmail,
				cegdAlipayRealName, cegdAlipayUserName, cegdBankRealName,
				cegdBankName, cegdBankNo, cegdCreateTime, cegdCreateUserId,
				cegdCreateUserName, cegdUpdateTime, cegdUpdateUserId,
				cegdUpdateUserName);
	}

}
