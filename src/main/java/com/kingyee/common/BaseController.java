package com.kingyee.common;

import com.google.gson.Gson;
import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.common.spring.mvc.WebUtil;
import com.kingyee.common.util.CommonUtil;
import com.kingyee.common.util.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 所有自定义Controller的顶级接口,封装常用的与session和response、request相关的操作
 */
public abstract class BaseController {

    public static String SUCCESS = "success";
    public static String ERROR = "error";

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 标准异常打印
     * @param e
     * @param msg
     */
    public void printLog(String msg, Throwable e) {
        HttpServletRequest request = WebUtil.getRequest();
        String url = "";
        try {
            url = HttpUtil.getFullUrl(request, false);
        } catch (UnsupportedEncodingException e1) {
            logger.error(msg, e);
        }
        String errMsg = "";
        if (request.getServletPath().startsWith("/admin/")) {
            // 后台
            errMsg = "后台-用户[" + AdminUserUtil.getUserId() + "]，在请求[" + url + "]时候发生了异常。";
        } else {
            // 前台
            if (UserUtil.hasLogin()) {
                errMsg = "前台-用户[" + UserUtil.getUserId() + "]，在请求[" + url + "]时候发生了异常。";
            } else {
                errMsg = "前台-未登陆用户，在请求[" + url + "]时候发生了异常。";
            }
        }
        errMsg = errMsg + msg;
        //写入日志
        logger.error(errMsg, e);
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response
     * @param object
     * @return
     */
    protected String renderString(HttpServletResponse response, Object object) {
        return renderString(response, new Gson().toJson(object), "application/json");
    }

    /**
     * 客户端返回字符串
     *
     * @param response
     * @param string
     * @return
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            //解决跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

}
