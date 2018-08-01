package com.kingyee.cats.web.admin;

import com.google.gson.JsonElement;
import com.kingyee.cats.db.CatsExpertGroup;
import com.kingyee.cats.db.CatsProject;
import com.kingyee.cats.db.CatsSurveyNotice;
import com.kingyee.cats.db.CatsSurveyProject;
import com.kingyee.cats.enums.UploadFileTypeEnum;
import com.kingyee.cats.export.SurveyFinishRecordXlsTemplate;
import com.kingyee.cats.service.admin.AdminEntService;
import com.kingyee.cats.service.admin.AdminExpertGroupService;
import com.kingyee.cats.service.admin.AdminProjectService;
import com.kingyee.cats.service.admin.AdminSurveyService;
import com.kingyee.common.Const;
import com.kingyee.common.export.AbstractXlsTemplate;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.spring.mvc.WebUtil;
import com.kingyee.common.util.PropertyConst;
import com.kingyee.common.util.TimeUtil;
import com.kingyee.common.util.io.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 调研管理
 *
 * @author ph
 *
 */
@Controller
@RequestMapping(value = "/admin/survey/")
public class AdminSurveyController {

    private final static Logger logger = LoggerFactory.getLogger(AdminSysUserController.class);

    @Autowired
    protected AdminSurveyService service;
    @Autowired
    protected AdminExpertGroupService expertGroupService;
    @Autowired
    protected AdminProjectService projectService;
	@Autowired
	private AdminEntService entService;

    /**
     * 调研列表
     * @param surveyName 调研名称
     * @param projectName 项目名称
     * @param startDate 开始时间
     * @param endDate 截止时间
     * @param page 页码
     * @param rowsPerPage 每页几条
     * @return
     */
    @RequestMapping("list")
    public String list(ModelMap mm, String surveyName, String projectName, String startDate, String endDate, Integer state, Integer page, Integer rowsPerPage) {
        if (page == null || page.intValue() == 0) {
            page = 1;
        }
        if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
            rowsPerPage = Const.ROWSPERPAGE;
        }
        POJOPageInfo pageInfo = new POJOPageInfo<CatsSurveyProject>(rowsPerPage, page);
        try {
            SearchBean searchBean = new SearchBean();
            if (StringUtils.isNotEmpty(surveyName)) {
                searchBean.addParpField("surveyName", "%" + surveyName.trim() + "%");
            }
            if (StringUtils.isNotEmpty(projectName)) {
                searchBean.addParpField("projectName", "%" + projectName.trim() + "%");
            }
            searchBean.addParpField("startDate", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE));
            searchBean.addParpField("endDate", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE));
            searchBean.addParpField("state", state);
            pageInfo = service.list(searchBean, pageInfo);
        } catch (Exception e) {
            logger.error("获取调研列表出错。", e);
            return "error";
        }

        mm.addAttribute("pageInfo", pageInfo);
        mm.addAttribute("surveyName", surveyName);
        mm.addAttribute("projectName", projectName);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("state", state);
        mm.addAttribute("page", page);
        mm.addAttribute("rowsPerPage", rowsPerPage);
        return "admin/survey/surveyList";
    }

    /**
     * 新增init
     *
     * @return
     */
    @RequestMapping("addInit")
    public String addUI(ModelMap mm) {

        List<CatsExpertGroup> expertGroupList = expertGroupService.getAllExpertGroupList();
        List<CatsProject> projectList = projectService.getAvailableProjectList();


        mm.addAttribute("survey", new CatsSurveyProject());
        mm.addAttribute("expertGroupList", expertGroupList);
        mm.addAttribute("projectList", projectList);

        return "admin/survey/surveyAdd";
    }

    /**
     * 新增调研
     *
     * @param survey
     * @return
     */
    @RequestMapping("add")
    public String add(CatsSurveyProject survey) {
        try {
            service.save(survey);
        } catch (Exception e) {
            logger.error("保存调研时出错。", e);
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
            CatsSurveyProject survey = service.getById(id);
            if(StringUtils.isNotEmpty(survey.getCspBrief())){
                String a = survey.getCspBrief().replace("\"", "\'");
                survey.setCspBrief(a);
            }


            List<CatsExpertGroup> expertGroupList = expertGroupService.getAllExpertGroupList();
            List<CatsProject> projectList = projectService.getAvailableProjectList();

            mm.addAttribute("survey", survey);
            mm.addAttribute("expertGroupList", expertGroupList);
            mm.addAttribute("projectList", projectList);
        } catch (Exception e) {
            logger.error("获取调研信息时出错。", e);
            return "admin/error";
        }
        return "admin/survey/surveyEdit";
    }

    /**
     * 编辑保存
     *
     * @param survey
     * @return
     */
    @RequestMapping("edit")
    public String edit(CatsSurveyProject survey) {
        try {
            service.edit(survey);
        } catch (Exception e) {
            logger.error("编辑调研信息时出错。", e);
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
            CatsSurveyProject survey = service.getById(id);
            if (survey.getCspIsValid() == 0) {
                survey.setCspIsValid(1);
            } else {
                survey.setCspIsValid(0);
            }
            service.update(survey);
        } catch (Exception e) {
            logger.error("注销/恢复调研时出错。", e);
            return "admin/error";
        }
        return "redirect:list";
    }

    /**
     * 上传调研报告
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "upload" }, method = RequestMethod.POST)
    public JsonElement uploadFile(MultipartFile file, Long surveyId){
        String folderPath = PropertyConst.UPLOAD_PATH + UploadFileTypeEnum.SURVEY_REPORT.text() + "/";
        String path = WebUtil.getRealPath(folderPath);
        String fileName = file.getOriginalFilename();
        //重命名
        String ext = FileUtil.getSuffix(fileName);
        fileName = System.currentTimeMillis() + "." + ext;

        //保存
        try {
            File targetFile = new File(path, fileName);
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);

            CatsSurveyProject survey = service.getById(surveyId);
            survey.setCspSurveyReportPath(folderPath + fileName);
            service.update(survey);

        } catch (Exception e) {
            logger.error("上传文件时出错。", e );
            return JsonWrapper.newErrorInstance("上传文件时出错。");
        }
        return JsonWrapper.newDataInstance(folderPath + fileName);
    }


	/**
	 * 下载报告
	 * @return
	 */
	@RequestMapping("report/download/{id}")
	public ResponseEntity<byte[]> download(HttpServletRequest request, @PathVariable("id") Long id) throws IOException {
		CatsSurveyProject surveyProject = entService.getSurvyProjectBy(id);
		String filePpath=surveyProject.getCspSurveyReportPath();
		Integer last = filePpath.lastIndexOf("/");
		String fileName = filePpath.substring(last+1);

		String folderPath = PropertyConst.UPLOAD_PATH + UploadFileTypeEnum.SURVEY_REPORT.text() + "/";
		String path = WebUtil.getRealPath(folderPath);

		//下载文件路径
		File file = new File(path+fileName);

		HttpHeaders headers = new HttpHeaders();
		if(file.exists()){
			//		String fileName=new String("你好.xlsx".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
					headers, HttpStatus.CREATED);
		}
		logger.error("下载文件时出错，文件不存在。调研报告ID="+id);
		return new ResponseEntity<byte[]>(null,
				headers, HttpStatus.NOT_FOUND);
	}


    /**
     * 某调研的推送情况
     *
     * @param mm
     * @param id
     * @return
     */
    @RequestMapping("{surveyId}/notice")
    public String sendInit(ModelMap mm, @PathVariable("surveyId") Long id) {
        try {
            CatsSurveyProject survey = service.getById(id);
            List<CatsSurveyNotice> surveyNoticeList = service.getSurveyNoticeList(id);
            CatsExpertGroup expertGroup = expertGroupService.getById(survey.getCspCegId());
            CatsProject project = projectService.getById(survey.getCspCpId());

            mm.addAttribute("survey", survey);
            mm.addAttribute("surveyNoticeList", surveyNoticeList);
            mm.addAttribute("expertGroup", expertGroup);
            mm.addAttribute("project", project);
        } catch (Exception e) {
            logger.error("获取调研推送信息时出错。", e);
            return "admin/error";
        }
        return "admin/survey/surveyNoticeSend";
    }

    /**
     * 保存调研推送
     *
     * @param id 调研id
     * @param type 发送类型  0:微信；1:短信
     * @param scope 发送范围  0：全部；1：仅未发送；
     * @return
     */
    @RequestMapping("{surveyId}/notice/send")
    @ResponseBody
    public JsonElement send(@PathVariable("surveyId") Long id, Integer type, Integer scope) {
        try {
            CatsSurveyNotice surveyNotice = new CatsSurveyNotice();
            surveyNotice.setCsnCspId(id);
            surveyNotice.setCsnSendType(type);
            return service.saveSurveyNotice(surveyNotice, scope);
        } catch (Exception e) {
            logger.error("获取调研推送信息时出错。", e);
            return JsonWrapper.newErrorInstance("有错误发生，请稍后再试");
        }
    }

    /**
     * 保存调研推送
     *
     * @param surveyId 调研id
     * @param sendId 发送id
     * @return
     */
    @RequestMapping("{surveyId}/notice/{sendId}/list")
    public String sendList(ModelMap mm, @PathVariable("surveyId") Long surveyId, @PathVariable("sendId") Long sendId, Integer page, Integer rowsPerPage) {
        if (page == null || page.intValue() == 0) {
            page = 1;
        }
        if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
            rowsPerPage = Const.ROWSPERPAGE;
        }
        POJOPageInfo pageInfo = new POJOPageInfo<Object[]>(rowsPerPage, page);
        CatsSurveyProject survey = service.getById(surveyId);
        try {
            pageInfo = service.getSurveyNoticeRecordList(sendId, pageInfo);
        } catch (Exception e) {
            logger.error("获取调研推送信息时出错。", e);
            return "error";
        }

        mm.addAttribute("pageInfo", pageInfo);
        mm.addAttribute("survey", survey);
        mm.addAttribute("surveyId", surveyId);
        mm.addAttribute("sendId", sendId);
        mm.addAttribute("page", page);
        mm.addAttribute("rowsPerPage", rowsPerPage);
        return "admin/survey/surveyNoticeRecordList";
    }


    /**
     * 调研完成医生列表
     * @param startDate 开始时间
     * @param endDate 截止时间
     * @param page 页码
     * @param rowsPerPage 每页几条
     * @return
     */
    @RequestMapping("{surveyId}/finish")
    public String finishRecord(ModelMap mm, @PathVariable("surveyId") Long surveyId, Integer csfrHadIssueReward, Integer csfrIsFirstAnswer, String startDate, String endDate, Integer page, Integer rowsPerPage) {
        if (page == null || page.intValue() == 0) {
            page = 1;
        }
        if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
            rowsPerPage = Const.ROWSPERPAGE;
        }
        POJOPageInfo pageInfo = new POJOPageInfo<Object[]>(rowsPerPage, page);
        CatsSurveyProject survey = service.getById(surveyId);
        try {
            SearchBean searchBean = new SearchBean();
            searchBean.addParpField("surveyId", surveyId);
            if (csfrHadIssueReward != null) {
                searchBean.addParpField("csfrHadIssueReward", csfrHadIssueReward);
            }
            if (csfrIsFirstAnswer != null) {
                searchBean.addParpField("csfrIsFirstAnswer", csfrIsFirstAnswer);
            }else{
                searchBean.addParpField("csfrIsFirstAnswer", 1);
            }
            searchBean.addParpField("startDate", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE));
            searchBean.addParpField("endDate", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE));
            pageInfo = service.surveyFinishList(searchBean, pageInfo);
        } catch (Exception e) {
            logger.error("获取调研列表出错。", e);
            return "error";
        }

        mm.addAttribute("survey", survey);
        mm.addAttribute("pageInfo", pageInfo);
        mm.addAttribute("surveyId", surveyId);
        mm.addAttribute("csfrHadIssueReward", csfrHadIssueReward);
        mm.addAttribute("csfrIsFirstAnswer", csfrIsFirstAnswer);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("page", page);
        mm.addAttribute("rowsPerPage", rowsPerPage);
        return "admin/survey/surveyFinishList";
    }


    /**
     * 单个发放奖励
     * @param surveyId 调研id
     * @param id id
     * @return
     */
    @RequestMapping("{surveyId}/issueReward")
    @ResponseBody
    public JsonElement finishRecord(ModelMap mm, @PathVariable("surveyId") Long surveyId, Long id) throws UnsupportedEncodingException {
        service.saveIssueReward(surveyId, id);
        return JsonWrapper.newSuccessInstance();
    }

    /**
     * 批量发放奖励
     * @param surveyId 调研id
     * @param ids ids
     * @return
     */
    @RequestMapping("{surveyId}/issueReward/batch")
    @ResponseBody
    public JsonElement finishRecord(ModelMap mm, @PathVariable("surveyId") Long surveyId, String ids) throws UnsupportedEncodingException {
        String[] idArray = ids.split(",");
        service.saveIssueRewardBatch(surveyId, idArray);
        return JsonWrapper.newSuccessInstance();
    }

    /**
     * 给所有未发放奖励金的，发放奖励金
     * @param surveyId 调研id
     * @return
     */
    @RequestMapping("{surveyId}/issueReward/all")
    @ResponseBody
    public JsonElement finishRecord(ModelMap mm, @PathVariable("surveyId") Long surveyId) throws UnsupportedEncodingException {
        service.saveIssueRewardAll(surveyId);
        return JsonWrapper.newSuccessInstance();
    }

    /**
     * 导出完成调研的列表
     * @param startDate
     * @param endDate
     * @param response
     */
    @RequestMapping(value = "{surveyId}/finish/export")
    public void exportRegList(ModelMap mm, @PathVariable("surveyId") Long surveyId, Integer csfrHadIssueReward, Integer csfrIsFirstAnswer, String startDate, String endDate, HttpServletResponse response){

        ServletOutputStream out = null;
        try {
            CatsSurveyProject survey = service.getById(surveyId);
            SearchBean searchBean = new SearchBean();
            searchBean.addParpField("surveyId", surveyId);
            if (csfrHadIssueReward != null) {
                searchBean.addParpField("csfrHadIssueReward", csfrHadIssueReward);
            }
            if (csfrIsFirstAnswer != null) {
                searchBean.addParpField("csfrIsFirstAnswer", csfrIsFirstAnswer);
            }else{
                searchBean.addParpField("csfrIsFirstAnswer", 1);
            }
            searchBean.addParpField("startDate", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE));
            searchBean.addParpField("endDate", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE));
            POJOPageInfo pageInfo = service.surveyFinishList(searchBean, null);
            List<Object[]> list = pageInfo.getItems();

            String filename = survey.getCspTitle() + "-调研完成列表-" + TimeUtil.longToString(System.currentTimeMillis(), TimeUtil.FORMAT_DATE);
            filename = URLEncoder.encode(filename, "utf-8") + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename="+filename);
            out = response.getOutputStream();
            HSSFWorkbook book = null;

            String[] titleCns = {"ID", "姓名", "医院", "科室", "职称", "完成时间", "奖励"};
            AbstractXlsTemplate xls = new SurveyFinishRecordXlsTemplate();
            book = xls.createExcelBook("调研完成列表", titleCns, list);

            try{
                if(null != book){
                    book.write(out);
                }
            }catch(Exception e){
                logger.error("导出完成调研的列表时出错。", e);
            }finally{
                out.close();
            }

        } catch (Exception e) {
            logger.error("导出完成调研的列表时出错。", e);
        }
    }

}
