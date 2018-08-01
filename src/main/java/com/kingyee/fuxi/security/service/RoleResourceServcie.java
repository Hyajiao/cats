package com.kingyee.fuxi.security.service;

import com.kingyee.common.model.ExtPageInfo;
import com.kingyee.fuxi.security.dao.RoleResourceDao;
import com.kingyee.fuxi.security.db.AuthModule;
import com.kingyee.fuxi.security.db.AuthResource;
import com.kingyee.fuxi.security.db.AuthRole;
import com.kingyee.fuxi.security.db.AuthRoleRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：角色资源逻辑处理类
 * @author 周振平
 * @createTime May 4, 2011 2:10:26 PM
 */
@Service
public class RoleResourceServcie {

    private final static Logger logger = LoggerFactory
            .getLogger(RoleResourceServcie.class);

    @Autowired
	private RoleResourceDao roleResourceDao;
	
	/**
	 * 根据角色ID取得角色名称
	 * @param roleId
	 * @return
	 */
	public String getRoleName(Long roleId){
		return roleResourceDao.getRoleName(roleId);
	}
	
	/**
	 * 根据ID获取角色bean
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AuthRole getRole(Long id) throws Exception {
        return roleResourceDao.load(AuthRole.class, id);
	}
	
	/**
	 * 获取角色列表
	 * @return
	 */
	public List<Object> getRoleList(ExtPageInfo pageInfo) throws Exception {
        return roleResourceDao.getRoleList(pageInfo);
	}
	
	/**
	 * 取得角色列表
	 * @return
	 * @throws Exception
	 */
	public List<AuthRole> listRole() throws Exception {
		return roleResourceDao.listRole();
	}
	
	/**
	 * 新增或修改角色
	 * @return
	 */
	public Long saveOrUpdateRole(AuthRole role) throws Exception {
		Long roleId = null;
        if(role.getAroId() == null || role.getAroId() == 0) {
            roleId = roleResourceDao.save(role);
        } else {
            roleResourceDao.update(role);
            roleId = role.getAroId();
        }

		return roleId;
	}
	
	/**
     * 删除角色
     * @return
     */
    public boolean delRole(Long roleId) throws Exception {

        boolean result = false;
        AuthRole role = roleResourceDao.load(AuthRole.class, roleId);
        result = roleResourceDao.delRole(role);

        return result;
    }

    /**
     * 删除角色
     * @return
     */
    public void delRoleRes(Long roleId) throws Exception {
        roleResourceDao.deleteRoleRes(roleId);
    }

	
	/**
	 * 获取权限资源列表
	 * @return
	 * @throws Exception
	 */
	public List<AuthResource> getResourceList(AuthModule mod) throws Exception {
        return roleResourceDao.getResourceList(mod);
	}
	
	/**
	 * 根据roleId查询角色资源
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<AuthRoleRes> getRoleResByRoleId(Long roleId) throws Exception {
        return roleResourceDao.getRoleResListByRoleId(roleId);
	}
	
	/**
	 * 根据roleId和模块ID查询角色资源
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public AuthRoleRes getRoleResByRIdAMId(Long roleId, Long moduleId, Long resId) throws Exception {
        return roleResourceDao.getRoleResByRIdAMId(roleId, moduleId, resId);
	}
	
	/**
	 * 新增或修改角色资源
	 * @return
	 */
	public Long saveOrUpdateRoleRes(AuthRoleRes roleRes) throws Exception {
		
		Long roleResId = null;
        if(roleRes.getArrId() == null || roleRes.getArrId() == 0) {
            roleResId = roleResourceDao.save(roleRes);
        } else {
            roleResourceDao.update(roleRes);
            roleResId = roleRes.getArrId();
        }

		return roleResId;
	}

	/**
	 * 根据访问的url，取得访问此url所对应需要的权限
	 * @author peihong
	 * @param url 权限URL
	 * @return AuthResource
	 */
	public AuthResource getAuthResource(String url){
		return roleResourceDao.getAuthResource(url);
	}
	

	
}
