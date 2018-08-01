package com.kingyee.cats.web.wechat;

import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.db.CatsSurveyProject;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.service.wechat.SurveyService;
import com.kingyee.cats.service.wechat.UserInfoService;
import com.kingyee.common.BaseController;
import com.kingyee.common.spring.mvc.WebUtil;
import com.kingyee.common.util.PropertyConst;
import com.kingyee.common.util.encrypt.AesUtil;
import com.kingyee.common.util.http.HttpUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.List;

/**
 * 问卷
 * @author hanyajiao
 */

@Controller
@RequestMapping(value= "/user/survey/")
public class SurveyController extends BaseController{

    private  final static Logger logger = LoggerFactory
            .getLogger(SurveyController.class);

    @Autowired
    private SurveyService surveyService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 可参与的问卷调查
     * @param modelMap
     * @return
     */
    @RequestMapping("surveyNew")
    public String getQuestList(ModelMap modelMap){
        Long userId = UserUtil.getUserId();
        CmUser userInfo = userInfoService.getUserInfo(userId);
        //医脉通id
        Long cuMedliveId = userInfo.getCuMedliveId();
        List<CatsSurveyProject> catsSurveyProjectList =  surveyService.getSurveyList(cuMedliveId,userId);
        modelMap.addAttribute("catsSurveyProjectList",catsSurveyProjectList);
        return "userInfo/survey/surveyNewList";
    }

    /**
     * 历史问卷
     * @param modelMap
     * @return
     */
    @RequestMapping("surveyOld")
    public String getOldQuest(ModelMap modelMap) {
        Long userId = UserUtil.getUserId();
        List<CatsSurveyProject> catsSurveyProjectList = surveyService.getfinishSurvey(userId);
        modelMap.addAttribute("catsSurveyProjectList", catsSurveyProjectList);
        return "userInfo/survey/surveyOldList";


    }

    /**
     * 问卷详情
     * @param modelMap
     * @param id 调研id
     * @return
     */
    @RequestMapping("surveyDetail")
    public String getQuestDetail(ModelMap modelMap, Long id) {
        try {
            Long  userId = UserUtil.getUserId();
            CatsSurveyProject catsSurveyProject = surveyService.getSurveyDetail(id);
            boolean permission = surveyService.checkPermission(id, userId);
            Integer detail = surveyService.getFinishiDetail(userId, id);
            if(permission==true){
                Long time = System.currentTimeMillis();
                //处理回调参数
                String redirectUrl = this.redirectUrl(userId, id,time);
                Base64 base64 = new Base64();
                byte[] textByte = redirectUrl.getBytes("UTF-8");
                String encodedText = base64.encodeToString(textByte);
                String redirectEncode = URLEncoder.encode(encodedText, "UTF-8");
                modelMap.addAttribute("redirectUrl",redirectEncode);
                modelMap.addAttribute("catsSurveyProject",catsSurveyProject);
                modelMap.addAttribute("userId",userId);
                modelMap.addAttribute("detail",detail);
            }else{
                String msg = "您不能参与此次调研！";
                modelMap.addAttribute("msg",msg);
                return "warn";
            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg ="获取问卷详情出错!";
            logger.error(msg,e);
            modelMap.addAttribute("msg",msg);
            return "error";
        }
        return "userInfo/survey/surveyDetail";
    }

    /**
     * 参与成功返回
     * @param modelMap
     * @param token
     * @param bid 调研id
     * @param time 事时间
     * @param answer_id 调研结果id
     * @return
     */
    @RequestMapping("message")
    public String redirectMsg(ModelMap modelMap,String token,Long bid,Long time,Long answer_id){
        try {
            if(token!=null&&StringUtils.isNotEmpty(token)&&
                    bid!=null&&time!=null){
                    Long userId = UserUtil.getUserId();
                    String encode = String.valueOf(userId+bid+time);
                    String value = AesUtil.encode(encode,PropertyConst.SURVEY_TOKEN);
                    if(value.equals(token)) {
                        //验证是否能够参加调研
                        boolean permission = surveyService.checkPermission(bid, userId);
                        if (permission == true) {
                            //参与成功添加记录
                            Integer finishiDetail = surveyService.getFinishiDetail(userId, bid);
                            Integer record =0;
                            if(finishiDetail==1){
                                record = surveyService.addRecord(userId, bid, answer_id);
                            }
                            CatsSurveyProject surveyDetail = surveyService.getSurveyDetail(bid);
                            if(record==1){
                                Integer num = surveyDetail.getCspFinishedNum();
                                if (num!=null){
                                   surveyDetail.setCspFinishedNum(num+1);
                                }else{
                                    surveyDetail.setCspFinishedNum(1);
                                }
                                surveyService.updateSurvey(surveyDetail);
                            }
                            String price = String.valueOf(surveyDetail.getCspUnitPrice());
                            modelMap.addAttribute("price", price);
                            modelMap.addAttribute("record", record);
                            return "userInfo/survey/questionMsg";
                        }
                        else{
                            String msg = "您不能参与此次调研！";
                            modelMap.addAttribute("msg",msg);
                            return "warn";
                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("参与调研返回出错",e);
            String msg ="参与调研返回出错！";
            modelMap.addAttribute("msg",msg);
            return "error";
        }
        return null;
    }

;
    /**
     * 拼接回调地址
     * @param userId
     * @param bizId
     * @return
     */
    public String redirectUrl(Long userId,Long bizId,Long time){
        String redirect_url  = "";
        try {
            if(userId!=null&&bizId!=null){
                String token = String.valueOf(userId+bizId+time);
                String value = AesUtil.encode(token,PropertyConst.SURVEY_TOKEN);
                String encode = URLEncoder.encode(value, "UTF-8");
                String path = HttpUtil.getBasePath(WebUtil.getRequest());
                redirect_url = path+"user/survey/message?token="+ encode +"&bid="+bizId+"&time="+time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redirect_url;
    }
}
