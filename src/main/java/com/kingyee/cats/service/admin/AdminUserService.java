package com.kingyee.cats.service.admin;

import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.sms.SmsTplUtil;
import com.kingyee.cats.common.SmsConst;
import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.dao.admin.AdminUserDao;
import com.kingyee.cats.db.CmAdminUser;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.cats.enums.AuthenticationStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 前台用户管理
 * 
 * @author peihong
 * 2017年08月17日
 */
@Service
public class AdminUserService {
	
    @Autowired
    private AdminUserDao dao;
    @Autowired
    private AdminSysUserService adminSysUserService;

    /**
     * 微信用户列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CmWechatUser> wechatUserList(SearchBean searchBean, POJOPageInfo<CmWechatUser> pageInfo){
        return dao.wechatUserList(searchBean, pageInfo);
    }


    /**
     * 注册用户列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CmUser> regUserList(SearchBean searchBean, POJOPageInfo<CmUser> pageInfo){
        return dao.regUserList(searchBean, pageInfo);
    }

    /**
     * 根据用户id取得用户
     * @param cuId 用户表主键
     * @return
     */
    public CmUser getRegUserById(Long cuId){
        return dao.getRegUserById(cuId);
    }

    /**
     * 注册用户认证
     * @param cuId 用户表主键
     * @return
     */
    public CmUser updateRegUserAuth(Long cuId, Integer cuIsAuthentication){
        CmUser user = dao.getRegUserById(cuId);
        user.setCuIsAuthentication(cuIsAuthentication);
        dao.update(user);

        return user;
    }

    /**
     * 根据用户的省份
     * @return
     */
    public List<String> getUserProvince(){
        long projectExecutiveId = AdminUserUtil.getUserId();
        return dao.getUserProvince(projectExecutiveId);
    }


    /**
     * 根据省份，取得城市
     * @return
     */
    public List<String> getUserCity(String province){
        long projectExecutiveId = AdminUserUtil.getUserId();
        return dao.getUserCity(province, projectExecutiveId);
    }

    /**
     * 根据省份和城市，取得医院
     * @return
     */
    public List<String> getUserHospital(String province, String city){
        long projectExecutiveId = AdminUserUtil.getUserId();
        return dao.getUserHospital(province, city, projectExecutiveId);
    }

    /**
     * 根据省份和城市，医院，取得用户
     * @return
     */
    public List<CmUser> getUser(String province, String city, String hospital){
        long projectExecutiveId = AdminUserUtil.getUserId();
        return dao.getUser(province, city, hospital, projectExecutiveId);
    }

}
