package com.kingyee.cats.common.security;

import com.kingyee.common.cache.CacheConst;
import com.kingyee.common.cache.CacheFacade;
import com.kingyee.common.spring.mvc.WebUtil;
import com.kingyee.common.util.http.CookieUtil;
import com.kingyee.common.util.encrypt.RC4Util;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.db.CmWechatUser;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

import static com.kingyee.cats.common.security.AdminUserUtil.ADMIN_USER_LOGIN_SESSION;


/**
 * 用户工具类
 *
 * @author 裴宏
 *
 */
@Component
public class UserUtil {

	public static String USER_SESSION_NAME = "USER_LOGIN_SESSION";
	public static String WECHAT_USER_SESSION_NAME = "WECHAT_USER_LOGIN_SESSION";
	public static String COOKIE_AUTOLOGIN = "USER_LOGIN_COOKIE";
	public static int COOKIE_TIME = 365 * 24 * 60 * 60;


	/** 用户权限分隔符 */
	public static final String USER_RIGHT_SPLITER = ",";

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
		return getSession().getAttribute(USER_SESSION_NAME) != null;
	}

    /**
     * 做登录时，需要的一些操作
     */
	public static void login(Long userId) throws UnsupportedEncodingException {
        getSession().setAttribute(USER_SESSION_NAME, userId);
        setCookie(WebUtil.getRequest(), WebUtil.getResponse(), COOKIE_AUTOLOGIN, userId.toString(), COOKIE_TIME);
    }

    /**
     * 做logout时，需要的一些操作
     */
    public static void logout() throws UnsupportedEncodingException {
        getSession().setAttribute(USER_SESSION_NAME, null);
        setCookie(WebUtil.getRequest(), WebUtil.getResponse(), COOKIE_AUTOLOGIN, "", -1);
    }


    /**
     * 取得用户的id
     *
     * @return
     */
    public static Long getUserId() {
        if (hasLogin()) {
            return (Long)getSession().getAttribute(USER_SESSION_NAME);
        }
        return null;
    }

    /**
     * 取得用户的id
     *
     * @return
     */
    public static Long getWechatUserId() {
        Object wechatUserId = getSession().getAttribute(WECHAT_USER_SESSION_NAME);
        if (wechatUserId != null) {
            return (Long)wechatUserId;
        }
        return null;
    }

    public static void cacheUser(CacheFacade cache, CmUser user){
        String key = CacheConst.KEY_CM_USER_BY_USER_ID_ + user.getCuId();
        cache.setValue(key, user);
    }

    public static void cacheWechatUser(CacheFacade cache, CmWechatUser wechatUser){
        // 缓存
        String key = CacheConst.KEY_CM_WECHAT_BY_WECAHT_ID_ + wechatUser.getCwuId();
        cache.setValue(key, wechatUser);
        key = CacheConst.KEY_CM_WECHAT_BY_USER_ID_ + wechatUser.getCwuCuId();
        cache.setValue(key, wechatUser);
        key = CacheConst.KEY_CM_WECHAT_BY_OPEN_ID_ + wechatUser.getCwuOpenid();
        cache.setValue(key, wechatUser);
    }

    public static void removeCache(CacheFacade cache){
        Long userId = getUserId();
        String key = CacheConst.KEY_CM_WECHAT_BY_USER_ID_ + userId;
        CmWechatUser wechatUser = cache.getValue(key);
        if(wechatUser != null){
            // 删除缓存
            cache.del(key);
            key = CacheConst.KEY_CM_WECHAT_BY_OPEN_ID_ + wechatUser.getCwuOpenid();
            cache.del(key);

            key = CacheConst.KEY_CM_WECHAT_BY_WECAHT_ID_ + wechatUser.getCwuId();
            cache.del(key);

            if(wechatUser.getCwuCuId() != null){
                key = CacheConst.KEY_CM_USER_BY_USER_ID_ + wechatUser.getCwuCuId();
                cache.del(key);
            }
        }
    }

//
//
//    public static String getShowName(){
//        return getLoginUser().getWechatUser().getCwuNickname();
//    }

	/**
	 * 创造cookie.
	 * @throws UnsupportedEncodingException 
	 *
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response,
		String cookieName, String userName, int maxAge) throws UnsupportedEncodingException{
		/* 自动登录 cookie设定 */
		String autoLogin = RC4Util.encode(userName);
		CookieUtil.setCookie(request, response, cookieName, autoLogin, maxAge);
	}
	
	/**
	 * 创造cookie.
	 * @throws UnsupportedEncodingException 
	 *
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response,
			String cookieName, String userName, int maxAge, String domain) throws UnsupportedEncodingException{
		/* 自动登录 cookie设定 */
		String autoLogin = RC4Util.encode(userName);
		CookieUtil.setCookie(request, response, cookieName, autoLogin, maxAge, domain);
	}
	
	/**
	 * 获得cookie对应的内容
	 *
	 * @param request
	 * @param key
	 * @return Cookie
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie cookie = CookieUtil.getCookie(request, key);
		if(cookie != null){
			String autoLogin = cookie.getValue();
			autoLogin = RC4Util.decode(autoLogin);
			return autoLogin;
		}else{
			return null;
		}
	}

}
