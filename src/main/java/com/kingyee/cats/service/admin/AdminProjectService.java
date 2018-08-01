package com.kingyee.cats.service.admin;

import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.dao.admin.AdminProjectDao;
import com.kingyee.cats.db.CatsProject;
import com.kingyee.cats.db.CatsProjectEnterpriseUserLink;
import com.kingyee.cats.db.CatsProjectLiteratureConfig;
import com.kingyee.cats.db.CatsProjectNewsConfig;
import com.kingyee.common.IBaseService;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目管理
 * 
 */
@Service
public class AdminProjectService implements IBaseService {

    private final static Logger logger = LoggerFactory.getLogger(AdminProjectService.class);
	
    @Autowired
    private AdminProjectDao dao;


    /**
     * 项目列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CatsProject> list(SearchBean searchBean, POJOPageInfo<CatsProject> pageInfo){
        return dao.list(searchBean, pageInfo);
    }

    /**
     * 取得可用项目列表
     * @return
     */
    public List<CatsProject> getAvailableProjectList(){
        return dao.getAvailableProjectList();
    }

    /**
     * 保存项目
     * @param project
     * @return
     */
    public Long save(CatsProject project) {
        project.setCpCreateUserId(AdminUserUtil.getUserId());
        project.setCpCreateUserName(AdminUserUtil.getShowName());
        project.setCpCreateTime(System.currentTimeMillis());
        project.setCpIsValid(1);
        return dao.save(project);
    }

    /**
     * 根据id获取项目信息
     * @param id
     * @return
     */
    public CatsProject getById(Long id){
        return dao.get(CatsProject.class, id);
    }

    /**
     * 更新项目信息
     * @param project
     */
    public void edit(CatsProject project) throws UnsupportedEncodingException {
        CatsProject db = getById(project.getCpId());

        db.setCpProjectName(project.getCpProjectName());
        db.setCpCompanyName(project.getCpCompanyName());
        db.setCpProjectMemo(project.getCpProjectMemo());
        db.setCpStartTime(project.getCpStartTime());
        db.setCpEndTime(project.getCpEndTime());
        db.setCpUpdateUserId(AdminUserUtil.getUserId());
        db.setCpUpdateUserName(AdminUserUtil.getShowName());
        db.setCpUpdateTime(System.currentTimeMillis());
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
     * 项目资讯的配置列表
     * @return
     */
    public List<CatsProjectNewsConfig> listProjectNewsConfig(Long cpId){
        return dao.listProjectNewsConfig(cpId);
    }

    /**
     * 保存项目的资讯配置
     * @param newsConfig
     * @return
     */
    public Long saveProjectNewsConfig(CatsProjectNewsConfig newsConfig) {
        newsConfig.setCpncCreateUserId(AdminUserUtil.getUserId());
        newsConfig.setCpncCreateUserName(AdminUserUtil.getShowName());
        newsConfig.setCpncCreateTime(System.currentTimeMillis());
        return dao.save(newsConfig);
    }

    /**
     * 保存项目的资讯配置
     * @param cpncId 配置id
     * @return
     */
    public Long delProjectNewsConfig(Long cpncId) {
        CatsProjectNewsConfig newsConfig = dao.get(CatsProjectNewsConfig.class, cpncId);
        dao.del(newsConfig);
        return newsConfig.getCpncCpId();
    }

    /**
     * 项目资讯的配置列表
     * @return
     */
    public List<CatsProjectLiteratureConfig> listProjectLiteratureConfig(Long cpId){
        return dao.listProjectLiteratureConfig(cpId);
    }

    /**
     * 保存项目的资讯配置
     * @param literatureConfig
     * @return
     */
    public Long saveProjectLiteratureConfig(CatsProjectLiteratureConfig literatureConfig) {
        literatureConfig.setCplcCreateUserId(AdminUserUtil.getUserId());
        literatureConfig.setCplcCreateUserName(AdminUserUtil.getShowName());
        literatureConfig.setCplcCreateTime(System.currentTimeMillis());
        return dao.save(literatureConfig);
    }

    /**
     * 保存项目的资讯配置
     * @param cplcId 文献配置id
     * @return
     */
    public Long delProjectLiteratureConfig(Long cplcId) {
        CatsProjectLiteratureConfig literatureConfig = dao.get(CatsProjectLiteratureConfig.class, cplcId);
        dao.del(literatureConfig);
        return literatureConfig.getCplcCpId();
    }

    /**
     * 查看用户的没有关联的项目
     * @param uid 用户id
     * @return
     */
    public List<CatsProject> getUnLinkedProjectByUserId(Long uid) {
        return dao.getUnLinkedProjectByUserId(uid);
    }

    /**
     * 查看用户的关联的项目
     * @param uid 用户id
     * @return
     */
    public List<CatsProject> getLinkedProjectByUserId(Long uid) {
        return dao.getLinkedProjectByUserId(uid);
    }

    /**
     * 添加用户和项目的关联关系
     * @param uid 用户id
     * @param projectId 用户id
     * @return
     */
    public void addProject2User(Long uid, Long projectId) {
        CatsProjectEnterpriseUserLink peul = dao.getLinkedProject(uid, projectId);
        if(peul == null){
            peul = new CatsProjectEnterpriseUserLink();
            peul.setCpeulCpId(projectId);
            peul.setCpeulCauId(uid);
            peul.setCpeulCreateTime(System.currentTimeMillis());
            peul.setCpeulCreateUserId(AdminUserUtil.getUserId());
            peul.setCpeulCreateUserName(AdminUserUtil.getShowName());
            dao.save(peul);
        }
    }

    /**
     * 删除用户和项目的关联关系
     * @param uid 用户id
     * @return
     */
    public void deleteProjectFromUser(Long uid, Long projectId) {
        CatsProjectEnterpriseUserLink peul = dao.getLinkedProject(uid, projectId);
        if(peul != null){
            dao.del(peul);
        }
    }
}
