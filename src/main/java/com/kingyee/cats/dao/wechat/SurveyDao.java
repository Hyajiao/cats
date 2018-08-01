package com.kingyee.cats.dao.wechat;

import com.kingyee.cats.db.CatsExpertGroupDetail;
import com.kingyee.cats.db.CatsSurveyFinishRecord;
import com.kingyee.cats.db.CatsSurveyProject;
import com.kingyee.common.db.CommonDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hanyajiao
 */

@Repository
public class SurveyDao extends CommonDao{

    /**
     * 问卷列表
     * @return
     */
    public List<CatsSurveyProject> getSurveyList(int status,Long cegId,Long time) {
        Map<String, Object> map = new HashMap();
        map.put("status", status);
        map.put("cegId",cegId);
        map.put("time",time);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsSurveyProject AS cp WHERE 1 = 1 ");
        hql.append(" AND cp.cspIsValid = :status ");
        hql.append(" AND cp.cspCegId =:cegId");
        hql.append(" AND cp.cspEndDate>=:time");
        List<CatsSurveyProject>  list = dao.createQuery(hql.toString(),map);
        return list ;
    }

//    /**
//     * 获取过期的调研
//     * @param cegId
//     * @param time
//     * @return
//     */
//    public List<CatsSurveyProject> getSurvey(Long cegId,Long time) {
//        Map<String, Object> map = new HashMap();
//        map.put("cegId",cegId);
//        map.put("time",time);
//        StringBuilder hql = new StringBuilder();
//        hql.append(" FROM CatsSurveyProject AS cp WHERE 1 = 1 ");
//        hql.append(" AND cp.cspCegId =:cegId");
//        hql.append(" AND cp.cspEndDate<:time");
//        List<CatsSurveyProject>  list = dao.createQuery(hql.toString(),map);
//        return list ;
//    }
    /**
     * 根据医脉通id获取专家组
     * @param cuMedliveId
     */
    public  List<CatsExpertGroupDetail> getGroupDetail(Long cuMedliveId) {
        Map<String,Object> map = new HashMap();
        map.put("cuMedliveId",cuMedliveId);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsExpertGroupDetail AS cegd where 1=1");
        hql.append(" AND cegd.cegdMedliveId=:cuMedliveId");
        List<CatsExpertGroupDetail> query = dao.createQuery(hql.toString(), map);
        if(query!=null&&query.size()>0){
            return query;
        }
        return  null;
    }

    /**
     * 查询专家组详情
     * @param cspCegId
     * @param cuMedliveId
     * @return
     */
    public  CatsExpertGroupDetail groupDetail(Long cspCegId,Long cuMedliveId) {
        Map<String,Object> map = new HashMap();
        map.put("cspCegId",cspCegId);
        map.put("cuMedliveId",cuMedliveId);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsExpertGroupDetail AS cegd where 1=1");
        hql.append(" AND cegd.cegdMedliveId=:cuMedliveId");
        hql.append(" AND cegd.cegdCegId =:cspCegId");
        CatsExpertGroupDetail query = dao.findOne(hql.toString(), map);
        return  query;
    }


    /**
     * 完成的调研
     * @param userId
     * @param bId
     * @return
     */
    public List<CatsSurveyFinishRecord> getfinishSurvey(Long userId,Long bId) {
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("bId",bId);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsSurveyFinishRecord AS csfr where 1=1");
        hql.append(" AND csfr.csfrCuId=:userId");
        hql.append(" AND csfr.csfrIsFirstAnswer=1");
        if(bId!=null){
            hql.append(" AND csfr.csfrCspId=:bId");
        }
        List<CatsSurveyFinishRecord> finishRecords = dao.createQuery(hql.toString(), map);
        return  finishRecords;
    }
}
