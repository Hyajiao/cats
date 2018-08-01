package com.kingyee.cats.dao.wechat;

import com.kingyee.cats.db.CmBills;
import com.kingyee.common.db.CommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hanyajiao
 */

@Repository
public class BillDao extends CommonDao{

    /**
     * 获取用户的提现记录
     * @param userId
     */
    public List<CmBills> getRecordList(Long userId) {
        Map<String, Object> map = new HashMap();
        map.put("userId",userId);
        StringBuffer sb = new StringBuffer();
        sb.append("  FROM CmBills WHERE  cbCuId=:userId");
        return dao.createQuery(sb.toString(),map);
    }

    /**
     *获取问卷调查的记录列表
     * @param userId
     */
    public List<Object[]>  getSurveyRecord(Long userId) {
        Map<String, Object> map = new HashMap();
        map.put("userId",userId);
        StringBuffer sb = new StringBuffer();
        sb.append(" from cats_survey_finish_record as csf left join cats_survey_project as csp on csf.csfr_csp_id = csp.csp_id ");
        sb.append(" where csf.csfr_cu_id =:userId and 1=1 ");
        sb.append(" and csf.csfr_is_first_answer=1");
        List<Object[]> sqlQuery = dao.createSQLQuery("select csp.csp_title as title,csp.csp_unit_price as money," +
                "csf.csfr_finish_time as time,csf.csfr_had_issue_reward as status" + sb.toString(), map);
        return sqlQuery;



    }

    /**
     *获取提现记录
     * @param userId
     * @return
     */
    public List<Object[]> getWithDrawRecord(Long userId) {
        Map<String, Object> map = new HashMap();
        map.put("userId",userId);
        StringBuffer sb = new StringBuffer();
        sb.append(" from cats_withdraw_record as cwr where 1=1");
        sb.append(" and cwr_cu_id =:userId");
        List<Object[]> sqlQuery = dao.createSQLQuery("select '提现' as title ," +
                "cwr.cwr_withdraw_money as money,cwr.cwr_withdraw_time as time,cwr.cwr_status as status" + sb.toString(),map);
        return sqlQuery;
    }
}
