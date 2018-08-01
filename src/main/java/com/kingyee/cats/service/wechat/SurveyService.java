package com.kingyee.cats.service.wechat;

import com.kingyee.cats.dao.UserDao;
import com.kingyee.cats.dao.wechat.SurveyDao;
import com.kingyee.cats.db.*;
import com.kingyee.common.IBaseService;
import com.kingyee.common.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanyajiao
 */


@Service
public class SurveyService implements IBaseService {

    @Autowired
    private SurveyDao surveyDao;
    @Autowired
    private UserDao userDao;


    /**
     * 未参与问卷列表
     * @return
     * @param cuMedliveId
     */
    public List<CatsSurveyProject> getSurveyList(Long cuMedliveId,Long userId) {
        //未参与
        List<CatsExpertGroupDetail> detailList = surveyDao.getGroupDetail(cuMedliveId);
        Long cegId = null;
        List<CatsSurveyProject> countSurveyList = new ArrayList<CatsSurveyProject>();
        List<CatsSurveyProject> removeSurveyList = new ArrayList<CatsSurveyProject>();
        List<CatsSurveyFinishRecord> finishRecords=null;
        if(detailList!=null&&detailList.size()>0){
            for(int i=0;i<detailList.size();i++){
                cegId = detailList.get(i).getCegdCegId();
                int status = 1;
                Long  time = System.currentTimeMillis();
                List<CatsSurveyProject> surveyProjectList = surveyDao.getSurveyList(status,cegId,time);
                countSurveyList.addAll(surveyProjectList);
            }
        }
        if(countSurveyList!=null && countSurveyList.size()>0){
            for(int i=0;i<countSurveyList.size();i++){
                Long id = countSurveyList.get(i).getCspId();
                finishRecords = surveyDao.getfinishSurvey(userId, id);
                if(finishRecords!=null&&finishRecords.size()>0){
                    countSurveyList.get(i).setFinished(1);
                    //调研项目表id
                    Long csfrCspId = finishRecords.get(0).getCsfrCspId();
                    if(id.equals(csfrCspId)){
                        removeSurveyList.add( countSurveyList.get(i));
                        continue;
                    }

                }else{
                    countSurveyList.get(i).setFinished(0);
                }
                Long startTime = countSurveyList.get(i).getCspStartDate();
                Long endTime = countSurveyList.get(i).getCspEndDate();
                countSurveyList.get(i).setCspStartDateStr(TimeUtil.longToString(startTime,TimeUtil.FORMAT_DATE));
                countSurveyList.get(i).setCspEndDateStr(TimeUtil.longToString(endTime,TimeUtil.FORMAT_DATE));
            }
        }
        countSurveyList.removeAll(removeSurveyList);
        return countSurveyList;

    }

    /**
     * 问卷详情
     * @param id
     * @return
     */
    public CatsSurveyProject getSurveyDetail(Long id) throws Exception {
        CatsSurveyProject question = surveyDao.get(CatsSurveyProject.class, id);
        if(question!=null){
            question.setCspStartDateStr(TimeUtil.longToString(question.getCspStartDate(),TimeUtil.FORMAT_DATE));
            question.setCspEndDateStr(TimeUtil.longToString(question.getCspEndDate(),TimeUtil.FORMAT_DATE));
        }
        return question;
    }

    /**
     * 历史问卷
     * @param userId
     * @return
     */
    public List<CatsSurveyProject> getfinishSurvey(Long userId) {
        List<CatsSurveyProject> list = new ArrayList<CatsSurveyProject>();
//        CmUser cmUser = userDao.get(CmUser.class, userId);
//        Long cuMedliveId = cmUser.getCuMedliveId();
//        List<CatsExpertGroupDetail> groupDetail = surveyDao.getGroupDetail(cuMedliveId);
//        if(groupDetail!=null&&groupDetail.size()>0){
//            for(int i=0;i<groupDetail.size();i++){
//                Long cegId = groupDetail.get(i).getCegdCegId();
//                Long  time = System.currentTimeMillis();
//                List<CatsSurveyProject> surveyProjectList = surveyDao.getSurvey(cegId,time);
//                list.addAll(surveyProjectList);
//            }
//        }
        List<CatsSurveyFinishRecord> finishRecords = surveyDao.getfinishSurvey(userId,null);
        if(finishRecords!=null && finishRecords.size()>0){
            for(int i=0;i<finishRecords.size();i++){
                Long csfrCspId = finishRecords.get(i).getCsfrCspId();
                Long finishTime = finishRecords.get(i).getCsfrFinishTime();
                Integer hadIssueReward = finishRecords.get(i).getCsfrHadIssueReward();
                CatsSurveyProject catsSurveyProject = surveyDao.get(CatsSurveyProject.class, csfrCspId);
                if(catsSurveyProject!=null){
                    catsSurveyProject.setCsfrHadIssueReward(hadIssueReward);
                    catsSurveyProject.setFinishTimeStr(TimeUtil.longToString(finishTime,TimeUtil.FORMAT_DATE));
                    list.add(catsSurveyProject);
                }
            }
        }
        return list;
    }

    /**
     * 添加参与调研的记录
     * @param userId
     * @param id 调研id
     */
    public Integer addRecord(Long userId, Long id,Long fromId) {
        Integer status;
        CmUser cmUser = userDao.get(CmUser.class, userId);
        Long cuMedliveId = cmUser.getCuMedliveId();
        CatsSurveyFinishRecord finishRecord = new CatsSurveyFinishRecord();
        //根据调研id获取专家组明细表主键
        if(cuMedliveId!=null){
            CatsSurveyProject surveyProject = surveyDao.get(CatsSurveyProject.class,id);
            //专家组主键
            Long cspCegId = surveyProject.getCspCegId();
            if(cspCegId!=null){
                //根据专家组主键和医脉通id查询专家组详情主键
                CatsExpertGroupDetail catsExpertGroupDetail = surveyDao.groupDetail(cspCegId,cuMedliveId);
                if(catsExpertGroupDetail!=null){
                    Long cegId = catsExpertGroupDetail.getCegdId();
                    finishRecord.setCsfrCegdId(cegId);
                }
            }
        }
        finishRecord.setCsfrCspId(id);
        finishRecord.setCsfrCuId(userId);
        List<CatsSurveyFinishRecord> finishRecords = surveyDao.getfinishSurvey(userId, id);
        if(finishRecords!=null&&finishRecords.size()>0){
            finishRecord.setCsfrIsFirstAnswer(0);
            status = 0;
        }else{
            finishRecord.setCsfrIsFirstAnswer(1);
            //第一次参与调研
            status = 1;
        }
        finishRecord.setCsfrFinishTime(System.currentTimeMillis());
        finishRecord.setCsfrHadIssueReward(0);
        finishRecord.setCsfrMatchFormId(fromId);
        surveyDao.save(finishRecord);
        return status;
    }

    /**
     * 验证能否参与调研
     * @param bid
     * @param userId
     */
    public boolean checkPermission(Long bid, Long userId) {
        CmUser cmUser = userDao.get(CmUser.class, userId);
        if(cmUser!=null){
            Long cuMedliveId = cmUser.getCuMedliveId();
            if(cuMedliveId!=null){
                List<CatsExpertGroupDetail> detailList = surveyDao.getGroupDetail(cuMedliveId);
                CatsSurveyProject catsSurveyProject = surveyDao.get(CatsSurveyProject.class, bid);
                if(detailList!=null&&detailList.size()>0&&catsSurveyProject!=null){
                    for(int i=0;i<detailList.size();i++){
                        if(detailList.get(i).getCegdCegId().equals(catsSurveyProject.getCspCegId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 更新调研表
     * @param surveyDetail
     */
    public void updateSurvey(CatsSurveyProject surveyDetail) {
        surveyDao.update(surveyDetail);
    }

    /**
     *
     * 查询完成调研
     * @param userId
     * @param id
     */
    public Integer getFinishiDetail(Long userId, Long id) {
        Integer  status;
        List<CatsSurveyFinishRecord> finishRecords = surveyDao.getfinishSurvey(userId, id);
        if(finishRecords!=null && finishRecords.size()>0){
            status = 0;
        }else{
            status =1;
        }
        return status;
    }

}
