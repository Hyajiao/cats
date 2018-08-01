package com.kingyee.cats.dao.admin;

import com.kingyee.cats.db.*;
import com.kingyee.cats.enums.SendScopeEnum;
import com.kingyee.cats.enums.YesNoEnum;
import com.kingyee.common.db.CommonDao;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ph on 2016/7/29.
 */
@Repository
public class AdminSurveyDao extends CommonDao {

    /**
     * 取得调研列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CatsSurveyProject> list(SearchBean searchBean, POJOPageInfo<CatsSurveyProject> pageInfo){
        Map<String, Object> parp = searchBean.getParps();
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsSurveyProject AS p WHERE 1 = 1 ");

        if(parp.get("surveyName") != null){
            hql.append(" AND p.cspTitle LIKE :surveyName ");
        }
        if(parp.get("endDate") != null){
            hql.append(" AND p.cspStartDate <=:endDate  ");
        }
        if(parp.get("startDate") != null){
            hql.append(" AND p.cspEndDate >=:startDate ");
        }
        if(parp.get("state") != null){
            hql.append(" AND p.cspIsValid =:state ");
        }

        Long count = dao.createCountQuery("SELECT COUNT(*) " + hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
            pageInfo.setItems(new ArrayList<CatsSurveyProject>());
            pageInfo.setCount(0);
            return pageInfo;
        }

        String order = " ORDER BY p.cspCreateTime DESC ";
        List<CatsSurveyProject> list = dao.createQuery(hql.toString() + order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;
    }

    /**
     * 取得没有关联调研的项目列表
     * @return
     */
    public List<CatsProject> getAvailableSurveyProjectList(Long surveyId){
        Map<String, Long> parp = new HashMap<>();

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsProject AS p ");
        hql.append(" WHERE p.cpIsValid = 1 ");
        hql.append(" AND p.cpId NOT IN ( SELECT csp.cspCpId FROM CatsSurveyProject AS csp ");

        if(surveyId != null){
            parp.put("surveyId", surveyId);
            hql.append(" WHERE csp.cspId !=:surveyId ");
        }

        hql.append(" ) ");

        return dao.createQuery(hql.toString(), parp);
    }

    /**
     * 取得调研推送的信息列表
     * @return
     */
    public List<CatsSurveyNotice> getSurveyNoticeList(Long surveyId){
        Map<String, Long> parp = new HashMap<>();
        parp.put("surveyId", surveyId);

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsSurveyNotice AS csn ");
        hql.append(" WHERE csn.csnCspId =:surveyId ");
        return dao.createQuery(hql.toString(), parp);
    }


    /**
     * 取得调研推送的明细列表
     * @return
     */
    public POJOPageInfo<Object[]> getSurveyNoticeRecordList(Long sendId, POJOPageInfo<Object[]> pageInfo){
        Map<String, Long> parp = new HashMap<>();
        parp.put("sendId", sendId);

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsSurveyNoticeRecord AS csnr, CatsExpertGroupDetail AS cegd ");
        hql.append(" WHERE csnr.csnrCsnId =:sendId ");
        hql.append(" AND csnr.csnrCegdId = cegd.cegdId ");

        Long count = dao.createCountQuery("SELECT COUNT(*) " + hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
            pageInfo.setItems(new ArrayList<Object[]>());
            pageInfo.setCount(0);
            return pageInfo;
        }

        List<Object[]> list = dao.createQuery(hql.toString(), parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;
    }

    /**
     * 取得调研推送的信息列表-微信信息
     * @param cegId 专家组id
     * @return
     */
    public List<Object[]> getWechatUserList4SendNotice(Long cegId, Integer scope){
        Map<String, Long> parp = new HashMap<>();

        // 取得专家组ID
        parp.put("cegId", cegId);

        StringBuilder hql = new StringBuilder();
        // 全部微信号
        if(scope.equals(SendScopeEnum.ALL.ordinal())){
            hql.append(" SELECT t.cegd_id, u.cu_id, t.cegd_real_name, wc.cwu_openid FROM cats_expert_group_detail AS t, cm_user AS u, cm_wechat_user AS wc ");
            hql.append(" WHERE t.cegd_ceg_id = :cegId ");
            hql.append(" AND u.cu_is_valid = 1  ");
            hql.append(" AND wc.cwu_subscribe = 1 ");
            hql.append(" AND t.cegd_medlive_id IS NOT NULL ");
            hql.append(" AND t.cegd_medlive_id = u.cu_medlive_id ");
            hql.append(" AND wc.cwu_cu_id = u.cu_id ");
        }else{
            // 只取得未发送的微信号
            hql.append(" SELECT t1.cegd_id, u.cu_id, t1.cegd_real_name, wc.cwu_openid FROM ");
            hql.append("   ( ");
            hql.append("     SELECT t.cegd_id, t.cegd_real_name, t.cegd_medlive_id, snr.csnr_cu_id ");
            hql.append("     FROM cats_expert_group_detail AS t LEFT OUTER JOIN cats_survey_notice_record AS snr ");
            hql.append("     ON t.cegd_id = snr.csnr_cegd_id ");
            hql.append("     WHERE t.cegd_medlive_id IS NOT NULL ");
            hql.append("     AND t.cegd_ceg_id = :cegId ");
	        hql.append("   ) AS t1, ");
            hql.append(" cm_user AS u, cm_wechat_user AS wc  ");
            hql.append(" WHERE t1.csnr_cu_id IS NULL ");
            hql.append(" AND u.cu_is_valid = 1 ");
            hql.append(" AND wc.cwu_subscribe = 1 ");
            hql.append(" AND t1.cegd_medlive_id = u.cu_medlive_id ");
            hql.append(" AND wc.cwu_cu_id = u.cu_id ");
        }

        return dao.createSQLQuery(hql.toString(), parp);
    }

    /**
     * 取得调研推送的信息列表-短信信息
     * @param cegId 专家组id
     * @return
     */
    public List<Object[]> getSmsList4SendNotice(Long cegId, Integer scope){
        Map<String, Long> parp = new HashMap<>();

        // 取得专家组ID
        parp.put("cegId", cegId);

        StringBuilder hql = new StringBuilder();
        // 全部短信
        if(scope.equals(SendScopeEnum.ALL.ordinal())){
            hql.append(" SELECT t.cegd_id, u.cu_id, t.cegd_real_name, t.cegd_tel_no FROM cats_expert_group_detail AS t LEFT OUTER JOIN cm_user AS u ");
            hql.append(" ON t.cegd_medlive_id = u.cu_medlive_id ");
            hql.append(" WHERE t.cegd_ceg_id = :cegId ");
            hql.append(" AND t.cegd_medlive_id IS NOT NULL ");
            hql.append(" AND t.cegd_tel_no IS NOT NULL ");
            hql.append(" AND t.cegd_tel_no != '' ");
        }else{
            // 只取得未发送的
            hql.append(" SELECT t1.cegd_id, u.cu_id, t1.cegd_real_name, t1.cegd_tel_no FROM ");
            hql.append("   ( ");
            hql.append("     SELECT t.cegd_id, t.cegd_real_name, t.cegd_tel_no, t.cegd_medlive_id, snr.csnr_cu_id ");
            hql.append("     FROM cats_expert_group_detail AS t LEFT OUTER JOIN cats_survey_notice_record AS snr ");
            hql.append("     ON t.cegd_id = snr.csnr_cegd_id ");
            hql.append("     WHERE t.cegd_medlive_id IS NOT NULL ");
            hql.append("     AND t.cegd_ceg_id = :cegId ");
            hql.append("   ) AS t1 LEFT OUTER JOIN cm_user AS u ");
            hql.append(" ON t1.cegd_medlive_id = u.cu_medlive_id ");
            hql.append(" WHERE t1.csnr_cu_id IS NULL ");
        }

        return dao.createSQLQuery(hql.toString(), parp);
    }


    /**
     * 取得调研完成医生的列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<Object[]> surveyFinishList(SearchBean searchBean, POJOPageInfo<Object[]> pageInfo){
        Map<String, Object> parp = searchBean.getParps();
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsSurveyFinishRecord AS p, CatsExpertGroupDetail AS cegd WHERE ");
        hql.append(" p.csfrCspId =:surveyId ");
        if(parp.get("csfrHadIssueReward") != null){
            hql.append(" AND p.csfrHadIssueReward =:csfrHadIssueReward ");
        }
        if(parp.get("csfrIsFirstAnswer") != null){
            hql.append(" AND p.csfrIsFirstAnswer =:csfrIsFirstAnswer ");
        }
        if(parp.get("startDate") != null){
            hql.append(" AND p.csfrFinishTime >=:startDate ");
        }
        if(parp.get("endDate") != null){
            // 增加一天
            Long date = Long.parseLong(parp.get("endDate").toString()) + 24 * 3600 * 1000L;
            parp.put("endDate", date);
            hql.append(" AND p.csfrFinishTime <:endDate ");
        }
        hql.append(" AND p.csfrCegdId = cegd.cegdId ");

        Long count = dao.createCountQuery("SELECT COUNT(*) " + hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
			pageInfo = new POJOPageInfo<Object[]>();
            pageInfo.setItems(new ArrayList<Object[]>());
            pageInfo.setCount(0);
            return pageInfo;
        }

        String order = " ORDER BY p.csfrFinishTime DESC ";
        hql.insert(0, " SELECT p, cegd ");

        List<Object[]> list = null;
        if(pageInfo != null){
            list = dao.createQuery(hql.toString() + order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        }else{
            pageInfo = new POJOPageInfo<Object[]>();
            list = dao.createQuery(hql.toString() + order, parp);
        }

        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;
    }

    /**
     * 取得调研完成医生的列表
     * @param surveyId 调研项目id
     * @param id 调研完成记录表id
     * @return
     */
    public CatsSurveyFinishRecord getFinshRecord(Long surveyId, Long id){
        Map<String, Object> parp = new HashMap<>();
        parp.put("surveyId", surveyId);
        parp.put("id", id);

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsSurveyFinishRecord AS p  ");
        hql.append(" WHERE p.csfrCspId =:surveyId ");
        hql.append(" AND p.csfrId =:id ");

        return dao.findOne(hql.toString(), parp);
    }

    /**
     * 取得未发放奖励的调研完成记录
     * @param surveyId 调研id
     * @return
     */
    public List<CatsSurveyFinishRecord> getUnIssueRewardList(Long surveyId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("surveyId", surveyId);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsSurveyFinishRecord AS p ");
        hql.append(" WHERE p.csfrCspId =:surveyId ");
        hql.append(" AND p.csfrHadIssueReward = " + YesNoEnum.NO.ordinal());
		hql.append(" AND p.csfrIsFirstAnswer = 1 ");
		hql.append(" GROUP BY p.csfrCuId ");
        return dao.createQuery(hql.toString(), parp);
    }
}
