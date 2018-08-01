package com.kingyee.fuxi.security.dao;

import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.common.db.CommonDao;
import com.kingyee.common.model.ExtPageInfo;
import com.kingyee.common.util.PropertyConst;
import com.kingyee.fuxi.security.db.AuthModule;
import com.kingyee.fuxi.security.db.AuthResource;
import com.kingyee.fuxi.security.db.AuthRole;
import com.kingyee.fuxi.security.db.AuthRoleRes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：角色资源服务类
 * @author 周振平
 * @createTime May 4, 2011 2:11:26 PM
 */
@Repository
public class RoleResourceDao extends CommonDao {

	/**
	 * 根据角色ID取得角色名称
	 * @param roleId
	 * @return
	 */
	public String getRoleName(Long roleId){
		if(null == roleId){
            return "";
        }

		String sql = " from AuthRole role where role.id = " + roleId;
		List<AuthRole> role = dao.createQuery(sql);
		if(null != role && !role.isEmpty()){
			return role.get(0).getAroName();
		}

		return "";
	}
	
	/**
	 * 获取角色列表，分页
	 * @return
	 */
	public List<Object> getRoleList(ExtPageInfo pageInfo) {
		List<Object> result = new ArrayList<Object>();
		List<AuthRole> roleList = new ArrayList<AuthRole>();
		String query = "from AuthRole ";
		roleList = dao.createQuery(query, pageInfo.getStart(), pageInfo.getLimit());
		long count = dao.createCountQuery("select count(ar.id) from AuthRole ar ");
		result.add(roleList);
		result.add(count);
		return result;
	}
	
	/**
	 * 取得角色列表
	 * @return
	 * @throws Exception
	 */
	public List<AuthRole> listRole() throws Exception {
		String hql = null;
		// 如果是系统管理员，则查找的角色列表包含系统管理员角色，否则不包含
		if(AdminUserUtil.isSysAdmin()){
			hql = " FROM AuthRole AS ar ";
		}else{
			hql = " FROM AuthRole AS ar WHERE ar.id != " + PropertyConst.ROLE_SYS_ADMIN;
		}
		return dao.createQuery(hql);
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	public boolean delRole(AuthRole role) {
		
		boolean result = true;
		if(this.roleHasUsed(role.getAroId()) > 0) {  //判断是否存在用户启用该角色
			return false;
		}
		dao.delete(role);
		this.deleteRoleRes(role.getAroId());  //删除角色资源表中的数据
		return result;
	}

	/**
	 * 判断是否存在用户启用该角色，启用则不可删除
	 * @param roleId
	 * @return count
	 * @throws Exception
	 */
	private Long roleHasUsed(Long roleId) {
		String sql = "select count(u.id) from CmAdminUser u where u.auRole = " + roleId;
		List<Long> list = new ArrayList<Long>();
		list = dao.createQuery(sql);
		
		return list.get(0);
	}
	
	/**
	 * 删除角色成功后删除该角色对应的角色资源数据
	 * @param roleId
	 */
	public void deleteRoleRes(Long roleId) {
		List<AuthRoleRes> arr = this.getRoleResListByRoleId(roleId);
		if(arr != null) {
			for(AuthRoleRes rr : arr) {
				dao.delete(rr);
			}
		}
	}
	
	
	/**
	 * 根据roleId获取角色资源列表
	 * @param roleId
	 * @return
	 */
	public List<AuthRoleRes> getRoleResListByRoleId(Long roleId) {
		List<AuthRoleRes> arrList = new ArrayList<AuthRoleRes>();
		String sql = "from AuthRoleRes rr where rr.arrRoleId = " + roleId;
		sql = sql + " ORDER BY rr.arrModule ";
		arrList = dao.createQuery(sql);
		return arrList;
	}
	
	/**
	 * 权限资源列表
	 * @return
	 */
	public List<AuthResource> getResourceList(AuthModule mod) {
		List<AuthResource> resList = new ArrayList<AuthResource>();
		String query = "from AuthResource res where res.arModule = " + mod.getAmId() + "ORDER BY arPermission ASC";
		resList = dao.createQuery(query);
		return resList;
	}

	/**
	 * 根据roleId和模块ID获取角色资源
	 * @param roleId
	 * @return
	 */
	public AuthRoleRes getRoleResByRIdAMId(Long roleId, Long moduleId, Long resId) {
		List<AuthRoleRes> arrList = new ArrayList<AuthRoleRes>();
		String sql = "from AuthRoleRes rr where rr.arrRoleId = " + roleId + " and rr.arrModule = " + moduleId + " and rr.arrRes = " + resId;
		arrList = dao.createQuery(sql);
		if(arrList != null && arrList.size() > 0) {
			return arrList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据访问的url，取得访问此url所对应需要的权限
	 * @author peihong
	 * @param url 权限URL
	 * @return AuthResource
	 */
	public AuthResource getAuthResource(String url) {
		List<AuthResource> arList = new ArrayList<AuthResource>();
		String sql = "FROM AuthResource AS ar WHERE ar.arUrl =:arUrl";
		Map<String, String> parp = new HashMap<String, String>();
		parp.put("arUrl", url);
		arList = dao.createQuery(sql, parp);
		if(arList != null && arList.size() > 0) {
			return arList.get(0);
		}
		return null;
	}
}