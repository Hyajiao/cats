package com.kingyee.cats.dao.admin;

import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.db.CatsSurveyFinishRecord;
import com.kingyee.cats.db.CatsSurveyProject;
import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.common.db.CommonDao;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 药企管理
 *
 * @author nmy
 * 2018年05月16日
 */
@Repository
public class AdminEntDao extends CommonDao {

	/**
	 * 获取用户对应的项目列表
	 * @return
	 */
	public POJOPageInfo getUserProjectList(SearchBean searchBean, POJOPageInfo pageInfo) {
		Map<String, Object> parp = searchBean.getParps();
		StringBuilder hql = new StringBuilder();
		hql.append("FROM CatsProjectEnterpriseUserLink AS pul,CatsProject as p WHERE 1=1 ");
		hql.append("AND pul.cpeulCpId = p.cpId ");
		hql.append("AND pul.cpeulCauId = :userId ");
		hql.append("AND p.cpStartTime <= :useTime ");
		hql.append("AND p.cpEndTime >= :useTime ");

		Long count = dao.createCountQuery("SELECT COUNT(*) "+hql.toString(), parp);
		if (count == null || count.intValue() == 0) {
			pageInfo.setItems(new ArrayList<CmWechatUser>());
			pageInfo.setCount(0);
			return pageInfo;
		}
		String order = "ORDER BY pul.cpeulCreateTime DESC";
		List<Object[]> list = dao.createQuery(hql.toString()+order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
		pageInfo.setCount(count.intValue());
		pageInfo.setItems(list);

		return pageInfo;
	}

	/**
	 * 项目明细
	 * @param projectId 项目表主键
	 * @return
	 */
	public List<CatsSurveyFinishRecord> getSurveyFinishRecordByProjectId(Long projectId,Long adminUserId) {
		Map<String, Object> parp = new HashMap<String, Object>();
		parp.put("projectId",projectId);
		parp.put("userId",adminUserId);
		StringBuilder hql = new StringBuilder();
		hql.append("FROM CatsProjectEnterpriseUserLink AS pul,CatsProject as p WHERE 1=1 ");
		hql.append("AND pul.cpeulCpId = p.cpId ");
		hql.append("AND pul.cpeulCauId = :userId ");
		hql.append("AND pul.cpeulCpId = :projectId ");

		Long count = dao.createCountQuery("SELECT COUNT(*) "+hql.toString(), parp);
		if (count == null || count.intValue() == 0) {
			return null;
		}
		String order = "ORDER BY pul.cpeulCreateTime DESC";
		return dao.createQuery(hql.toString()+order, parp);
	}

	/**
	 * 调研报告列表
	 * @return
	 */
	public List<CatsSurveyProject> getSurveyFinishProjectList(Long projectId) {
		Map<String, Object> parp = new HashMap<String, Object>();
		parp.put("projectId",projectId);
		StringBuilder hql = new StringBuilder();
		hql.append("FROM CatsSurveyProject AS sp WHERE 1=1 ");
		hql.append("AND sp.cspCpId = :projectId ");
		Long count = dao.createCountQuery("SELECT COUNT(*) "+hql.toString(), parp);
		if (count == null || count.intValue() == 0) {
			return null;
		}
		String order = "ORDER BY sp.cspCreateTime DESC";
		return dao.createQuery(hql.toString()+order, parp);
	}
}
