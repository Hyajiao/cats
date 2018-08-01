package com.kingyee.cats.dao.admin;

import com.kingyee.cats.db.CatsExpertGroup;
import com.kingyee.cats.db.CatsExpertGroupDetail;
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
public class AdminExpertGroupDao extends CommonDao {

    /**
     * 专家组列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CatsExpertGroup> list(SearchBean searchBean, POJOPageInfo<CatsExpertGroup> pageInfo){
        Map<String, Object> parp = searchBean.getParps();
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsExpertGroup AS ep WHERE 1 = 1 ");
        if(parp.get("groupName") != null){
            hql.append(" AND (ep.cegExpertGroupName LIKE :groupName ) ");
        }
        // 状态
        if(parp.get("state") != null){
            hql.append(" AND ep.cegIsValid = :state ");
        }
        Long count = dao.createCountQuery("SELECT COUNT(*) " + hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
            pageInfo.setItems(new ArrayList<CatsExpertGroup>());
            pageInfo.setCount(0);
            return pageInfo;
        }
        String order = " ORDER BY ep.cegCreateTime DESC";
        List<CatsExpertGroup> list = dao.createQuery(hql.toString() + order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;
    }

    /**
     * 取得所有有效的专家组列表
     * @return
     */
    public List<CatsExpertGroup> getAllExpertGroupList(){
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsExpertGroup AS ep WHERE ep.cegIsValid = 1 ");
        return dao.createQuery(hql.toString());
    }


    /**
     * 专家列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CatsExpertGroupDetail> expertList(SearchBean searchBean, POJOPageInfo<CatsExpertGroupDetail> pageInfo){
        Map<String, Object> parp = searchBean.getParps();
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CatsExpertGroupDetail AS t ");
        hql.append(" WHERE t.cegdCegId =:cegId ");
        if(parp.get("expertName") != null){
            hql.append(" AND (t.cegdRealName LIKE :expertName ) ");
        }
        Long count = dao.createCountQuery("SELECT COUNT(*) " + hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
            pageInfo.setItems(new ArrayList<CatsExpertGroupDetail>());
            pageInfo.setCount(0);
            return pageInfo;
        }
        String order = " ORDER BY t.cegdCreateTime DESC";
        List<CatsExpertGroupDetail> list = dao.createQuery(hql.toString() + order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;
    }


    /**
     * 查看用户名是否已经存在
     * @param medliveId 医脉通id
     * @param cegId 专家组id
     * @return CatsExpertGroupDetail
     */
    public CatsExpertGroupDetail getExpertByMedliveId(Long medliveId, Long cegId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("medliveId", medliveId);
        parp.put("cegId", cegId);
        String sql = " FROM CatsExpertGroupDetail u WHERE u.cegdMedliveId =:medliveId AND u.cegdCegId =:cegId ";
        return dao.findOne(sql, parp);
    }

    /**
     * 查看用户名是否已经存在
     * @param cegdId 专家id
     * @return
     */
    public CatsExpertGroupDetail getExpertByCegdId(Long cegdId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("cegdId", cegdId);
        String sql = " FROM CatsExpertGroupDetail u WHERE u.cegdId =:cegdId ";
        return dao.findOne(sql, parp);
    }

}
