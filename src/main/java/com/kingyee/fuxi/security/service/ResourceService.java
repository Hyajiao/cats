package com.kingyee.fuxi.security.service;

import com.kingyee.common.model.ExtPageInfo;
import com.kingyee.fuxi.security.dao.ResourceDao;
import com.kingyee.fuxi.security.db.AuthResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 权限相关的service
 * 
 * @author peihong
 * 2018年02月06日
 */
@Service
public class ResourceService {

    private final static Logger logger = LoggerFactory
            .getLogger(ResourceService.class);
	
    @Autowired
    private ResourceDao dao;


    /**
     * 根据所有的资源
     * @return
     */
    public List<AuthResource> listAuthResource(){
        return dao.createQuery("FROM AuthResource AS ar");
    }


    /**
     * 根据ID获取权限资源bean
     * @param id
     * @return
     * @throws Exception
     */
    public AuthResource getResource(Long id) throws Exception {
        AuthResource model = null;
        try{
            model = dao.load(AuthResource.class, id);
        } catch(Exception e) {
            logger.error("查询权限资源失败！", e);
            throw new Exception("查询权限资源失败！");
        }
        return model;
    }

    /**
     * 根据模块ID和权限代码判断数据库中是否存在该权限代码
     * @param moduleId
     * @param arCode
     * @return
     * @throws Exception
     */
    public boolean findByModuleArCode(Long moduleId, Integer arCode) throws Exception {
        try{
            return dao.findByModuleArCode(moduleId, arCode);
        } catch(Exception e) {
            logger.error("查询权限资源失败！", e);
            throw new Exception("查询权限资源失败！");
        }
    }

    /**
     * 新增或修改权限资源
     * @return
     */
    public Long saveOrUpdateRole(AuthResource res) throws Exception {

        Long resId = null;

        try {
            if(res.getArId() == null || res.getArId() == 0) {
                resId = dao.save(res);
            } else {
                dao.update(res);
                resId = res.getArId();
            }
        } catch(Exception e) {
            logger.error("新增或修改权限资源出错！", e);
            throw new Exception("新增或修改权限资源出错！");
        }
        return resId;
    }

    /**
     * 获取权限资源列表
     * @return
     */
    public List<Object> getResList(ExtPageInfo pageInfo, Map<String,Object> parp) throws Exception {
        List<Object> resList = null;

        try {
            resList = dao.getResList(pageInfo, parp);
        } catch(Exception e) {
            logger.error("获取权限资源列表出错！", e);
            throw new Exception("获取权限资源列表出错！");
        }

        return resList;
    }

    /**
     * 删除角色
     * @return
     */
    public boolean delRes(Long resId) throws Exception {

        boolean result = false;
        try {
            AuthResource res = dao.load(AuthResource.class, resId);
            result = dao.delRes(res);

        } catch(Exception e) {
            logger.error("删除出错！", e);
            throw new Exception("删除出错！");
        }

        return result;
    }

    /**
     * 根据权限模块和权限代码查看该代码是否可用
     * @param module
     * @param arCode
     * @return  true表示可用，false表示不可用
     */
    public boolean checkArCode(Long module, Integer arCode) throws Exception {
        boolean result = false;
        try {
            result = dao.findByModuleArCode(module, arCode);
        } catch(Exception e) {
            logger.error("查询权限资源出错！", e);
            throw new Exception("查询权限资源出错！");
        }

        return result;
    }

    /**
     * 验证权限标示是否唯一
     * @param arPermission
     * @return  false表示可用，true表示不可用
     */
    public boolean checkUniqueName(String arPermission) throws Exception {
        boolean result = false;
        try {
            result = dao.findByUniqueName(arPermission);
        } catch(Exception e) {
            logger.error("查询权限资源出错！", e);
            throw new Exception("查询权限资源出错！");
        }

        return result;
    }

}
