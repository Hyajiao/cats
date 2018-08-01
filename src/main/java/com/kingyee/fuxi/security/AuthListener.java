package com.kingyee.fuxi.security;

import com.kingyee.common.db.BaseDAO;
import com.kingyee.fuxi.security.db.AuthResource;
import com.kingyee.fuxi.security.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * 初始化 资源hashMap
 * @author peihong
 * @version 2011-06-02
 */
public class AuthListener implements ApplicationListener {

    @Autowired
	private ResourceService resourceService;
	
	@Override
	public void onApplicationEvent(ApplicationEvent appEvent) {
		if(appEvent instanceof ContextRefreshedEvent){
			List<AuthResource> arList = resourceService.listAuthResource();
			if(arList != null){
				// 初始化 资源hashMap
				AuthUtil.initHashMap(arList);
			}
			
		}
	}

}
