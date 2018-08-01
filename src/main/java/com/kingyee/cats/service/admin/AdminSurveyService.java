package com.kingyee.cats.service.admin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.dao.admin.AdminBillDao;
import com.kingyee.cats.dao.admin.AdminExpertGroupDao;
import com.kingyee.cats.dao.admin.AdminSurveyDao;
import com.kingyee.cats.dao.admin.AdminUserDao;
import com.kingyee.cats.db.*;
import com.kingyee.cats.enums.BillTypeEnum;
import com.kingyee.cats.enums.SendNoticeStatusEnum;
import com.kingyee.cats.enums.SendTypeEnum;
import com.kingyee.cats.enums.YesNoEnum;
import com.kingyee.common.IBaseService;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.sms.SmsTplUtil;
import com.kingyee.common.util.PropertyConst;
import com.kingyee.fuxi.shorturl.service.ShortUrlService;
import com.kingyee.medlive.util.AccountUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 调研管理
 * 
 */
@Service
public class AdminSurveyService implements IBaseService {

    private final static Logger logger = LoggerFactory.getLogger(AdminSurveyService.class);
	
    @Autowired
    private AdminSurveyDao dao;
    @Autowired
    private AdminBillDao billDao;
    @Autowired
    private AdminUserDao userDao;
    @Autowired
    private AdminExpertGroupDao expertGroupDao;
    @Autowired
    private ShortUrlService shortUrlService;
    @Autowired
    protected WxMpService wxMpService;

    /**
     * 调研列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CatsSurveyProject> list(SearchBean searchBean, POJOPageInfo<CatsSurveyProject> pageInfo){
        return dao.list(searchBean, pageInfo);
    }

    /**
     * 取得没有关联调研的项目列表
     * @return
     */
    public List<CatsProject> getAvailableSurveyProjectList(){
        return dao.getAvailableSurveyProjectList(null);
    }

    /**
     * 取得没有关联调研的项目列表
     * @return
     */
    public List<CatsProject> getAvailableSurveyProjectList(Long surveyId){
        return dao.getAvailableSurveyProjectList(surveyId);
    }

    /**
     * 保存调研
     * @param survey
     * @return
     */
    public Long save(CatsSurveyProject survey) {
        survey.setCspFinishedNum(0);
        survey.setCspCreateUserId(AdminUserUtil.getUserId());
        survey.setCspCreateUserName(AdminUserUtil.getShowName());
        survey.setCspCreateTime(System.currentTimeMillis());
        survey.setCspIsValid(1);
        return dao.save(survey);
    }

    /**
     * 根据id获取调研信息
     * @param id
     * @return
     */
    public CatsSurveyProject getById(Long id){
        return dao.get(CatsSurveyProject.class, id);
    }

    /**
     * 更新调研信息
     * @param survey
     */
    public void edit(CatsSurveyProject survey) throws UnsupportedEncodingException {
        CatsSurveyProject db = getById(survey.getCspId());

        BeanUtils.copyProperties(survey, db, new String[] {"cspId", "cspFinishedNum", "cspSurveyReportPath", "cspIsValid", "cspCreateTime", "cspCreateUserId", "cspCreateUserName"});
        db.setCspUpdateTime(System.currentTimeMillis());
        db.setCspUpdateUserId(AdminUserUtil.getUserId());
        db.setCspUpdateUserName(AdminUserUtil.getShowName());
        dao.update(db);
    }

    /**
     * 更新信息
     * @param table
     */
    public void update(Object table) {
        dao.update(table);
    }


    /**
     * 取得调研推送的信息列表
     * @return
     */
    public List<CatsSurveyNotice> getSurveyNoticeList(Long surveyId){
        return dao.getSurveyNoticeList(surveyId);
    }

    /**
     * 保存调研推送
     * @param surveyNotice
     * @return
     */
    public JsonElement saveSurveyNotice(CatsSurveyNotice surveyNotice, Integer scope) throws UnsupportedEncodingException {
        CatsSurveyProject survey = this.getById(surveyNotice.getCsnCspId());
        List<Object[]> sendList = null;
        if(surveyNotice.getCsnSendType().equals(SendTypeEnum.WECHAT.ordinal())){
            sendList = dao.getWechatUserList4SendNotice(survey.getCspCegId(), scope);
        }else{
            sendList = dao.getSmsList4SendNotice(survey.getCspCegId(), scope);
        }

        if(sendList.size() > 0){
            surveyNotice.setCsnSendNum(sendList.size());
            surveyNotice.setCsnSuccessNum(0);
            surveyNotice.setCsnSendStatus(SendNoticeStatusEnum.NOT_SEND.ordinal());
            surveyNotice.setCsnCreateUserId(AdminUserUtil.getUserId());
            surveyNotice.setCsnCreateUserName(AdminUserUtil.getShowName());
            surveyNotice.setCsnCreateTime(System.currentTimeMillis());
            Long csnId = dao.save(surveyNotice);

            surveyNotice.setCsnId(csnId);

            int successNum = 0;
            if(surveyNotice.getCsnSendType().equals(SendTypeEnum.WECHAT.ordinal())){
                // 微信
                for(int i = 0; i < sendList.size(); i++){
                    Object[] objs = sendList.get(i);

                    // 发送模板消息
                    SendNoticeStatusEnum status = sendSurveyNotice4Wechat((String)objs[2], (String)objs[3], survey);
                    // 保存发送记录
                    CatsSurveyNoticeRecord noticeRecord = new CatsSurveyNoticeRecord();
                    noticeRecord.setCsnrCsnId(csnId);
                    noticeRecord.setCsnrCegdId(((BigInteger)objs[0]).longValue());
                    noticeRecord.setCsnrCuId(((BigInteger)objs[1]).longValue());
                    noticeRecord.setCsnrSendType(SendTypeEnum.WECHAT.ordinal());
                    noticeRecord.setCsnrSendStatus(status.ordinal());
                    noticeRecord.setCsnrSendTime(System.currentTimeMillis());
                    dao.save(noticeRecord);

                    if(status.equals(SendNoticeStatusEnum.SEND_FINISH)){
                        successNum++;
                    }
                }
            }else{
                // 短信
                for(int i = 0; i < sendList.size(); i++){
                    Object[] objs = sendList.get(i);

                    CatsExpertGroupDetail expert = expertGroupDao.getExpertByCegdId(((BigInteger)objs[0]).longValue());

                    // 发送短信消息
                    SendNoticeStatusEnum status = sendSurveyNotice4Sms(expert.getCegdMedliveId(), (String)objs[2], (String)objs[3], survey);
                    // 保存发送记录
                    CatsSurveyNoticeRecord noticeRecord = new CatsSurveyNoticeRecord();
                    noticeRecord.setCsnrCsnId(csnId);
                    noticeRecord.setCsnrCegdId(((BigInteger)objs[0]).longValue());
                    if(objs[1] != null){
                        noticeRecord.setCsnrCuId(((BigInteger)objs[1]).longValue());
                    }
                    noticeRecord.setCsnrSendType(SendTypeEnum.SMS.ordinal());
                    noticeRecord.setCsnrSendStatus(status.ordinal());
                    noticeRecord.setCsnrSendTime(System.currentTimeMillis());
                    dao.save(noticeRecord);

                    if(status.equals(SendNoticeStatusEnum.SEND_FINISH)){
                        successNum++;
                    }
                }
            }


            surveyNotice.setCsnSuccessNum(successNum);
            dao.update(surveyNotice);

            return JsonWrapper.newSuccessInstance();
        }else{
            return JsonWrapper.newErrorInstance("没有数据需要发送");
        }
    }

    /**
     * 取得调研推送的明细列表
     * @return
     */
    public POJOPageInfo<Object[]> getSurveyNoticeRecordList(Long sendId, POJOPageInfo<Object[]> pageInfo){
        return dao.getSurveyNoticeRecordList(sendId, pageInfo);
    }

    /**
     * 取得调研完成医生的列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<Object[]> surveyFinishList(SearchBean searchBean, POJOPageInfo<Object[]> pageInfo){
        return dao.surveyFinishList(searchBean, pageInfo);
    }


    /**
     * 发放奖励金
     * @return
     */
    public void saveIssueReward(Long surveyId, Long id) throws UnsupportedEncodingException {
        // 更新调研完成记录
        CatsSurveyFinishRecord surveyFinishRecord = dao.getFinshRecord(surveyId, id);
        // 未支付
        if(surveyFinishRecord.getCsfrHadIssueReward().equals(YesNoEnum.NO.ordinal())){
            surveyFinishRecord.setCsfrHadIssueReward(YesNoEnum.YES.ordinal());
            surveyFinishRecord.setCsfrRewardIssueTime(System.currentTimeMillis());
            surveyFinishRecord.setCsfrRewardIssueUserId(AdminUserUtil.getUserId());
            surveyFinishRecord.setCsfrRewardIssueUserName(AdminUserUtil.getShowName());
            dao.update(surveyFinishRecord);


            List<CmBills> billList = billDao.getBillByBizId(BillTypeEnum.SUVERY.ordinal(), surveyFinishRecord.getCsfrId());
            if(billList == null || billList.size() == 0){
                // 保存账单表
                CatsSurveyProject survey = this.getById(surveyId);
                CmBills bill = new CmBills();
                bill.setCbBizName(survey.getCspTitle());
                bill.setCbCuId(surveyFinishRecord.getCsfrCuId());
                bill.setCbBizType(BillTypeEnum.SUVERY.ordinal());
                bill.setCbBizId(surveyFinishRecord.getCsfrId());
                bill.setCbTradeMoney(survey.getCspUnitPrice());
                bill.setCbTradeTime(System.currentTimeMillis());
                dao.save(bill);

                // 修改用户余额
                CmUser user = userDao.getRegUserById(surveyFinishRecord.getCsfrCuId());
                if(user.getCuBalance() == null){
                    user.setCuBalance(bill.getCbTradeMoney());
                }else{
                    user.setCuBalance(user.getCuBalance() + bill.getCbTradeMoney());
                }

                dao.update(user);

                CmWechatUser wechatUser = userDao.getWechatUserByCuId(surveyFinishRecord.getCsfrCuId());
                if(wechatUser == null){
                    CatsExpertGroupDetail expert = dao.get(CatsExpertGroupDetail.class, surveyFinishRecord.getCsfrCegdId());
                    // 发送短信提醒
                    sendBalanceChange4Sms(expert, bill);
                }else{
                    // 发送微信提醒
                    sendBalanceChange4Wechat(wechatUser.getCwuOpenid(), bill, user);
                }
            }
        }
    }

    /**
     * 批量发放奖励金
     * @return
     */
    public void saveIssueRewardBatch(Long surveyId, String[] idArray) throws UnsupportedEncodingException {
        for(String id : idArray){
            this.saveIssueReward(surveyId, Long.valueOf(id));
        }
    }

    /**
     * 给所有未发放奖励金的，发放奖励金
     * @return
     */
    public void saveIssueRewardAll(Long surveyId) throws UnsupportedEncodingException {
        List<CatsSurveyFinishRecord> list = dao.getUnIssueRewardList(surveyId);
        for (CatsSurveyFinishRecord rec : list){
            this.saveIssueReward(surveyId, rec.getCsfrId());
        }
    }


    /**
     * 发送调研问卷模板消息
     * @param realName
     * @param openid
     * @param survey
     * @return
     */
    private SendNoticeStatusEnum sendSurveyNotice4Wechat(String realName, String openid, CatsSurveyProject survey){
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openid)
                .url(PropertyConst.DOMAIN_URL + "/user/survey/surveyDetail?id=" + survey.getCspId())
                .templateId(PropertyConst.TEMPLATE_SURVEY_NOTICE).build();

        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("first", "您好，问卷任务已经接收成功"));
        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("keyword1", survey.getCspTitle()));
        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("keyword2", survey.getCspStartDateStr()));
        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("keyword3", survey.getCspEndDateStr()));
        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("remark", "问卷详细内容请点击此信息进入查阅"));

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            logger.error("发送调研问卷模板消息失败：openid=" + openid + "，调研id=" + survey.getCspId());
            return SendNoticeStatusEnum.SEND_FAIL;
        }
        return SendNoticeStatusEnum.SEND_FINISH;
    }

    /**
     * 发送调研问卷的短信
     * @param medliveId 医脉通id
     * @param realName 真实姓名
     * @param telNo 电话号码
     * @param survey 调研
     * @return
     */
    private SendNoticeStatusEnum sendSurveyNotice4Sms(Long medliveId, String realName, String telNo, CatsSurveyProject survey) throws UnsupportedEncodingException {

        List<String> values1 = new ArrayList<String>();
        values1.add(realName);
        values1.add(survey.getCspTitle());
        String url = AccountUtil.getForceLoginUrl(medliveId, PropertyConst.DOMAIN_URL + "/user/survey/surveyDetail?id=" + survey.getCspId());
        CmShortUrl shortUrl = shortUrlService.saveShortUrl(url);
        values1.add(survey.getCspEndDateStr() + "，点击参加" + PropertyConst.DOMAIN_URL + "/s/" + shortUrl.getCsuCode());

        JsonObject obj = SmsTplUtil.sendSms(telNo, PropertyConst.SMS_TEMPLATE_SURVEY_NOTICE, values1, "医调研");
        if(obj.get("success").getAsBoolean()){
            return SendNoticeStatusEnum.SEND_FINISH;
        }else{
            return SendNoticeStatusEnum.SEND_FAIL;
        }
    }


    /**
     * 发放奖励金的模板消息
     * @param openid
     * @param bill
     * @return
     */
    private SendNoticeStatusEnum sendBalanceChange4Wechat(String openid, CmBills bill, CmUser user){
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openid)
                .url(PropertyConst.DOMAIN_URL + "/user/wallet/recordList")
                .templateId(PropertyConst.TEMPLATE_BALANCE_CHANGE).build();

        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("first", bill.getCbBizName() + "，奖励金入账"));
        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("keyword1", "奖励金入账"));
        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("keyword2", bill.getCbTradeMoney().toString()));
        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("keyword3", bill.getCbTradeTimeStr()));
        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("keyword4", user.getCuBalance().toString()));
        templateMessage.addWxMpTemplateData(
                new WxMpTemplateData("remark", "感谢使用, 如有问题请及时联系我们!"));

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            logger.error("发放奖励金的模板消息失败：openid=" + openid + "，账单id=" + bill.getCbId());
            return SendNoticeStatusEnum.SEND_FAIL;
        }
        return SendNoticeStatusEnum.SEND_FINISH;
    }

    /**
     * 发放奖励金的短信
     * @param expert 专家信息
     * @param bill 账单
     * @return
     */
    private SendNoticeStatusEnum sendBalanceChange4Sms(CatsExpertGroupDetail expert, CmBills bill) throws UnsupportedEncodingException {

        List<String> values1 = new ArrayList<String>();
        values1.add(expert.getCegdRealName());
        values1.add(bill.getCbBizName());
        values1.add(bill.getCbTradeMoney().toString());

        String url = AccountUtil.getForceLoginUrl(expert.getCegdMedliveId(), PropertyConst.DOMAIN_URL + "/user/wallet/recordList");
        CmShortUrl shortUrl = shortUrlService.saveShortUrl(url);
        values1.add(PropertyConst.DOMAIN_URL + "/s/" + shortUrl.getCsuCode());

        JsonObject obj = SmsTplUtil.sendSms(expert.getCegdTelNo(), PropertyConst.SMS_TEMPLATE_GATHERING, values1, "医调研");
        if(obj.get("success").getAsBoolean()){
            return SendNoticeStatusEnum.SEND_FINISH;
        }else{
            return SendNoticeStatusEnum.SEND_FAIL;
        }
    }

}
