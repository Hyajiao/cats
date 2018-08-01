package com.kingyee.cats.web;

import com.google.gson.JsonElement;
import com.kingyee.cats.common.security.UserModel;
import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.cats.service.SmsService;
import com.kingyee.cats.service.UserService;
import com.kingyee.cats.service.admin.AdminSysUserService;
import com.kingyee.common.BaseController;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.util.PropertyConst;
import com.kingyee.common.util.http.CookieUtil;
import com.kingyee.common.util.http.HttpUtil;
import com.kingyee.medlive.util.AccountUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户
 * 
 * @author peihong
 * 2017年05月10日
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController extends BaseController{
	private final static Logger logger = LoggerFactory
			.getLogger(UserController.class);

    @Autowired
    private UserService service;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private AdminSysUserService adminSysUserService;



    /**
     * 医生修改个人信息-初始化
     * @return
     */
    @RequestMapping("editInit")
    public String editInit(ModelMap mm, String from){

        UserModel userModel = service.getUserModel(UserUtil.getUserId());
        mm.addAttribute("userModel", userModel);
        mm.addAttribute("from", from);

        return "user/doctorEdit";
    }

    /**
     * 清空cookie和session
     * @return
     */
    @RequestMapping("rmCache")
    @ResponseBody
    public JsonElement rmCache(ModelMap mm, HttpServletRequest request, HttpServletResponse response){
        service.removeCache();
        HttpSession session = request.getSession(true);
        session.setAttribute(UserUtil.USER_SESSION_NAME, null);
        session.setAttribute(UserUtil.WECHAT_USER_SESSION_NAME, null);
        CookieUtil.setCookie(request, response, UserUtil.COOKIE_AUTOLOGIN,
                "", UserUtil.COOKIE_TIME);

        return JsonWrapper.newSuccessInstance();

    }


    /**
     * 医生在医脉通登录，注册后跳转到的中转页面，用户获取医脉通的信息
     * @return
     */
    @RequestMapping("reg")
    public String reg(ModelMap mm, HttpServletRequest request) throws Exception{
        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session
                .getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : null;

        // 没有登录CAS系统，而session中用户信息存在，则清除session
        if (assertion != null) {
            AttributePrincipal principal = assertion.getPrincipal();
            String userid = principal.getName();
            //将UserId存储在Session中
            if(null != userid && !userid.isEmpty()){
                // 为了在取到id的同时,也取得用户昵称。(在保存检索历史记录中用到)
                HttpClient httpClient = null;
                HttpGet httpGetUserPrfileUrl = null;
                try{
                    httpClient = HttpUtil.getHttpClient();
                    String url = PropertyConst.MEDLIVE_GET_USER_INFO_URL;
                    url = url + "?hashid=" + AccountUtil.hashUserID(Long.valueOf(userid), AccountUtil.HASH_EXT_KEY_HASHID);
                    url = url + "&checkid=" + AccountUtil.hashUserID(Long.valueOf(userid), AccountUtil.HASH_EXT_KEY_CHECKID);
                    httpGetUserPrfileUrl = new HttpGet(url);
                    HttpResponse clientResponse = httpClient.execute(httpGetUserPrfileUrl);
                    int statusCode = clientResponse.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        HttpEntity entity = clientResponse.getEntity();
                        String result = EntityUtils.toString(entity, "UTF-8");
                        JSONObject obj = new JSONObject(result);
                        if(obj.getString("success_msg").equals("success")){
                            Long wechatUserId = UserUtil.getWechatUserId();
                            CmWechatUser wechatUserInSession = service.getWechatUserByWechatUserId(wechatUserId);

                            JSONObject data = obj.getJSONObject("data");
                            Long medliveId = data.getLong("user_id");
                            CmUser user = service.getUserByMedliveId(medliveId);
                            if(user == null){
                                user = new CmUser();
                            }else{
                                CmWechatUser wechatUser = service.getWechatUserByUserId(user.getCuId());
                                if(wechatUser != null && !wechatUser.getCwuOpenid().equals(wechatUserInSession.getCwuOpenid())){
                                    // 此医脉通账号信息被绑定 并且 已绑定的用户和当前用户不是同一个人
                                    String reLoginUrl = AccountUtil.getReloginUrl(HttpUtil.getBasePath(request) + "user/reg");
                                    mm.addAttribute("msg", "此医脉通账号已关联其他微信号。<a href='" + reLoginUrl + "'>点此处重新绑定</a>其他医脉通账号。");
                                    return "warn";
                                }
                            }

                            user = service.buildMedliveUser(user, data);
                            user = service.saveRegUser(user);
                            wechatUserInSession.setCwuCuId(user.getCuId());
                            service.updateCmWechatUser(wechatUserInSession);



                            String msg = "您已登录并绑定成功！";
                            mm.addAttribute("msg",msg);
                            return "success";
                        }
                    }
                }catch (Exception e) {
                    logger.error("取得用户信息时，有错误发生。", e);
                }
            }
        }
        //Session失效，没有登录成功
        String reLoginUrl = AccountUtil.getReloginUrl(HttpUtil.getBasePath(request) + "user/reg");
        mm.addAttribute("msg", "没有绑定成功，请<a href='" + reLoginUrl + "'>重试</a>。");
        return "warn";
    }


}
