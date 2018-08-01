package com.kingyee.fuxi.security.dao;

import com.kingyee.common.db.CommonDao;
import com.kingyee.common.model.ExtPageInfo;
import com.kingyee.fuxi.security.db.AuthResource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：权限资源服务类
 * @author 周振平
 * @CreateTime 2011-5-30 下午04:39:28
 */
@Repository
public class ResourceDao extends CommonDao {

	/**
	 * 获取权限资源列表，分页
	 * @return
	 */
	public List<Object> getResList(ExtPageInfo pageInfo, Map<String,Object> parp) {
		List<Object> result = new ArrayList<Object>();
		List<Object[]> resList = new ArrayList<Object[]>();
		StringBuilder query = new StringBuilder();
		StringBuilder countQuery = new StringBuilder();
		query.append("select r.id,r.arModule,r.arName,r.arUrl,m.amName,r.arPermission,r.arDescription from AuthResource r,AuthModule m where r.arModule = m.id ");
		countQuery.append("select count(r.id) from AuthResource r where 1=1 ");
		String queryConditions = this.getQueryConditions(parp);
		query.append(queryConditions);
		countQuery.append(queryConditions);
		resList = dao.createQuery(query.toString(), parp, pageInfo.getStart(), pageInfo.getLimit());
		long count = dao.createCountQuery(countQuery.toString(), parp);
		result.add(resList);
		result.add(count);
		return result;
	}
	
	/**
	 * 根据查询条件拼接查询权限资源列表的SQL语句
	 * @param parp
	 * @return
	 */
	private String getQueryConditions(Map<String, Object> parp) {
		StringBuilder condition = new StringBuilder();
		if(parp.containsKey("arModule")) {
			condition.append(" and r.arModule=:arModule ");
		}
		if(parp.containsKey("arName")) {
			condition.append(" and r.arName like:arName ");
		}
		if(parp.containsKey("arPermission")) {
			condition.append(" and r.arPermission like:arPermission ");
		}
		if(parp.containsKey("arUrl")) {
			condition.append(" and r.arUrl like:arUrl ");
		}
		condition.append(" order by r.arModule, r.arPermission");
		return condition.toString();
	}

	/**
	 * 删除权限资源
	 * @return 
	 */
	public boolean delRes(AuthResource res) {
		
		boolean result = true;
		if(this.resBeUsedByRole(res) > 0) {  //判断角色是否使用过该权限
			return false;
		}
		dao.delete(res);
		return result;
	}
	
	/**
	 * 判断该权限资源是否被角色使用
	 * @param res
	 * @return
	 */
	private long resBeUsedByRole(AuthResource res) {
		long sum = 0l;
//		StringBuilder query = new StringBuilder();
//		query.append("select SUM(LOCATE(',").append(res.getArCode()).append(",'")
//			 .append(",r.arrRes)) from AuthRoleRes r where r.arrModule = ").append(res.getArModule());
//		List result = dao.createQuery(query.toString());
//		if(result.get(0) != null) {
//			sum = (Long)result.get(0);
//		}
		return sum;
	}
	
	/**
	 * 根据权限模块和权限代码查看该代码是否可用
	 * @param module
	 * @param arCode
	 * @return  false表示可用，true表示不可用
	 */
	public boolean findByModuleArCode(Long module, Integer arCode) {
		String query = "from AuthResource r where r.arModule = " + module + " and r.arCode = " + arCode;
		List<AuthResource> list = dao.createQuery(query);
		if(list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证权限标示是否唯一
	 * @param arPermission
	 * @return  false表示可用，true表示不可用
	 */
	public boolean findByUniqueName(String arPermission) {
		String query = "from AuthResource r where r.arPermission = '" + arPermission + "'";
		List<AuthResource> list = dao.createQuery(query);
		if(list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

}
