package com.kingyee.cats.common.security;

import javax.servlet.http.HttpSession;

import com.kingyee.common.spring.mvc.WebUtil;
import com.kingyee.common.util.PropertyConst;

/**
 * 用户工具类
 * 
 * 
 * @author 李旭光
 * @version 2013-7-13下午1:11:59
 */
public class AdminUserUtil {

	public static final String ADMIN_USER_LOGIN_SESSION = "ADMIN_USER_LOGIN_SESSION";

	
	/** 取得session */
	private static HttpSession getSession() {
		return WebUtil.getOrCreateSession();
	}

	/**
	 * 判断用户是否登录
	 * 
	 * @return
	 */
	public static boolean hasLogin() {
		return getSession().getAttribute(ADMIN_USER_LOGIN_SESSION) != null;
	}


	/**
	 * 取得登录用户信息
	 * 
	 * @return
	 */
	public static AdminUserModel getLoginUser() {
		if (hasLogin()) {
			return (AdminUserModel) getSession().getAttribute(ADMIN_USER_LOGIN_SESSION);
		} else {
			return null;
		}
	}

    /**
     * 判断当前用户是否为系统管理员
     * @return
     */
    public static boolean isSysAdmin(){
        String role = getLoginUser().getRole().toString();
        if(role.equals(PropertyConst.ROLE_SYS_ADMIN)){
            return true;
        }
        return false;
    }
	
	public static String getShowName(){
		return getLoginUser().getShowName();
	}
	
	public static Long getUserId(){
		return getLoginUser().getId();
	}
	
	public static long getRole(){
		return getLoginUser().getRole();
	}

}
