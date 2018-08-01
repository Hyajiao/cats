package com.kingyee.cats.dao.admin;

import com.kingyee.cats.db.CatsProject;
import com.kingyee.cats.db.CatsProjectEnterpriseUserLink;
import com.kingyee.cats.db.CatsProjectLiteratureConfig;
import com.kingyee.cats.db.CatsProjectNewsConfig;
import com.kingyee.common.db.CommonDao;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ph on 2016/7/29.
 */
@Repository
public class AdminProjectDao extends CommonDao {

    /**
     * 取得项目列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CatsProject> list(SearchBean searchBean, POJOPageInfo<CatsProject> pageInfo){
        Map<String, Object> parp = searchBean.getParps();
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsProject AS p WHERE 1 = 1 ");

        if(parp.get("projectName") != null){
            hql.append(" AND p.cpProjectName LIKE :projectName ");
        }
        if(parp.get("companyName") != null){
            hql.append(" AND p.cpCompanyName LIKE :companyName ");
        }
        if(parp.get("endDate") != null){
            hql.append(" AND p.cpStartTime <=:endDate ");
        }
        if(parp.get("startDate") != null){
            hql.append(" AND p.cpEndTime >=:startDate ");
        }

        if(parp.get("state") != null){
            hql.append(" AND p.cpIsValid =:state ");
        }

        Long count = dao.createCountQuery("SELECT COUNT(*) " + hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
            pageInfo.setItems(new ArrayList<CatsProject>());
            pageInfo.setCount(0);
            return pageInfo;
        }

        String order = " ORDER BY p.cpCreateTime DESC ";
        List<CatsProject> list = dao.createQuery(hql.toString() + order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;
    }

    /**
     * 取得可用项目列表
     * @return
     */
    public List<CatsProject> getAvailableProjectList(){
        Map<String, Long> parp = new HashMap<>();

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsProject AS p ");
        hql.append(" WHERE p.cpIsValid = 1 ");

        return dao.createQuery(hql.toString(), parp);
    }

    /**
     * 取得项目资讯配置列表
     * @return
     */
    public List<CatsProjectNewsConfig> listProjectNewsConfig(Long cpId){
        Map<String, Long> parp = new HashMap<>();
        parp.put("cpId", cpId);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsProjectNewsConfig AS p WHERE ");
        hql.append(" p.cpncCpId = :cpId ");
        String order = " ORDER BY p.cpncCreateTime DESC ";
        return dao.createQuery(hql.toString() + order, parp);
    }

    /**
     * 取得项目文献配置列表
     * @return
     */
    public List<CatsProjectLiteratureConfig> listProjectLiteratureConfig(Long cpId){
        Map<String, Long> parp = new HashMap<>();
        parp.put("cpId", cpId);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsProjectLiteratureConfig AS p WHERE ");
        hql.append(" p.cplcCpId = :cpId ");
        String order = " ORDER BY p.cplcCreateTime DESC ";
        return dao.createQuery(hql.toString() + order, parp);
    }


    /**
     * 查看用户的没有关联的项目
     * @param uid 用户id
     * @return
     */
    public List<CatsProject> getUnLinkedProjectByUserId(Long uid) {
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsProject AS p  ");
        hql.append(" WHERE p.cpIsValid = 1  ");
        hql.append(" AND p.cpId NOT IN ( SELECT cpeulCpId FROM CatsProjectEnterpriseUserLink WHERE cpeulCauId =:uid)   ");
		hql.append(" ORDER BY p.cpCreateTime DESC  ");

        Map<String, Object> parp = new HashMap<String,Object>();
        parp.put("uid", uid);

        return dao.createQuery(hql.toString(), parp);
    }

    /**
     * 查看用户的关联的项目
     * @param uid 用户id
     * @return
     */
    public List<CatsProject> getLinkedProjectByUserId(Long uid) {
        StringBuilder hql = new StringBuilder();
        hql.append(" SELECT p FROM CatsProject AS p, CatsProjectEnterpriseUserLink AS peul ");
        hql.append(" WHERE p.cpIsValid = 1  ");
        hql.append(" AND p.cpId = peul.cpeulCpId ");
		hql.append(" AND peul.cpeulCauId = :uid ");
		hql.append(" ORDER BY peul.cpeulCreateTime DESC ");

        Map<String, Object> parp = new HashMap<String,Object>();
        parp.put("uid", uid);

        return dao.createQuery(hql.toString(), parp);
    }

    /**
     * 查看用户的关联的项目
     * @param uid 用户id
     * @param projectId 项目id
     * @return
     */
    public CatsProjectEnterpriseUserLink getLinkedProject(Long uid, Long projectId) {
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsProjectEnterpriseUserLink AS peul ");
        hql.append(" WHERE peul.cpeulCpId =:projectId  ");
        hql.append(" AND peul.cpeulCauId =:uid ");

        Map<String, Object> parp = new HashMap<String,Object>();
        parp.put("uid", uid);
        parp.put("projectId", projectId);

        return dao.findOne(hql.toString(), parp);
    }

}
