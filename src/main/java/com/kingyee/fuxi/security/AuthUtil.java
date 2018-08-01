package com.kingyee.fuxi.security;

import com.kingyee.fuxi.security.db.AuthResource;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户权限共通方法类
 * 
 * @author peihong
 * @version 2011-06-02
 */
public class AuthUtil {

	/** 资源hashMap key：资源的唯一名称，value：资源*/
	private static Map<String, AuthResource> resourceMap;
	/** 资源hashMap key：url，value：资源*/
	private static Map<String, AuthResource> urlMap;
	
	/**
	 * 初始化资源 hashMap
	 * @param arList
	 */
	public static void initHashMap(List<AuthResource> arList){
		// 初始化时
		if(resourceMap == null){
			resourceMap = new HashMap<String, AuthResource>();
		}else{
			// 重新初始数据时，先清空
			resourceMap.clear();
		}
		// 初始化时
		if(urlMap == null){
			urlMap = new HashMap<String, AuthResource>();
		}else{
			// 重新初始数据时，先清空
			urlMap.clear();
		}
		if(arList != null && !arList.isEmpty()){
			for (AuthResource authResource : arList) {
				if(authResource.getArPermission() != null && !"".equals(authResource.getArPermission())){
					resourceMap.put(authResource.getArPermission(), authResource);
				}
				if(authResource.getArUrl() != null && !"".equals(authResource.getArUrl())){
					urlMap.put(authResource.getArUrl(), authResource);
				}
			}
		}
	}

	/**
	 * 根据资源唯一标示判断用户是否具有此资源的访问权限(主要前台界面使用)
	 * @param uniqueName 资源唯一标示
	 * @return true：有权限；false：没有权限
	 */
	public static boolean hasAuth(String uniqueName){
		AuthResource authResource = resourceMap.get(uniqueName);
		return hasAuth(authResource);
	}

	
	/**
	 * 判断是否具有访问此资源的权限
	 * @param authResource 资源
	 * @return true：有权限；false：没有权限
	 */
	public static boolean hasAuth(AuthResource authResource){
		boolean hasAuth = false;
		if(authResource != null){
            Subject subject = SecurityUtils.getSubject();
            if(subject.isPermitted(authResource.getArPermission())){
                hasAuth = true;
            }else{
                hasAuth = false;
            }
		}else{
			// 无此访问资源，有权限访问
			hasAuth = true;
		}
		return hasAuth;
	}

	
	
	public static Map<String, AuthResource> getResourceMap() {
		return resourceMap;
	}

	public static void setResourceMap(Map<String, AuthResource> resourceMap) {
		AuthUtil.resourceMap = resourceMap;
	}

	public static Map<String, AuthResource> getUrlMap() {
		return urlMap;
	}

	public static void setUrlMap(Map<String, AuthResource> urlMap) {
		AuthUtil.urlMap = urlMap;
	}
	
}
