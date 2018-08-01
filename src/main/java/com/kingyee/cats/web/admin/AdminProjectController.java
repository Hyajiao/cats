package com.kingyee.cats.web.admin;

import com.kingyee.cats.db.CatsProject;
import com.kingyee.cats.db.CatsProjectLiteratureConfig;
import com.kingyee.cats.db.CatsProjectNewsConfig;
import com.kingyee.cats.service.admin.AdminProjectService;
import com.kingyee.common.Const;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 项目管理
 *
 * @author ph
 *
 */
@Controller
@RequestMapping(value = "/admin/project/")
public class AdminProjectController {

    private final static Logger logger = LoggerFactory.getLogger(AdminSysUserController.class);

    @Autowired
    private AdminProjectService service;

    /**
     * 项目列表
     * @param projectName 项目名称
     * @param companyName 药企名称
     * @param startDate 开始时间
     * @param endDate 截止时间
     * @param page 页码
     * @param rowsPerPage 每页几条
     * @return
     */
    @RequestMapping("list")
    public String list(ModelMap mm, String projectName, String companyName, String startDate, String endDate, Integer state, Integer page, Integer rowsPerPage) {
        if (page == null || page.intValue() == 0) {
            page = 1;
        }
        if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
            rowsPerPage = Const.ROWSPERPAGE;
        }
        POJOPageInfo pageInfo = new POJOPageInfo<CatsProject>(rowsPerPage, page);
        try {
            SearchBean searchBean = new SearchBean();
            if (StringUtils.isNotEmpty(projectName)) {
                searchBean.addParpField("projectName", "%" + projectName.trim() + "%");
            }
            if (StringUtils.isNotEmpty(companyName)) {
                searchBean.addParpField("companyName", "%" + companyName.trim() + "%");
            }
            searchBean.addParpField("startDate", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE));
            searchBean.addParpField("endDate", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE));
            searchBean.addParpField("state", state);
            pageInfo = service.list(searchBean, pageInfo);
        } catch (Exception e) {
            logger.error("获取项目列表出错。", e);
            return "error";
        }

        mm.addAttribute("pageInfo", pageInfo);
        mm.addAttribute("projectName", projectName);
        mm.addAttribute("companyName", companyName);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("state", state);
        mm.addAttribute("page", page);
        mm.addAttribute("rowsPerPage", rowsPerPage);
        return "admin/project/projectList";
    }

    /**
     * 新增init
     *
     * @return
     */
    @RequestMapping("addInit")
    public String addUI(ModelMap mm) {
        return "admin/project/projectAdd";
    }

    /**
     * 新增项目
     *
     * @param project
     * @return
     */
    @RequestMapping("add")
    public String add(CatsProject project) {
        try {
            service.save(project);
        } catch (Exception e) {
            logger.error("保存项目时出错。", e);
            return "error";
        }
        return "redirect:list";
    }

    /**
     * 编辑init
     *
     * @param mm
     * @param id
     * @return
     */
    @RequestMapping("editInit")
    public String editUI(ModelMap mm, Long id) {
        try {
            CatsProject project = service.getById(id);
            mm.addAttribute("project", project);
        } catch (Exception e) {
            logger.error("获取项目信息时出错。", e);
            return "admin/error";
        }
        return "admin/project/projectEdit";
    }

    /**
     * 编辑保存
     *
     * @param project
     * @return
     */
    @RequestMapping("edit")
    public String edit(CatsProject project) {
        try {
            service.edit(project);
        } catch (Exception e) {
            logger.error("编辑项目信息时出错。", e);
            return "admin/error";
        }
        return "redirect:list";
    }


    /**
     * 注销
     *
     * @param id
     * @return
     */
    @RequestMapping("reset")
    public String reset(Long id) {
        try {
            CatsProject project = service.getById(id);
            if (project.getCpIsValid() == 0) {
                project.setCpIsValid(1);
            } else {
                project.setCpIsValid(0);
            }
            service.update(project);
        } catch (Exception e) {
            logger.error("注销/恢复项目时出错。", e);
            return "admin/error";
        }
        return "redirect:list";
    }

    /**
     * 资讯配置init
     *
     * @param id
     * @return
     */
    @RequestMapping("config/news/{id}")
    public String newsConfigInit(ModelMap mm, @PathVariable("id") Long id) {
        try {
            CatsProject project = service.getById(id);
            List<CatsProjectNewsConfig> list = service.listProjectNewsConfig(id);

            mm.addAttribute("project", project);
            mm.addAttribute("projectNewsConfigList", list);
        } catch (Exception e) {
            logger.error("资讯配置init时出错。", e);
            return "admin/error";
        }
        return "admin/project/projectNewsConfig";
    }

    /**
     * 资讯配置保存
     *
     * @param keyword
     * @return
     */
    @RequestMapping("config/news/{id}/save")
    public String newsConfigSave(ModelMap mm, String[] keyword, @PathVariable("id") Long id) {
        try {

            String keywords = "";
            for(int i = 0; i < keyword.length; i++){
                if (StringUtils.isNotEmpty(keyword[i])) {
                    keywords = keywords + keyword[i] + "+";
                }
            }

            if(keywords.length() > 0){
                keywords = keywords.substring(0, keywords.length() - 1);
            }

            CatsProjectNewsConfig newsConfig = new CatsProjectNewsConfig();
            newsConfig.setCpncCpId(id);
            newsConfig.setCpncKeywords(keywords);

            service.saveProjectNewsConfig(newsConfig);

        } catch (Exception e) {
            logger.error("资讯配置保存时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/project/config/news/" + id;
    }

    /**
     * 资讯配置删除
     *
     * @return
     */
    @RequestMapping("config/news/{id}/del")
    public String newsConfigDel(ModelMap mm, @PathVariable("id") Long cpncId) {
        Long cpId = null;
        try {
            cpId = service.delProjectNewsConfig(cpncId);
        } catch (Exception e) {
            logger.error("资讯配置删除时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/project/config/news/" + cpId;
    }

    /**
     * 文献配置init
     *
     * @param id
     * @return
     */
    @RequestMapping("config/literature/{id}")
    public String literatureConfigInit(ModelMap mm, @PathVariable("id") Long id) {
        try {
            CatsProject project = service.getById(id);
            List<CatsProjectLiteratureConfig> list = service.listProjectLiteratureConfig(id);

            mm.addAttribute("project", project);
            mm.addAttribute("projectLiteratureConfigList", list);
        } catch (Exception e) {
            logger.error("文献配置init时出错。", e);
            return "admin/error";
        }
        return "admin/project/projectLiteratureConfig";
    }

    /**
     * 文献配置保存
     *
     * @return
     */
    @RequestMapping("config/literature/{id}/save")
    public String literatureConfigSave(ModelMap mm, CatsProjectLiteratureConfig literatureConfig, @PathVariable("id") Long id) {
        try {
            service.saveProjectLiteratureConfig(literatureConfig);
        } catch (Exception e) {
            logger.error("文献配置保存时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/project/config/literature/" + id;
    }

    /**
     * 文献配置删除
     *
     * @return
     */
    @RequestMapping("config/literature/{id}/del")
    public String literatureConfigDel(ModelMap mm, @PathVariable("id") Long cplcId) {
        Long cpId = null;
        try {
            cpId = service.delProjectLiteratureConfig(cplcId);
        } catch (Exception e) {
            logger.error("文献配置删除时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/project/config/literature/" + cpId;
    }

}
