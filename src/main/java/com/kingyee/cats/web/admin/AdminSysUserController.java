package com.kingyee.cats.web.admin;

import com.google.gson.JsonElement;
import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.db.CatsProject;
import com.kingyee.cats.db.CmAdminUser;
import com.kingyee.cats.enums.SysUserTypeChEnum;
import com.kingyee.cats.service.admin.AdminProjectService;
import com.kingyee.cats.service.admin.AdminSysUserService;
import com.kingyee.common.BaseController;
import com.kingyee.common.Const;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.util.encrypt.EncryptUtil;
import com.kingyee.fuxi.security.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 *
 * @author fanyongqian
 * 2016年10月31日
 */
@Controller
@RequestMapping(value = "/admin/sysuser/")
public class AdminSysUserController extends BaseController {
    private final static Logger logger = LoggerFactory
            .getLogger(AdminSysUserController.class);

    @Autowired
    private AdminSysUserService sysUserService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminProjectService projectService;

    /**
     * 用户列表
     *
     * @param mm
     * @param keyword
     * @param page
     * @param rowsPerPage
     * @return
     */
    @RequestMapping("list")
    public String list(ModelMap mm, Long auRole, String keyword, Integer state, Integer page, Integer rowsPerPage) {
        if (page == null || page.intValue() == 0) {
            page = 1;
        }
        if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
            rowsPerPage = Const.ROWSPERPAGE;
        }
        POJOPageInfo pageInfo = new POJOPageInfo<CmAdminUser>(rowsPerPage, page);
        try {
            SearchBean searchBean = new SearchBean();
            searchBean.addParpField("roleId", auRole);
            if (StringUtils.isNotEmpty(keyword)) {
                searchBean.addParpField("keyword", "%" + keyword.trim() + "%");
            }
            searchBean.addParpField("state", state);
            pageInfo = sysUserService.list(searchBean, pageInfo);
        } catch (Exception e) {
            logger.error("获取用户列表信息出错。", e);
            return "error";
        }

        CmAdminUser adminUser = new CmAdminUser();
        adminUser.setAuRole(auRole);

        mm.addAttribute("pageInfo", pageInfo);
        mm.addAttribute("user", adminUser);
        mm.addAttribute("roleList", roleService.listRole());
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("page", page);
        mm.addAttribute("state", state);
        mm.addAttribute("rowsPerPage", rowsPerPage);
        return "admin/sysuser/sysUserList";
    }

    /**
     * 新增init
     *
     * @return
     */
    @RequestMapping("addInit")
    public String addUI(ModelMap mm) {
        mm.addAttribute("user", new CmAdminUser());
        mm.addAttribute("roleList", roleService.listRole());
        return "admin/sysuser/sysUserAdd";
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequestMapping("add")
    public String add(CmAdminUser user) {
        try {
            sysUserService.save(user);
        } catch (Exception e) {
            logger.error("保存用户时出错。", e);
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
            CmAdminUser user = sysUserService.getById(id);
            user.setAuPassword(null);
            mm.addAttribute("user", user);
            mm.addAttribute("roleList", roleService.listRole());
        } catch (Exception e) {
            logger.error("获取用户信息时出错。", e);
            return "admin/error";
        }
        return "admin/sysuser/sysUserEdit";
    }

    /**
     * 编辑保存
     *
     * @param user
     * @return
     */
    @RequestMapping("edit")
    public String edit(CmAdminUser user) {
        try {
            sysUserService.edit(user);
        } catch (Exception e) {
            logger.error("编辑用户信息时出错。", e);
            return "admin/error";
        }
        return "redirect:list";
    }

    /**
     * 查看用户名是否已经存在
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"exsitUser"}, method = RequestMethod.GET)
    public JsonElement checkName(String fieldValue, String fieldId) {
        Object[] array = new Object[2];
        try {
            Boolean flag = sysUserService.checkName(fieldValue);
            array[0] = fieldId;
            array[1] = !flag;
        } catch (Exception e) {
            String errMsg = "检查用户是否存在时出错。usCode=" + fieldValue;
            logger.error(errMsg, e);
            // 返回json格式必须是[字段ID,布尔类型,显示信息]这种格式
            Object[] array_ = new Object[]{fieldId, false, errMsg};
            return JsonWrapper.newJson(array_);
        }
        return JsonWrapper.newJson(array);
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
            CmAdminUser user = sysUserService.getById(id);
            if (user.getAuIsValid() == 0) {
                user.setAuIsValid(1);
            } else {
                user.setAuIsValid(0);
            }
            sysUserService.update(user);
        } catch (Exception e) {
            logger.error("注销/恢复用户时出错。", e);
            return "admin/error";
        }
        return "redirect:list";
    }


    /**
     * 通过excel批量导入用户
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"import"})
    public JsonElement batchImport(MultipartFile file) throws IOException {
        XSSFWorkbook xwb = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = xwb.getSheetAt(0);
        XSSFRow row;
        List<CmAdminUser> userList = new ArrayList<>();
        CmAdminUser user = null;
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            String name = row.getCell(0).toString();
            String tel = null;
            if(row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC){
                tel = row.getCell(1).getRawValue();
            }else if(row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING){
                tel = row.getCell(1).toString();
            }

            String email = row.getCell(2).toString();
            String zone = row.getCell(3).toString();
            String roles = row.getCell(4).toString();
            Long role = 1L;
            if(StringUtils.isNotEmpty(roles)){
				if(SysUserTypeChEnum.ENTERPRISE_USER.text().equals(roles)){
					role = 2L;
				}else if(SysUserTypeChEnum.FINANCE.text().equals(roles)){
					role = 3L;
				}
			}
            user = new CmAdminUser();
            // 姓名
            user.setAuShowName(name);
            // 电话
            user.setAuTel(tel);
            // 邮箱
            user.setAuEmail(email);
            // 大区
            user.setAuZone(zone);
            // 用户名
            user.setAuUserName(tel);
            // 密码
            String password = "";
            if(role == 2){
				//数字和字母并且为8位
				password = tel.substring(tel.length() - 7, tel.length())+"a";
			}else{
				password = tel.substring(tel.length() - 6, tel.length());
			}
            user.setAuPassword(EncryptUtil.getSHA256Value(password));
            // 角色
            user.setAuRole(role);
            user.setAuIsValid(1);
            user.setAuCreateDate(System.currentTimeMillis());
            user.setAuCreateUserId(AdminUserUtil.getUserId());
            user.setAuCreateUserName(AdminUserUtil.getShowName());
            userList.add(user);
        }

        // 保存
        return sysUserService.saveBatch(userList);
    }

    /**
     * 取得用户的关联的项目和没有关联的项目
     *
     * @param uid
     * @return
     */
    @RequestMapping("{uid}/project")
    public String project(ModelMap mm, @PathVariable Long uid) {
        try {
            List<CatsProject> unLinkedProjectList = projectService.getUnLinkedProjectByUserId(uid);
            List<CatsProject> linkedProjectList = projectService.getLinkedProjectByUserId(uid);

            mm.addAttribute("uid", uid);
            mm.addAttribute("unLinkedProjectList", unLinkedProjectList);
            mm.addAttribute("linkedProjectList", linkedProjectList);
        } catch (Exception e) {
            logger.error("取得用户的关联的项目和没有关联的项目时出错。", e);
            return "admin/error";
        }
        return "admin/sysuser/sysUserProject";
    }

    /**
     * 添加用户和项目的关联关系
     *
     * @param uid
     * @return
     */
    @RequestMapping("{uid}/project/add/{projectId}")
    public String projectAdd(ModelMap mm, @PathVariable Long uid, @PathVariable Long projectId) {
        try {
            projectService.addProject2User(uid, projectId);
        } catch (Exception e) {
            logger.error("给用户关联项目时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/sysuser/" + uid + "/project";
    }

    /**
     * 删除用户和项目的关联关系
     *
     * @param uid
     * @return
     */
    @RequestMapping("{uid}/project/delete/{projectId}")
    public String projectDelete(ModelMap mm, @PathVariable Long uid, @PathVariable Long projectId) {
        try {
            projectService.deleteProjectFromUser(uid, projectId);
        } catch (Exception e) {
            logger.error("删除用户关联项目时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/sysuser/" + uid + "/project";
    }

}
