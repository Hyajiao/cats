package com.kingyee.cats.common.security;

import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.service.UserService;
import com.kingyee.common.util.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 前台用户信息取得拦截器
 * 
 * @author peihong
 * @version 2014-4-14 下午2:03:58
 * 
 */
public class UserInfoInterceptor extends HandlerInterceptorAdapter {
    private final static org.slf4j.Logger logger = LoggerFactory
            .getLogger(UserInfoInterceptor.class);
    @Autowired
    private UserService service;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {


        // 如果是爬虫，则跳过此拦截器
        if(HttpUtil.isRequestFromSpider(request)){
            return super.preHandle(request, response, handler);
        }

        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session
                .getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : null;

        Long userId = session != null ? (Long) session.getAttribute(UserUtil.USER_SESSION_NAME) : null;

        // 没有登录CAS系统，而session中用户信息存在，则清除session
        if (assertion == null) {
            if(userId != null){
                session.removeAttribute(UserUtil.USER_SESSION_NAME);
            }
        }else{
            AttributePrincipal principal = assertion.getPrincipal();
            String medliveId = principal.getName();
            //将UserId存储在Session中
            if(null != medliveId && !medliveId.isEmpty()){
                // 以医脉通cas的登录信息，作为此平台的登录基准
                CmUser cmUser = service.getUserByMedliveId(Long.valueOf(medliveId));
                if(cmUser == null){
                    cmUser = new CmUser();
                    cmUser = service.saveDoctorByMedliveId(cmUser, Long.valueOf(medliveId));
                    cmUser = service.saveRegUser(cmUser);
                    userId = cmUser.getCuId();
                }else{
                    if(!cmUser.getCuId().equals(userId) ){
                        cmUser = service.saveRegUser(cmUser);
                        userId = cmUser.getCuId();
                    }else{
                        cmUser = service.saveDoctorByMedliveId(cmUser, Long.valueOf(medliveId));
                        service.updateCmUser(cmUser);
                        userId = cmUser.getCuId();
                    }
                }
            }
            session.setAttribute(UserUtil.USER_SESSION_NAME, userId);
        }


//        HttpSession session = request.getSession(true);
//        Long userId = session != null ? (Long) session.getAttribute(UserUtil.USER_SESSION_NAME) : null;
//
//        if(userId == null){
//            // session不存在，则从cookie中取得用户信息
//            String userIdStr = UserUtil.getCookie(request, UserUtil.COOKIE_AUTOLOGIN);
//            if(userIdStr != null && !userIdStr.equals("")){
//                userId = Long.valueOf(userIdStr);
//                CmUser cmUser = service.getUserById(userId);
//                if(cmUser != null){
//                    session.setAttribute(UserUtil.USER_SESSION_NAME, userId);
//                }else{
//                    CookieUtil.setCookie(request, response, UserUtil.COOKIE_AUTOLOGIN,
//                            "", UserUtil.COOKIE_TIME);
//                }
//            }else{
//                CookieUtil.setCookie(request, response, UserUtil.COOKIE_AUTOLOGIN,
//                        "", UserUtil.COOKIE_TIME);
//            }
//        }else{
//            //取关 清session
////            CmUser cmUser = service.getUserById(userId);
////            CmWechatUser wechatUser = service.getUserById(userId);
////            if(wechatUser.getCwuSubscribe() != null && wechatUser.getCwuSubscribe() == 0){
////                //未关注
////                service.removeCache();
////                session.setAttribute(UserUtil.USER_SESSION_NAME, null);
////                session.setAttribute(UserUtil.NEED_REG_PROMPT, null);
////                CookieUtil.setCookie(request, response, UserUtil.COOKIE_AUTOLOGIN,
////                        "", UserUtil.COOKIE_TIME);
////            }
//        }

        return super.preHandle(request, response, handler);
    }
}
