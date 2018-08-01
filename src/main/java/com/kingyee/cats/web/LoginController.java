package com.kingyee.cats.web;

import com.google.gson.JsonElement;
import com.kingyee.common.cache.CacheFacade;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.util.http.HttpUtil;
import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.enums.SmsTypeEnum;
import com.kingyee.cats.service.SmsService;
import com.kingyee.cats.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 用户
 * 
 * @author peihong
 * 2017年05月10日
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {
	private final static Logger logger = LoggerFactory
			.getLogger(LoginController.class);

    @Autowired
	private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private CacheFacade cache;

    /**
     * 用户登录init
     */
    @RequestMapping("loginInit")
    public String loginInit(ModelMap mm){
        return "login";
    }

    /**
     * 医生注册-取得短信验证码code
     * @return
     */
    @RequestMapping("loginCode")
    @ResponseBody
    public JsonElement loginCode(String tel, HttpServletRequest request){

        CmUser user = userService.getUserByTelNo(tel);
        if(user != null){
            return smsService.saveSmsCode(HttpUtil.getRemoteIpAddr(request), tel, SmsTypeEnum.LOGIN);
        }else{
            return JsonWrapper.newErrorInstance("该用户尚未注册，请注册后登录。");
        }
    }

    /**
     * 用户登录
     */
    @RequestMapping("login")
    public String login(ModelMap mm, String telNo, String code, String back) throws UnsupportedEncodingException {

        if(smsService.updateSmsCode(telNo, code, SmsTypeEnum.LOGIN)){
            CmUser cmUser = userService.getUserByTelNo(telNo);
            UserUtil.login(cmUser.getCuId());
            UserUtil.cacheUser(cache, cmUser);

            if(StringUtils.isNotEmpty(back)){
                return "redirect:" + back;
            }else{
                return "success";
            }
        }else{
            mm.addAttribute("errMsg", "手机号和验证码不匹配");
            return "login";
        }
    }

    /**
     * 用户登出操作
     */
    @RequestMapping("logout")
    public String logout(String service){
        try{
            UserUtil.logout();
        }catch(Exception e){
            logger.error("退出时出错。", e);
            return "error";
        }
        if(StringUtils.isNotEmpty(service)){
            return "redirect:" + service;
        }else{
            return "login";
        }
    }


}
