package com.kingyee.fuxi.security.service;

import com.kingyee.common.IBaseService;
import com.kingyee.fuxi.security.dao.SecurityDao;
import com.kingyee.fuxi.security.db.AuthResource;
import com.kingyee.fuxi.security.db.AuthRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 权限相关的service
 * 
 * @author peihong
 * 2018年02月06日
 */
@Service
public class SecurityService implements IBaseService {

    private final static Logger logger = LoggerFactory
            .getLogger(SecurityService.class);
	
    @Autowired
    private SecurityDao dao;


    /**
     * 根据用户名，取得所有的角色
     * @param userName
     * @return
     */
    public Set<String> findRoleNameByUsername(String userName){

        StringBuffer hql = new StringBuffer();
        hql.append("SELECT aro FROM CmAdminUser AS au, AuthRole AS aro");
        hql.append(" WHERE au.auRole = aro.aroId ");
        hql.append(" AND au.auUserName =:userName ");

        Map<String, String> parp = new HashMap<>();
        parp.put("userName", userName);

        List<AuthRole> ls =  dao.createQuery(hql.toString(), parp);

        Set<String> permissionSet = new HashSet<>();
        for (AuthRole role: ls) {
            permissionSet.add(role.getAroNameEn());
        }
        return permissionSet;
    }

    /**
     * 根据用户名，取得所有的权限
     * @param userName
     * @return
     */
    public Set<String> findPermissionNameByUserName(String userName){

        StringBuffer hql = new StringBuffer();
        hql.append("SELECT ar FROM CmAdminUser AS au, AuthRoleRes AS ars, AuthResource AS ar ");
        hql.append(" WHERE au.auRole = ars.arrRoleId ");
        hql.append(" AND ars.arrRes = ar.arId ");
        hql.append(" AND au.auUserName =:userName ");


        Map<String, String> parp = new HashMap<>();
        parp.put("userName", userName);

        List<AuthResource> ls =  dao.createQuery(hql.toString(), parp);

        Set<String> permissionSet = new HashSet<>();
        for (AuthResource resource: ls) {
            permissionSet.add(resource.getArPermission());
        }
        return permissionSet;
    }

}
