package com.kingyee.cats.dao.admin;

import com.kingyee.common.db.CommonDao;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.util.TimeUtil;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.cats.enums.AuthenticationStatusEnum;
import com.kingyee.cats.enums.YesNoEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台用户管理
 *
 * @author peihong
 * 2017年08月17日
 */
@Repository
public class AdminUserDao extends CommonDao {


    /**
     * 微信用户列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CmWechatUser> wechatUserList(SearchBean searchBean, POJOPageInfo<CmWechatUser> pageInfo){
        Map<String, Object> parp = searchBean.getParps();
        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmWechatUser AS user WHERE 1=1 ");
        if(parp.get("nickName") != null){
            hql.append("AND user.cwuNickname LIKE :nickName ");
        }
        //订阅状态
        if(parp.get("subscribe") != null){
            hql.append("AND user.cwuSubscribe = :subscribe ");
        }
        Long count = dao.createCountQuery("SELECT COUNT(*) "+hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
            pageInfo.setItems(new ArrayList<CmWechatUser>());
            pageInfo.setCount(0);
            return pageInfo;
        }
        String order = "ORDER BY user.cwuCreateTime DESC";
        List<CmWechatUser> list = new ArrayList<CmWechatUser>();
        list = dao.createQuery(hql.toString()+order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;
    }

    /**
     * 注册用户列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CmUser> regUserList(SearchBean searchBean, POJOPageInfo<CmUser> pageInfo){
        Map<String, Object> parp = searchBean.getParps();
        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmUser AS user WHERE 1=1 ");
        hql.append("AND user.cuRealName != '' ");
        hql.append("AND user.cuRealName IS NOT NULL ");

        String startDate = (String)parp.get("startDate");
        if(startDate != null && !startDate.trim().equals("")){
            hql.append(" AND user.cuCreateTime >= :startDate ");
            parp.put("startDate", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE));
        }
        String endDate = (String)parp.get("endDate");
        if(endDate != null && !endDate.trim().equals("")){
            hql.append(" AND user.cuCreateTime < :endDate ");
            // 增加一天
            Long date = TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE) + (24 * 3600 * 1000L);
            parp.put("endDate", date);
        }

        if(parp.get("realName") != null){
            hql.append("AND user.cuRealName LIKE :realName ");
        }
        //注册用户id
        /*if(parp.get("cuId") != null){
            hql.append("AND user.cuId = :cuId ");
        }*/
		//医脉通id
		if(parp.get("cuMedliveId") != null){
			hql.append("AND user.cuMedliveId = :cuMedliveId ");
		}
        // 是否认证
        if(parp.get("cuIsAuthentication") != null){
            hql.append("AND user.cuIsAuthentication = :cuIsAuthentication ");
        }
        // 用户类型
        if(parp.get("userType") != null && StringUtils.isNotEmpty(parp.get("userType").toString())){
            hql.append("AND user.cuType = :userType ");
        }

        Long count = dao.createCountQuery("SELECT COUNT(*) "+hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
            pageInfo.setItems(new ArrayList<CmUser>());
            pageInfo.setCount(0);
            return pageInfo;
        }
        String order = "ORDER BY user.cuCreateTime DESC";

        List<CmUser> list = new ArrayList<CmUser>();
        if(pageInfo != null){
            list = dao.createQuery(hql.toString()+order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        }else{
            pageInfo = new POJOPageInfo<CmUser>();
            list = dao.createQuery(hql.toString()+order, parp);
        }

        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;

    }

    /**
     * 根据用户id取得用户
     * @param cuId 用户表主键
     * @return
     */
    public CmUser getRegUserById(Long cuId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("cuId", cuId);

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmUser AS user WHERE ");
        hql.append(" user.cuId =:cuId ");

        CmUser user = dao.findOne(hql.toString(), parp);

        return user;

    }


    /**
     * 根据用户id取得用户
     * @param cuId 用户表主键
     * @return
     */
    public CmWechatUser getWechatUserByCuId(Long cuId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("cuId", cuId);

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmWechatUser AS user WHERE ");
        hql.append(" user.cwuCuId =:cuId ");

        return dao.findOne(hql.toString(), parp);
    }


    /**
     * 根据用户的省份
     * @return
     */
    public List<String> getUserProvince(long projectExecutiveId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("cuIsAuthentication", AuthenticationStatusEnum.APPROVE.ordinal());
        parp.put("projectExecutiveId", projectExecutiveId);

        StringBuilder hql = new StringBuilder();
        hql.append(" SELECT user.cuProvince FROM CmUser AS user ");
        hql.append(" WHERE cuIsAuthentication = :cuIsAuthentication ");
        hql.append(" AND cuProjectExecutiveId = :projectExecutiveId ");
        hql.append(" GROUP BY user.cuProvince ");
        return dao.createQuery(hql.toString(), parp);
    }


    /**
     * 根据省份，取得城市
     * @return
     */
    public List<String> getUserCity(String province, long projectExecutiveId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("cuIsAuthentication", AuthenticationStatusEnum.APPROVE.ordinal());
        parp.put("projectExecutiveId", projectExecutiveId);
        parp.put("province", province);

        StringBuilder hql = new StringBuilder();
        hql.append(" SELECT user.cuCity FROM CmUser AS user ");
        hql.append(" WHERE user.cuProvince = :province ");
        hql.append(" AND cuIsAuthentication = :cuIsAuthentication ");
        hql.append(" AND cuProjectExecutiveId = :projectExecutiveId ");
        hql.append(" GROUP BY user.cuCity ");

        return dao.createQuery(hql.toString(), parp);
    }

    /**
     * 根据省份和城市，取得医院
     * @return
     */
    public List<String> getUserHospital(String province, String city, long projectExecutiveId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("cuIsAuthentication", AuthenticationStatusEnum.APPROVE.ordinal());
        parp.put("projectExecutiveId", projectExecutiveId);
        parp.put("province", province);
        parp.put("city", city);

        StringBuilder hql = new StringBuilder();
        hql.append(" SELECT user.cuHospital FROM CmUser AS user ");
        hql.append(" WHERE user.cuProvince = :province ");
        hql.append(" AND user.cuCity = :city ");
        hql.append(" AND cuIsAuthentication = :cuIsAuthentication ");
        hql.append(" AND cuProjectExecutiveId = :projectExecutiveId ");
        hql.append(" GROUP BY user.cuHospital ");

        return dao.createQuery(hql.toString(), parp);
    }

    /**
     * 根据省份和城市，医院，取得用户
     * @return
     */
    public List<CmUser> getUser(String province, String city, String hospital, long projectExecutiveId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("cuIsAuthentication", AuthenticationStatusEnum.APPROVE.ordinal());
        parp.put("projectExecutiveId", projectExecutiveId);
        parp.put("province", province);
        parp.put("city", city);
        parp.put("hospital", hospital);


        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmUser AS user ");
        hql.append(" WHERE user.cuProvince = :province ");
        hql.append(" AND user.cuCity = :city ");
        hql.append(" AND user.cuHospital = :hospital ");
        hql.append(" AND cuIsAuthentication = :cuIsAuthentication ");
        hql.append(" AND cuProjectExecutiveId = :projectExecutiveId ");

        return dao.createQuery(hql.toString(), parp);
    }
}
