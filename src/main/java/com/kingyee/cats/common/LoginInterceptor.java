// ======================================
// Project Name:ifc
// Package Name:com.kingyee.ifc.common
// File Name:AdminLoginInterceptor.java
// Create Date:2014-4-14
// ======================================
package com.kingyee.cats.common;

import com.kingyee.common.util.PropertyConst;
import com.kingyee.common.util.http.HttpUtil;
import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 前台用户登录拦截器
 * 
 * @author peihong
 * @version 2017-10-28
 * 
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService service;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        Long userId = session != null ? (Long) session.getAttribute(UserUtil.USER_SESSION_NAME) : null;
        String back = HttpUtil.getFullUrl(request, true);
        if(userId == null){
            response.sendRedirect(PropertyConst.MEDLIVE_URL + "/auth/login?service=" + back);
            return false;
        }
        return super.preHandle(request, response, handler);
    }

}
