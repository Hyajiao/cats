// ======================================
// Project Name:ifc
// Package Name:com.kingyee.ifc.common
// File Name:AdminLoginInterceptor.java
// Create Date:2014-4-14
// ======================================
package com.kingyee.fuxi.security.interceptor;

import com.google.gson.JsonElement;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.util.http.HttpUtil;
import com.kingyee.cats.service.UserService;
import com.kingyee.fuxi.security.AuthUtil;
import com.kingyee.fuxi.security.db.AuthResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * 前台用户登录拦截器
 *
 * @author peihong
 * @version 2017-10-28
 */
public class ShiroInterceptor extends HandlerInterceptorAdapter {

    private static final int BUFFER_SIZE = 1024;
    private String accessDeniedUrl = "/403";

    @Autowired
    private UserService service;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String contextPath = request.getContextPath();

        // 取得访问的URL
//        String strURL = request.getRequestURI();
//        if (strURL.startsWith(contextPath)) {
//            strURL = strURL.replaceFirst(contextPath, "");
//        }

        String strURL = (String)request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        if (AuthUtil.getUrlMap() == null) {
            return super.preHandle(request, response, handler);
        }
        AuthResource authRes = AuthUtil.getUrlMap().get(strURL);
        // 如果此URL没有
        if (authRes == null) {
            return super.preHandle(request, response, handler);
        } else {
            // 判断是否具有访问此资源的权限
            if (AuthUtil.hasAuth(authRes)) {
                return super.preHandle(request, response, handler);
            } else {
                // 没有访问此URL的权限
                if (HttpUtil.isAjaxRequest(request)) {
                    // 如果是异步请求 返回权限不足信息
                    response.setContentType("text/plain;");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "inline");
                    JsonElement json = JsonWrapper.newErrorInstance("权限不足");
                    PrintWriter writer = response.getWriter();
                    StringReader reader = null;
                    try {
                        reader = new StringReader(json.toString());
                        char[] buffer = new char[BUFFER_SIZE];
                        int charRead = 0;
                        while ((charRead = reader.read(buffer)) != -1) {
                            writer.write(buffer, 0, charRead);
                        }
                    } finally {
                        if (reader != null) {
                            reader.close();
                        }
                        if (writer != null) {
                            writer.flush();
                            writer.close();
                        }
                    }
                } else {
                    // 直接跳转到权限不足页面
                    response.sendRedirect(contextPath + accessDeniedUrl);
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

}
