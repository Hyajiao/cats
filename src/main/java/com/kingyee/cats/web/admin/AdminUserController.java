package com.kingyee.cats.web.admin;

import com.google.gson.JsonElement;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.cats.enums.YesNoEnum;
import com.kingyee.cats.service.admin.AdminUserService;
import com.kingyee.common.BaseController;
import com.kingyee.common.Const;
import com.kingyee.common.export.AbstractXlsTemplate;
import com.kingyee.common.export.BaseXlsTemplate;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * 前台用户管理
 * 
 * @author peihong
 * 2017年08月17日
 */
@Controller
@RequestMapping(value = "/admin/user")
public class AdminUserController extends BaseController {

    @Autowired
    private AdminUserService service;

    /**
     * 微信用户列表
     * @param mm
     * @param nickName
     * @param page
     * @param rowsPerPage
     * @return
     */
    @RequestMapping("wechat/list")
    public String wechatUserList(ModelMap mm, String nickName, Integer subscribe, Integer page, Integer rowsPerPage){
        if (page == null || page.intValue() == 0) {
            page = 1;
        }
        if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
            rowsPerPage = Const.ROWSPERPAGE;
        }
        POJOPageInfo pageInfo = new POJOPageInfo<CmWechatUser>(rowsPerPage , page);
        try {
            SearchBean searchBean = new SearchBean();
            if (StringUtils.isNotEmpty(nickName)) {
                searchBean.addParpField("nickName", "%" + nickName.trim() + "%");
            }
            searchBean.addParpField("subscribe", subscribe);
            pageInfo = service.wechatUserList(searchBean, pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户列表信息出错。", e );
            return "error";
        }
        mm.addAttribute("pageInfo",pageInfo);
        mm.addAttribute("nickName",nickName);
        mm.addAttribute("page",page);
        mm.addAttribute("subscribe", subscribe);
        mm.addAttribute("rowsPerPage",rowsPerPage);
        return "admin/user/wechatUserList";
    }


    /**
     * 注册用户列表
     * @param mm
     * @param page
     * @param rowsPerPage
     * @param realName 用户名
     * @param userType 用户类型
     * @param cuMedliveId 医脉通ID
     * @param userType 绑定的医生的id
     * @return
     */
    @RequestMapping("reg/list")
    public String regUserList(ModelMap mm, Integer page, Integer rowsPerPage, String realName, String userType, Long cuMedliveId,
                              Integer cuIsAuthentication, Integer isFinish, String startDate, String endDate){
        if (page == null || page.intValue() == 0) {
            page = 1;
        }
        if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
            rowsPerPage = Const.ROWSPERPAGE;
        }
        POJOPageInfo pageInfo = new POJOPageInfo<CmUser>(rowsPerPage , page);
        try {
            SearchBean searchBean = new SearchBean();
            if (StringUtils.isNotEmpty(realName)) {
                searchBean.addParpField("realName", "%" + realName.trim() + "%");
            }
            if(isFinish == null){
                isFinish = YesNoEnum.YES.ordinal();
            }

            searchBean.addParpField("userType", userType);
            searchBean.addParpField("cuMedliveId", cuMedliveId);
            searchBean.addParpField("cuIsAuthentication", cuIsAuthentication);
            searchBean.addParpField("isFinish", isFinish);
            searchBean.addParpField("startDate", startDate);
            searchBean.addParpField("endDate", endDate);

            pageInfo = service.regUserList(searchBean, pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户列表信息出错。", e );
            return "error";
        }
        mm.addAttribute("pageInfo",pageInfo);
        mm.addAttribute("page",page);
        mm.addAttribute("rowsPerPage",rowsPerPage);
        mm.addAttribute("realName",realName);
        mm.addAttribute("userType", userType);
        mm.addAttribute("cuMedliveId", cuMedliveId);
        mm.addAttribute("cuIsAuthentication", cuIsAuthentication);
        mm.addAttribute("isFinish", isFinish);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);

        return "admin/user/regUserList";
    }

    /**
     * 注册用户明细
     * @param cuId 用户表主键
     * @return
     */
    @RequestMapping("reg/{id}")
    public String regUser(ModelMap mm, @PathVariable("id") Long cuId){

        CmUser user = service.getRegUserById(cuId);
        mm.addAttribute("user", user);
        return "admin/user/regUser";
    }

    /**
     * 注册用户认证
     * @param cuId 用户表主键
     * @return
     */
    @RequestMapping("reg/{id}/auth")
    public String regUserAuth(ModelMap mm, @PathVariable("id") Long cuId, Integer cuIsAuthentication){
        service.updateRegUserAuth(cuId, cuIsAuthentication);
        return "redirect:/admin/user/reg/list";
    }

    /**
     * 导出注册用户
     * @param startDate
     * @param endDate
     * @param response
     */
    @RequestMapping(value = "exportRegList")
    public void exportRegList(String startDate, String endDate, String realName, String userType, Long cuId, Long bindDoctorId, HttpServletResponse response){

        SearchBean searchBean = new SearchBean();
        if (StringUtils.isNotEmpty(realName)) {
            searchBean.addParpField("realName", "%" + realName.trim() + "%");
        }
//        searchBean.addParpField("userType", userType);
        searchBean.addParpField("cuId", cuId);
//        searchBean.addParpField("bindDoctorId", bindDoctorId);
        searchBean.addParpField("startDate", startDate);
        searchBean.addParpField("endDate", endDate);


        try{
            String filename = "注册绑定用户-"+ TimeUtil.longToString(System.currentTimeMillis(), TimeUtil.FORMAT_DATE);
            filename = URLEncoder.encode(filename, "utf-8") + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename="+filename);
            ServletOutputStream out = response.getOutputStream();
            HSSFWorkbook book = null;
            POJOPageInfo pageInfo = service.regUserList(searchBean, null);
            List<CmUser> list = pageInfo.getItems();

            String[] titleCns = {"ID", "医脉通ID", "真实姓名", "省市","市", "医院", "科室", "职称", "手机号"};
            String[] titleEns = {"cuId", "cuMedliveId", "cuRealName", "cuProvince","cuCity","cuHospital", "cuDept", "cuProfessional", "cuTelNo"};
            AbstractXlsTemplate xls = new BaseXlsTemplate();
            book = xls.createExcelBook("注册绑定用户", titleCns, titleEns, list);

            try{
                if(null != book){
                    book.write(out);
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error("导出注册用户时出错。", e);
            }finally{
                out.close();
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("导出注册用户时出错。", e);
        }

    }

    /**
     * 取得注册用户省份对应的城市
     * @param province 省份
     * @return
     */
    @RequestMapping("reg/city")
    @ResponseBody
    public JsonElement city(ModelMap mm, String province){
        List<String> list = service.getUserCity(province);
        return JsonWrapper.newDataInstance(list);
    }

    /**
     * 取得注册用户省份,城市,对应的医院
     * @param province 省份
     * @return
     */
    @RequestMapping("reg/hospital")
    @ResponseBody
    public JsonElement hospital(ModelMap mm, String province, String city){
        List<String> list = service.getUserHospital(province, city);
        return JsonWrapper.newDataInstance(list);
    }

    /**
     * 取得注册用户省份，城市，医院，对应的用户
     * @param province 省份
     * @return
     */
    @RequestMapping("reg/user")
    @ResponseBody
    public JsonElement user(ModelMap mm, String province, String city, String hospital){
        List<CmUser> list = service.getUser(province, city, hospital);
        return JsonWrapper.newDataInstance(list);
    }

}
