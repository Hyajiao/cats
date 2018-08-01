// ======================================
// Project Name:ifc
// Package Name:com.kingyee.ifc.common
// File Name:AdminLoginInterceptor.java
// Create Date:2014-4-14
// ======================================
package com.kingyee.cats.common;

import com.kingyee.common.util.http.HttpUtil;
import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.enums.AuthenticationStatusEnum;
import com.kingyee.cats.enums.UserTypeEnum;
import com.kingyee.cats.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 前台用户认证拦截器
 * 
 * @author peihong
 * @version 2017-10-28
 * 
 */
public class RegInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService service;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        Long userId = session != null ? (Long) session.getAttribute(UserUtil.USER_SESSION_NAME) : null;

        String back = HttpUtil.getFullUrl(request, true);

        CmUser user = service.getUserById(userId);
        if(user == null){
            // 未注册
            response.sendRedirect(HttpUtil.getBasePath(request) + "user/regInit?back=" + back);
            return false;
        }

        return super.preHandle(request, response, handler);
    }

}
