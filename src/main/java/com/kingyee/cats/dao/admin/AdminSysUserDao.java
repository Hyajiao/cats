package com.kingyee.cats.dao.admin;

import com.kingyee.cats.db.CmAdminUser;
import com.kingyee.common.db.CommonDao;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.fuxi.security.db.AuthRole;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ph on 2016/7/29.
 */
@Repository
public class AdminSysUserDao extends CommonDao {


    /**
     * 根据用户名查看此用户是否存在
     * @param name 用户名
     * @return
     */
    public CmAdminUser getUserByNameAndPassword(String name){
        CmAdminUser user = null;
        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmAdminUser AS u WHERE u.auIsValid = 1 ");
        Map<String, Object> parp = new HashMap<String,Object>();
        parp.put("username", name);
        hql.append("AND u.auUserName = :username");
        List<CmAdminUser> list = dao.createQuery(hql.toString(),parp);
        if(list !=null && list.size() > 0){
            user = list.get(0);
        }
        return user;
    }

    /**
     * 根据用户名和密码查看此用户是否存在
     * @param name 用户名
     * @param password 密码（编码后）
     * @return
     */
    public CmAdminUser getUserByNameAndPassword(String name, String password) {
        CmAdminUser user = null;
        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmAdminUser AS u WHERE u.auIsValid = 1 ");
        hql.append(" AND u.auUserName = :username ");
        hql.append(" AND u.auPassword = :auPassword ");

        Map<String, Object> parp = new HashMap<String,Object>();
        parp.put("username", name);
        parp.put("auPassword", password);

        List<CmAdminUser> list = dao.createQuery(hql.toString(), parp);
        if(list !=null && list.size() > 0){
            user = list.get(0);
        }
        return user;
    }

    /**
     * 用户列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CmAdminUser> list(SearchBean searchBean, POJOPageInfo<CmAdminUser> pageInfo){
        Map<String, Object> parp = searchBean.getParps();
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmAdminUser AS user, AuthRole AS ar WHERE user.auRole = ar.aroId ");
        if(parp.get("keyword") != null){
            hql.append("AND (user.auUserName LIKE :keyword OR user.auShowName LIKE :keyword) ");
        }
        // 状态
        if(parp.get("state") != null){
            hql.append("AND user.auIsValid = :state ");
        }
        // 角色id
        if(parp.get("roleId") != null){
            hql.append("AND user.auRole = :roleId ");
        }
        Long count = dao.createCountQuery("SELECT COUNT(*) " + hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
            pageInfo.setItems(new ArrayList<CmAdminUser>());
            pageInfo.setCount(0);
            return pageInfo;
        }
        String order = "ORDER BY user.auCreateDate DESC";
        List<CmAdminUser> list = new ArrayList<CmAdminUser>();
        List<Object[]> objList = dao.createQuery("SELECT user, ar " + hql.toString()+order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        for(Object[] objs : objList){
            CmAdminUser adminUser = (CmAdminUser)objs[0];
            AuthRole role = (AuthRole)objs[1];
            adminUser.setRoleName(role.getAroName());
            list.add(adminUser);
        }
        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;
    }

    /**
     * 查看用户名是否已经存在
     * @param name
     * @return true 存在， false 不存在
     */
    public boolean checkName(String name){
        Map<String, Object> parp = new HashMap<String,Object>();
        parp.put("auUserName", name);
        String sql = "SELECT COUNT(*) FROM CmAdminUser u WHERE u.auUserName = :auUserName";
        Long count = dao.createCountQuery(sql,parp);
        if(count == null || count.intValue() == 0){
            return false;
        }else{
            //用户名已存在
            return true;
        }
    }

    /**
     * 查看用户名是否已经存在
     * @param name
     * @return
     */
    public CmAdminUser getAdminUserByUserName(String name){
        Map<String, Object> parp = new HashMap<String,Object>();
        parp.put("auUserName", name);
        String sql = "FROM CmAdminUser u WHERE u.auUserName = :auUserName";
        List<CmAdminUser> listAdminUser = dao.createQuery(sql,parp);
        if(listAdminUser != null && listAdminUser.size() > 0){
            return listAdminUser.get(0);
        }else{
            //用户名已存在
            return null;
        }
    }

	/**
	 * 根据用户名查看此药企用户是否存在
	 * @param name 用户名
	 * @return
	 */
	public CmAdminUser getEntUserByNameAndPassword(String name){
		CmAdminUser user = null;
		StringBuilder hql = new StringBuilder();
		hql.append("FROM CmAdminUser AS u WHERE u.auIsValid = 1 AND u.auRole = 2 ");
		Map<String, Object> parp = new HashMap<String,Object>();
		parp.put("username", name);
		hql.append("AND (u.auUserName = :username OR u.auTel = :username)");
		List<CmAdminUser> list = dao.createQuery(hql.toString(),parp);
		if(list !=null && list.size() > 0){
			user = list.get(0);
		}
		return user;
	}
}
