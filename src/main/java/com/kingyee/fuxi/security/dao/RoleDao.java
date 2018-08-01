package com.kingyee.fuxi.security.dao;

import com.kingyee.cats.db.CmAdminUser;
import com.kingyee.common.db.CommonDao;
import com.kingyee.fuxi.security.db.AuthRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ph on 2016/7/29.
 * @author ph
 */
@Repository
public class RoleDao extends CommonDao {


    /**
     * 显示所有的角色列表
     * @return
     */
    public List<AuthRole> listRole(){
        CmAdminUser user = null;
        StringBuilder hql = new StringBuilder();
        hql.append("FROM AuthRole ");
        return dao.createQuery(hql.toString());
    }

}
