package com.kingyee.fuxi.security.service;

import com.kingyee.common.IBaseService;
import com.kingyee.fuxi.security.dao.RoleDao;
import com.kingyee.fuxi.security.db.AuthRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色管理
 * 
 */
@Service
public class RoleService implements IBaseService {

    private final static Logger logger = LoggerFactory
            .getLogger(RoleService.class);

    @Autowired
    private RoleDao dao;

    /**
     * 显示所有的角色列表
     * @return
     */
    public List<AuthRole> listRole(){
        return dao.listRole();
    }

}
