package com.kingyee.cats.dao.admin;

import com.kingyee.cats.db.CatsWithdrawRecord;
import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.common.db.CommonDao;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 财务管理
 *
 * @author nmy
 * 2018年05月16日
 */
@Repository
public class AdminFinanceDao extends CommonDao {

	/**
	 * 取得财务列表
	 * @param searchBean
	 * @param pageInfo
	 * @return
	 */
	public POJOPageInfo getFinanceList(SearchBean searchBean, POJOPageInfo pageInfo) {
		Map<String, Object> parp = searchBean.getParps();
		StringBuilder hql = new StringBuilder();
		hql.append("FROM CatsWithdrawRecord AS r,CmUser AS u WHERE r.cwrCuId = u.cuId ");

		if(parp.get("keyword") != null&& StringUtils.isNotEmpty(parp.get("keyword").toString())){
			hql.append(" AND r.cwrRealName LIKE :keyword ");
		}
		if(parp.get("state") != null){
			hql.append(" AND r.cwrStatus =:state ");
		}
		if(parp.get("txState") != null){
			if((Integer)parp.get("txState") == 0){
				hql.append(" AND r.cwrAlipayUserName IS NOT NULL ");
			}else if((Integer)parp.get("txState") == 1){
				hql.append(" AND r.cwrAlipayUserName IS NULL ");
			}
		}
		if(parp.get("startDate") != null){
			hql.append(" AND r.cwrWithdrawTime >=:startDate ");
		}
		if(parp.get("endDate") != null){
			hql.append(" AND r.cwrWithdrawTime <:endDate ");
		}

		Long count = dao.createCountQuery("SELECT COUNT(*) "+hql.toString(), parp);
		if (count == null || count.intValue() == 0) {
			pageInfo.setItems(new ArrayList<CatsWithdrawRecord>());
			pageInfo.setCount(0);
			return pageInfo;
		}

		String order = "ORDER BY r.cwrWithdrawTime DESC";
		List<CatsWithdrawRecord> list = dao.createQuery(hql.toString()+order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
		pageInfo.setCount(count.intValue());
		pageInfo.setItems(list);
		return pageInfo;
	}

	/**
	 * 用户账单明细列表
	 * @param searchBean
	 * @param pageInfo
	 * @return
	 */
	public POJOPageInfo getFinanceDetailList(SearchBean searchBean, POJOPageInfo pageInfo) {
		Map<String, Object> parp = searchBean.getParps();
		StringBuilder hql = new StringBuilder();
		StringBuffer hqlCount = new StringBuffer();
		hqlCount.append("select count(*) from ( ");
		hql.append("select '提现' as bizName, 1 as type,c.cwr_id as id,c.cwr_withdraw_money as money,IFNULL(c.cwr_bank_no,-1) as bankno,IFNULL(c.cwr_alipay_user_name,-1) as alipay,c.cwr_withdraw_time as time ");
		hql.append("from cats_withdraw_record as c , cm_bills as b where 1=1 ");
		hql.append("and b.cb_cu_id = :userId ");
		hql.append("and c.cwr_cu_id = b.cb_cu_id ");
		hql.append("and b.cb_biz_type = 1 ");
		hql.append("and b.cb_biz_id = c.cwr_id ");

		hql.append("union all ");

		hql.append("select b1.cb_biz_name as bizName,0 as type, b1.cb_id as id,b1.cb_trade_money as money,-1 as bankno,-1 as alipay ,b1.cb_trade_time as time ");
		hql.append("from cm_bills as b1 where 1=1 ");
		hql.append("and b1.cb_cu_id = :userId ");
		hql.append("and b1.cb_biz_type = 0 ");

		hqlCount.append(hql);
		hqlCount.append(") as t ");

		Long count = dao.createSQLCountQuery(hqlCount.toString(),parp);
		if (count == null || count.intValue() == 0) {
			pageInfo.setItems(new ArrayList<Object[]>());
			pageInfo.setCount(0);
			return pageInfo;
		}

		hql.append("order by time desc ");
		List<Object[]> list = dao.createSQLQuery(hql.toString(), parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
		pageInfo.setCount(count.intValue());
		pageInfo.setItems(list);
		return pageInfo;
	}

	//查询用户是否绑定微信
	public List<CmWechatUser> getWechatUserByUserId(Long userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("FROM CmWechatUser AS t WHERE t.cwuCuId = "+userId);
		return dao.createQuery(hql.toString());
	}
}
