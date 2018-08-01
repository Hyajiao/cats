package com.kingyee.cats.dao;

import com.kingyee.common.db.CommonDao;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.db.CmWechatUser;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关DAO
 * Created by ph on 2016/7/29.
 */
@Repository
public class UserDao extends CommonDao {


    /**
     * 根据openid，取得用户的微信信息
     * @param wechatId
     * @return
     */
    public CmWechatUser getWechatUserByWechatId(Long wechatId){

        Map<String, Long> parp = new HashMap<>();
        parp.put("wechatId", wechatId);

        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmWechatUser WHERE cwuId = :wechatId");

        return dao.findOne(hql.toString(), parp);
    }

    /**
     * 根据openid，取得用户的微信信息
     * @param openid
     * @return
     */
    public CmWechatUser getWechatUserByOpenid(String openid){

        Map<String, String> parp = new HashMap<>();
        parp.put("openid", openid);

        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmWechatUser WHERE cwuOpenid = :openid");

        return dao.findOne(hql.toString(), parp);
    }

    /**
     * 根据cmuser表的用户id，取得用户的微信信息
     * @param userId
     * @return
     */
    public CmWechatUser getWechatUserByUserId(Long userId){

        Map<String, Long> parp = new HashMap<>();
        parp.put("userId", userId);

        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmWechatUser WHERE cwuCuId = :userId");

        return dao.findOne(hql.toString(), parp);

    }


    /**
     * 根据telNo，取得用户的信息
     * @param telNo 手机号
     * @return
     */
    public CmUser getUserByTelNo(String telNo){
        Map<String, String> parp = new HashMap<>();
        parp.put("telNo", telNo);

        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmUser WHERE cuTelNo = :telNo");

        return dao.findOne(hql.toString(), parp);

    }

    /**
     * 根据medliveId取得用户信息
     * @param medliveId
     * @return
     */
    public CmUser getUserByMedliveId(Long medliveId){
        Map<String, Long> parp = new HashMap<>();
        parp.put("medliveId", medliveId);

        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmUser WHERE cuMedliveId = :medliveId");
        return dao.findOne(hql.toString(), parp);
    }

}
