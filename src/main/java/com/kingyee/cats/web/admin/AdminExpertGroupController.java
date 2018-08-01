package com.kingyee.cats.web.admin;

import com.google.gson.JsonElement;
import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.db.CatsExpertGroup;
import com.kingyee.cats.db.CatsExpertGroupDetail;
import com.kingyee.cats.service.admin.AdminExpertGroupService;
import com.kingyee.common.BaseController;
import com.kingyee.common.Const;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.fuxi.security.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 专家管理
 *
 * @author ph
 */
@Controller
@RequestMapping(value = "/admin/expert/")
public class AdminExpertGroupController extends BaseController {
    private final static Logger logger = LoggerFactory
            .getLogger(AdminExpertGroupController.class);

    @Autowired
    private AdminExpertGroupService service;
    @Autowired
    private RoleService roleService;

    /**
     * 专家组管理列表
     *
     * @param mm
     * @param groupName
     * @param state
     * @param page
     * @param rowsPerPage
     * @return
     */
    @RequestMapping("group/list")
    public String list(ModelMap mm, String groupName, Integer state, Integer page, Integer rowsPerPage) {
        if (page == null || page.intValue() == 0) {
            page = 1;
        }
        if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
            rowsPerPage = Const.ROWSPERPAGE;
        }
        POJOPageInfo pageInfo = new POJOPageInfo<CatsExpertGroup>(rowsPerPage, page);
        try {
            SearchBean searchBean = new SearchBean();
            if (StringUtils.isNotEmpty(groupName)) {
                searchBean.addParpField("groupName", "%" + groupName.trim() + "%");
            }
            searchBean.addParpField("state", state);
            pageInfo = service.list(searchBean, pageInfo);
        } catch (Exception e) {
            logger.error("获取专家列表信息出错。", e);
            return "error";
        }


        mm.addAttribute("pageInfo", pageInfo);
        mm.addAttribute("groupName", groupName);
        mm.addAttribute("page", page);
        mm.addAttribute("state", state);
        mm.addAttribute("rowsPerPage", rowsPerPage);
        return "admin/expert/groupList";
    }

    /**
     * 新增init
     *
     * @return
     */
    @RequestMapping("group/addInit")
    public String addUI(ModelMap mm) {
        return "admin/expert/groupAdd";
    }

    /**
     * 新增专家组
     *
     * @param expertGroup
     * @return
     */
    @RequestMapping("group/add")
    public String add(CatsExpertGroup expertGroup) {
        try {
            service.save(expertGroup);
        } catch (Exception e) {
            logger.error("保存专家组时出错。", e);
            return "error";
        }
        return "redirect:/admin/expert/group/list";
    }

    /**
     * 编辑init
     *
     * @param mm
     * @param id
     * @return
     */
    @RequestMapping("group/editInit")
    public String editUI(ModelMap mm, Long id) {
        try {
            CatsExpertGroup expertGroup = service.getById(id);
            mm.addAttribute("group", expertGroup);
        } catch (Exception e) {
            logger.error("获取专家组信息时出错。", e);
            return "admin/error";
        }
        return "admin/expert/groupEdit";
    }

    /**
     * 编辑保存
     *
     * @param expertGroup
     * @return
     */
    @RequestMapping("group/edit")
    public String edit(CatsExpertGroup expertGroup) {
        try {
            service.edit(expertGroup);
        } catch (Exception e) {
            logger.error("编辑专家组信息时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/expert/group/list";
    }


    /**
     * 注销
     *
     * @param id
     * @return
     */
    @RequestMapping("group/reset")
    public String reset(Long id) {
        try {
            CatsExpertGroup expertGroup = service.getById(id);
            if (expertGroup.getCegIsValid() == 0) {
                expertGroup.setCegIsValid(1);
            } else {
                expertGroup.setCegIsValid(0);
            }
            service.update(expertGroup);
        } catch (Exception e) {
            logger.error("注销/恢复专家组时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/expert/group/list";
    }


    /**
     * 专家列表
     *
     * @param mm
     * @param expertName
     * @param page
     * @param rowsPerPage
     * @return
     */
    @RequestMapping("{cegId}/list")
    public String expertList(ModelMap mm, @PathVariable("cegId") Long cegId, String expertName, Integer page, Integer rowsPerPage) {
        if (page == null || page.intValue() == 0) {
            page = 1;
        }
        if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
            rowsPerPage = Const.ROWSPERPAGE;
        }
        POJOPageInfo pageInfo = new POJOPageInfo<CatsExpertGroupDetail>(rowsPerPage, page);
        try {
            SearchBean searchBean = new SearchBean();
            if (StringUtils.isNotEmpty(expertName)) {
                searchBean.addParpField("expertName", "%" + expertName.trim() + "%");
            }
            searchBean.addParpField("cegId", cegId);
            pageInfo = service.expertList(searchBean, pageInfo);
        } catch (Exception e) {
            logger.error("获取专家列表信息出错。", e);
            return "error";
        }


        mm.addAttribute("pageInfo", pageInfo);
        mm.addAttribute("cegId", cegId);
        mm.addAttribute("expertName", expertName);
        mm.addAttribute("page", page);
        mm.addAttribute("rowsPerPage", rowsPerPage);
        return "admin/expert/expertList";
    }

    /**
     * 新增init
     *
     * @return
     */
    @RequestMapping("addInit/{cegId}")
    public String expertAddUI(ModelMap mm, @PathVariable("cegId") Long cegId) {
        mm.addAttribute("cegId", cegId);
        return "admin/expert/expertAdd";
    }

    /**
     * 新增专家组
     *
     * @param expertGroupDetail
     * @return
     */
    @RequestMapping("add")
    public String expertAdd(CatsExpertGroupDetail expertGroupDetail) {
        try {
            service.save(expertGroupDetail);
        } catch (Exception e) {
            logger.error("保存专家组时出错。", e);
            return "error";
        }
        return "redirect:/admin/expert/" + expertGroupDetail.getCegdCegId() + "/list";
    }

    /**
     * 编辑init
     *
     * @param mm
     * @param id
     * @return
     */
    @RequestMapping("editInit/{cegId}")
    public String expertEditUI(ModelMap mm, Long id, @PathVariable("cegId") Long cegId) {
        try {
            CatsExpertGroupDetail expertGroupDetail = service.getExpertById(id);
            mm.addAttribute("expert", expertGroupDetail);
            mm.addAttribute("cegId", cegId);
        } catch (Exception e) {
            logger.error("获取专家组信息时出错。", e);
            return "admin/error";
        }
        return "admin/expert/expertEdit";
    }

    /**
     * 编辑保存
     *
     * @param expertGroupDetail
     * @return
     */
    @RequestMapping("edit")
    public String expertEdit(CatsExpertGroupDetail expertGroupDetail) {
        try {
            service.edit(expertGroupDetail);
        } catch (Exception e) {
            logger.error("编辑专家组信息时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/expert/" + expertGroupDetail.getCegdCegId() + "/list";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("{id}/del")
    public String del(@PathVariable("id") Long id) {
        Long cegId = null;
        try {
            cegId = service.del(id);
        } catch (Exception e) {
            logger.error("删除专家时出错。", e);
            return "admin/error";
        }
        return "redirect:/admin/expert/" + cegId + "/list";
    }

    /**
     * 通过excel批量导入专家
     *
     * @return
     */
    @RequestMapping(value = {"{cegId}/importInit"})
    public String importInit(ModelMap mm, @PathVariable("cegId") Long cegId) throws IOException {
        CatsExpertGroup expertGroup = service.getById(cegId);
        mm.addAttribute("group", expertGroup);
        return "admin/expert/expertImport";
    }

    /**
     * 通过excel批量导入专家
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"{cegId}/import"})
    public JsonElement batchImport(MultipartFile file, @PathVariable("cegId") Long cegId) throws IOException {
        XSSFWorkbook xwb = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = xwb.getSheetAt(0);
        XSSFRow row;
        List<CatsExpertGroupDetail> expertList = new ArrayList<>();
        CatsExpertGroupDetail expert = null;
        for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);

            // 姓名作为必填字段的验证。如果没有填写，直接跳过
            if(StringUtils.isNotEmpty(getCellValue(row.getCell(1)))){
                expert = new CatsExpertGroupDetail();
                expert.setCegdCegId(cegId);
                String medliveId = getCellValue(row.getCell(0));
                if(StringUtils.isNotEmpty(medliveId)){
                    expert.setCegdMedliveId(Long.valueOf(medliveId));
                }
                expert.setCegdRealName(getCellValue(row.getCell(1)));
                expert.setCegdSex(getCellValue(row.getCell(2)));
                expert.setCegdHospital(getCellValue(row.getCell(3)));
                expert.setCegdDept(getCellValue(row.getCell(4)));
                expert.setCegdProfessional(getCellValue(row.getCell(5)));
                expert.setCegdTelNo(getCellValue(row.getCell(6)));
                expert.setCegdEmail(getCellValue(row.getCell(7)));
                expert.setCegdAlipayRealName(getCellValue(row.getCell(8)));
                expert.setCegdAlipayUserName(getCellValue(row.getCell(9)));
                expert.setCegdBankRealName(getCellValue(row.getCell(10)));
                expert.setCegdBankName(getCellValue(row.getCell(11)));
                expert.setCegdBankNo(getCellValue(row.getCell(12)));
                expert.setCegdCreateUserId(AdminUserUtil.getUserId());
                expert.setCegdCreateUserName(AdminUserUtil.getShowName());
                expert.setCegdCreateTime(System.currentTimeMillis());

                expertList.add(expert);
            }

        }

        // 保存
        return service.saveExpertBatch(expertList);
    }

    /**
     * 取得excel单元格的数据
     * @param cell
     * @return
     */
    private String getCellValue(XSSFCell cell){
        String value = "";
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            value = cell.getRawValue();
        }else if(cell.getCellType() == Cell.CELL_TYPE_STRING){
            value = cell.getStringCellValue();
        }

        if (value != null) {
            return value;
        }else{
            return "";
        }
    }

}
