/**
 * 
 */
package com.kingyee.fuxi.security.dao;

import com.kingyee.common.db.CommonDao;
import com.kingyee.fuxi.security.db.AuthModule;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：权限模块管理服务类
 * @author 周振平
 * @CreateTime 2011-5-31 下午03:31:35
 */
@Repository
public class ModuleDao extends CommonDao {

	/**
	 * 获取权限模块列表
	 * @return
	 */
	public List<AuthModule> getModuleList() {
		List<AuthModule> resList = new ArrayList<AuthModule>();
		String query = "from AuthModule ";
		resList = dao.createQuery(query);
		return resList;
	}

}
