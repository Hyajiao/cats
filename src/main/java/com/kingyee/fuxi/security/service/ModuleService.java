package com.kingyee.fuxi.security.service;

import com.kingyee.fuxi.security.dao.ModuleDao;
import com.kingyee.fuxi.security.db.AuthModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：权限模块逻辑处理类
 * @author 周振平
 * @CreateTime 2011-5-31 下午03:29:44
 */
@Service
public class ModuleService {
	
    private final static Logger log = LoggerFactory
            .getLogger(ModuleService.class);

    @Autowired
	private ModuleDao dao;

	/**
	 * 获取权限模块列表
	 * @return
	 */
	public List<AuthModule> getModuleList() {
        return dao.getModuleList();
	}

}
