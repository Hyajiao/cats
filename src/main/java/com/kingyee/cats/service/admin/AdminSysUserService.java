package com.kingyee.cats.service.admin;

import com.google.gson.JsonElement;
import com.kingyee.cats.db.CmAdminUser;
import com.kingyee.common.IBaseService;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.util.encrypt.EncryptUtil;
import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.dao.admin.AdminSysUserDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 用户管理
 * 
 * @author fanyongqian
 * 2016年10月31日
 */
@Service
public class AdminSysUserService implements IBaseService {

    private final static Logger logger = LoggerFactory
            .getLogger(AdminSysUserService.class);
	
    @Autowired
    private AdminSysUserDao dao;

    /**
     * 根据用户名查看此用户是否存在
     * @param name 用户名
     * @return
     */
    public CmAdminUser getUserByNameAndPassword(String name){
        return dao.getUserByNameAndPassword(name);
    }

    /**
     * 根据用户名和密码查看此用户是否存在
     * @param name 用户名
     * @param password 密码（编码后）
     * @return
     */
    public CmAdminUser getUserByNameAndPassword(String name, String password) {
        return dao.getUserByNameAndPassword(name, password);
    }

    /**
     * 用户列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CmAdminUser> list(SearchBean searchBean, POJOPageInfo<CmAdminUser> pageInfo){
        return dao.list(searchBean, pageInfo);
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    public Long save(CmAdminUser user) throws UnsupportedEncodingException {
        user.setAuCreateUserId(AdminUserUtil.getUserId());
        user.setAuCreateUserName(AdminUserUtil.getShowName());
        user.setAuCreateDate(System.currentTimeMillis());
        user.setAuIsValid(1);
        user.setAuPassword(EncryptUtil.getSHA256Value(user.getAuPassword()));
        return dao.save(user);
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    public CmAdminUser getById(Long id){
        return dao.get(CmAdminUser.class, id);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void edit(CmAdminUser user) throws UnsupportedEncodingException {
        CmAdminUser db = getById(user.getAuId());
        if(StringUtils.isNotEmpty(user.getAuPassword())){
            db.setAuPassword(EncryptUtil.getSHA256Value(user.getAuPassword()));
        }
        db.setAuShowName(user.getAuShowName());
        db.setAuTel(user.getAuTel());
        db.setAuEmail(user.getAuEmail());
        db.setAuZone(user.getAuZone());
        db.setAuRole(user.getAuRole());
        db.setAuUpdateUserId(AdminUserUtil.getUserId());
        db.setAuUpdateUserName(AdminUserUtil.getShowName());
        db.setAuUpdateDate(System.currentTimeMillis());
        dao.update(db);
    }

    /**
     * 更新信息
     * @param table
     */
    public void update(Object table) {
        dao.update(table);
    }

    /**
     * 查看用户名是否已经存在
     * @param name
     * @return
     */
    public boolean checkName(String name){
        return dao.checkName(name);
    }

    /**
     * 查看用户名是否已经存在
     * @param name
     * @return
     */
    public CmAdminUser getAdminUserByUserName(String name){
        return dao.getAdminUserByUserName(name);
    }

    /**
     * 根据id获取用户信息
     * @return
     */
    public JsonElement saveBatch(List<CmAdminUser> userList){
        for (CmAdminUser user: userList) {
            if(checkName(user.getAuUserName())){
                // 用户存在
                return JsonWrapper.newErrorInstance("用户" + user.getAuUserName() + "已存在");
            }else{
                dao.save(user);
            }
        }
        return JsonWrapper.newSuccessInstance();
    }

	/**
	 * 根据用户名查看此药企用户是否存在
	 * @param auUserName 用户名
	 * @return
	 */
	public CmAdminUser getEntUserByNameAndPassword(String auUserName) {
		return dao.getEntUserByNameAndPassword(auUserName);
	}
}
