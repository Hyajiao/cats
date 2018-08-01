package com.kingyee.cats.web.admin;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.kingyee.cats.bean.NewsModel;
import com.kingyee.cats.common.security.AdminUserModel;
import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.db.*;
import com.kingyee.cats.enums.UploadFileTypeEnum;
import com.kingyee.cats.service.admin.AdminEntService;
import com.kingyee.cats.service.admin.AdminProjectService;
import com.kingyee.common.BaseController;
import com.kingyee.common.Const;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.spring.mvc.WebUtil;
import com.kingyee.common.util.CommonApiUtil;
import com.kingyee.common.util.PropertyConst;
import com.kingyee.common.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletRequest;

/**
 * 药企管理
 *
 * @author nmy
 * 2018年05月16日
 */
@Controller
@RequestMapping(value = "/admin/ent")
public class AdminEntController extends BaseController {

    @Autowired
    private AdminEntService service;
	@Autowired
	private AdminProjectService projectService;

	/**
	 * 获取用户对应的项目列表
	 * @return
	 */
	@RequestMapping(value = {"index", ""})
	public String index(ModelMap mm,Integer page, Integer rowsPerPage){
		if (page == null || page.intValue() == 0) {
			page = 1;
		}
		if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
			rowsPerPage = Const.ROWSPERPAGE;
		}
		POJOPageInfo pageInfo = new POJOPageInfo<CatsProjectEnterpriseUserLink>(rowsPerPage , page);
		try {
			AdminUserModel user = AdminUserUtil.getLoginUser();
			SearchBean searchBean = new SearchBean();
			searchBean.addParpField("userId", user.getId());
			searchBean.addParpField("useTime", TimeUtil.getNowDate());
			pageInfo = service.getUserProjectList(searchBean, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取用户所有的项目列表信息出错。", e );
			return "error";
		}
		mm.addAttribute("page",page);
		mm.addAttribute("rowsPerPage",rowsPerPage);
		mm.addAttribute("pageInfo", pageInfo);
		if(pageInfo.getItems() != null && pageInfo.getItems().size() == 1){
			Object[] obj = (Object[]) pageInfo.getItems().get(0);
			CatsProject project = (CatsProject)obj[1];
			return "redirect:abstractInfo/"+project.getCpId();
		}else{
			return "admin/ent/projectList";
		}
	}

	/**
	 * ajax-获取项目列表
	 * @return
	 */
	@RequestMapping("getProjectList")
	@ResponseBody
	public JsonElement getProjectList(){
		String errMsg = "";
		POJOPageInfo pageInfo = new POJOPageInfo<CatsProjectEnterpriseUserLink>(0 , 1);
		try {
			AdminUserModel user = AdminUserUtil.getLoginUser();
			SearchBean searchBean = new SearchBean();
			searchBean.addParpField("userId", user.getId());
			searchBean.addParpField("useTime",TimeUtil.getNowDate());
			pageInfo = service.getUserProjectList(searchBean,pageInfo);
			return JsonWrapper.newDataInstance(pageInfo.getItems());
		} catch (Exception e) {
			errMsg = "ajax--获取项目列表时出错。";
			e.printStackTrace();
			logger.error(errMsg, e);
			return JsonWrapper.newErrorInstance(errMsg);
		}
	}

	/**
	 * 产品概述
	 * @param id 项目表主键
	 * @return
	 */
	@RequestMapping("abstractInfo/{id}")
	public String abstractInfo(ModelMap mm, @PathVariable("id") Long id){
		try {
			//调研报告完成情况
			List<CatsSurveyProject> list = service.getSurveyFinishProjectList(id);
			if(list != null && list.size()>0){
				Integer finishNum = 0;
				Integer unFinishNum = 0;
				for(CatsSurveyProject bean :list){
					if(StringUtils.isNotEmpty(bean.getCspSurveyReportPath())){
						finishNum ++;
					}else{
						unFinishNum++;
					}
				}
				mm.addAttribute("finishNum",finishNum);
				mm.addAttribute("unFinishNum",unFinishNum);
			}else{
				mm.addAttribute("finishNum",0);
				mm.addAttribute("unFinishNum", 0);
			}
			CatsProject project = service.getProjectInfoById(id);
			mm.addAttribute("project",project);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("产品概述初始化出错", e);
			return "error";
		}
		return "admin/ent/abstractInfo";
	}

	/**
	 * 产品资讯
	 * @param id 项目表主键
	 * @return
	 */
	@RequestMapping("newsList/{id}")
	public String newsList(ModelMap mm, @PathVariable("id") Long id){
		CatsProject project = service.getProjectInfoById(id);
		mm.addAttribute("project",project);
		return "admin/ent/newsList";
	}

	/**
	 * 产品文献
	 * @param id 项目表主键
	 * @return
	 */
	@RequestMapping("literatureList/{id}")
	public String literatureList(ModelMap mm, @PathVariable("id") Long id){
		CatsProject project = service.getProjectInfoById(id);
		mm.addAttribute("project",project);
		return "admin/ent/literatureList";
	}

	/**
	 * ajax--资讯检索接口
	 * @param id 项目id
	 * @param startDate 开始时间 yyyy-MM-dd
	 * @param endDate 结束时间 yyyy-MM-dd
	 * @return
	 */
	@RequestMapping("getNewsList")
	@ResponseBody
	public JsonElement getNewsList(Long id,String startDate,String endDate){
		String errMsg = "";
		try {
			//组合参数
			String url = buildParamNews(id,1,startDate,endDate);
			if(StringUtils.isEmpty(url)){
				return JsonWrapper.newDataInstance(null);
			}
			String result=CommonApiUtil.getUrl(url);
			if (StringUtils.isNotEmpty(result)) {
				JsonElement element = new JsonParser().parse(result);
				JsonObject jsonObj = element.getAsJsonObject();
				if(jsonObj.get("totalRow") != null){
					JsonArray data = jsonObj.get("list").getAsJsonArray();
					return JsonWrapper.newDataInstance(data);
				}else if(!jsonObj.get("success").getAsBoolean()){
					String msg = jsonObj.get("msg").getAsString();
					errMsg = "ajax--接口获取资讯时出错。msg="+msg;
					logger.error(errMsg);
					return JsonWrapper.newErrorInstance(errMsg);
				}
			}else{
				/*errMsg = "接口返回资讯时出错，结果为空，请稍后重试。";
				logger.error(errMsg);
				return JsonWrapper.newErrorInstance(errMsg);*/
				return JsonWrapper.newDataInstance(null);
			}
		} catch (Exception e) {
			errMsg = "ajax--接口获取资讯时出错。";
			e.printStackTrace();
			logger.error(errMsg, e);
			return JsonWrapper.newErrorInstance(errMsg);
		}
		return null;
	}

	/**
	 * ajax--资讯统计接口
	 * @param id 项目id
	 * @param startDate 开始时间 yyyy-MM-dd
	 * @param endDate 结束时间 yyyy-MM-dd
	 * @return
	 */
	@RequestMapping("getNewsAnalyze")
	@ResponseBody
	public JsonElement getNewsAnalyze(Long id,String startDate,String endDate){
		String errMsg = "";
		try {
			//组合参数
			String url = buildParamNews(id,2,startDate,endDate);
			if(StringUtils.isEmpty(url)){
				return JsonWrapper.newDataInstance(null);
			}
			String result=CommonApiUtil.getUrl(url);
			logger.error(result);
			if (StringUtils.isNotEmpty(result)) {
				JsonElement element = new JsonParser().parse(result);
				JsonObject jsonObj = element.getAsJsonObject();
				if(jsonObj.get("total") != null){
					JsonArray data = jsonObj.get("data").getAsJsonArray();
					Gson gson = new Gson();
					List<NewsModel> newsList= gson.fromJson(data, new TypeToken<ArrayList<NewsModel>>(){}.getType());
//					newsList = newList(startDate,endDate,newsList);
					return JsonWrapper.newDataInstance(newsList);
				}else if(!jsonObj.get("success").getAsBoolean()){
					String msg = jsonObj.get("msg").getAsString();
					errMsg = "ajax--接口统计资讯时出错。msg="+msg;
					logger.error(errMsg);
					return JsonWrapper.newErrorInstance(errMsg);
				}
			}else{
				return JsonWrapper.newDataInstance(null);
			}
		} catch (Exception e) {
			errMsg = "ajax--接口统计资讯时出错。";
			e.printStackTrace();
			logger.error(errMsg, e);
			return JsonWrapper.newErrorInstance(errMsg);
		}
		return null;
	}

	private List<NewsModel> newList(String startDate,String endDate,List<NewsModel> newsList) throws Exception {
		Long st = TimeUtil.stringToLong(startDate,TimeUtil.FORMAT_DATE);
		Long et = TimeUtil.stringToLong(endDate,TimeUtil.FORMAT_DATE);
		Long days = (et-st)/(24*60*60*1000);
		if(days<180){
			return newsList;
		}
		List<NewsModel> list = new ArrayList<NewsModel>();
		String month = "";
		for(int i=0;i<newsList.size();i++){
			NewsModel model = newsList.get(i);
			String time = model.getName().substring(5,7);
			if(time.equals(month)){
				continue;
			}else{
				month = time;
			}
			Integer total = Integer.parseInt(model.getValue());
			for(int j=i+1;j<newsList.size()-1;j++){
				NewsModel modelJ = newsList.get(j);
				String timeJ = modelJ.getName().substring(5,7);
				if(time.equals(timeJ)){
					total = total + Integer.parseInt(modelJ.getValue());
				}
			}
			if(total>0){
				NewsModel m = new NewsModel();
				m.setName(model.getName().substring(0,7));
				if(StringUtils.isNotEmpty(m.getValue())){
					m.setValue(total.toString());
				}else{
					m.setValue(total.toString());
				}
				list.add(m);
			}
			newsList.remove(model);
		}
		return list;
	}

	//资讯-组合url
	private String buildParamNews(Long id,Integer type,String startDate,String endDate) throws UnsupportedEncodingException {
		String url = "";
		if(type == 1){
			url = PropertyConst.NEWS_URL+"?token="+PropertyConst.NEWS_TOKEN;
		}else{
			url = PropertyConst.NEWS_ANALYZE+"?token="+PropertyConst.NEWS_TOKEN;
		}
		//获取资讯配置
		List<CatsProjectNewsConfig> newsConfigs = projectService.listProjectNewsConfig(id);
		if(newsConfigs != null && newsConfigs.size()>0){
			for(CatsProjectNewsConfig c:newsConfigs){
				url = url+"&keyword[]="+URLEncoder.encode(c.getCpncKeywords(), "UTF-8");
			}
		}else{
			return null;
		}
//		接口的关键字默认检索标题摘要关键词
//		可以加检索范围指定是标题还是摘要还是关键词
		//&field[]=title&field[]=digest&field[]=content
		url = url+"&startDate="+startDate+"&endDate="+endDate;
		if(type == 1){
			url = url+"&page=1&pageSize=10";
		}
		return url;
	}

	/**
	 * ajax--文献检索接口
	 * @return
	 */
	@RequestMapping("getLiteratureList")
	@ResponseBody
	public JsonElement getLiteratureList(Long id,String startDate,String endDate){
		String errMsg = "";
		try {
			//组合参数
			String url = buildParamLiterature(id,1,startDate,endDate);
			if(StringUtils.isEmpty(url)){
				return JsonWrapper.newDataInstance(null);
			}
			String result=CommonApiUtil.getUrl(url);
			if (StringUtils.isNotEmpty(result)) {
				JsonElement element = new JsonParser().parse(result);
				JsonObject jsonObj = element.getAsJsonObject();
				if(jsonObj.get("totalRow") != null){
					JsonArray data = jsonObj.get("list").getAsJsonArray();
					return JsonWrapper.newDataInstance(data);
				}else if(!jsonObj.get("success").getAsBoolean()){
					String msg = jsonObj.get("msg").getAsString();
					errMsg = "ajax--接口获取文献时出错。msg="+msg;
					logger.error(errMsg);
					return JsonWrapper.newErrorInstance(errMsg);
				}
			}else{
				/*errMsg = "接口返回文献时出错，结果为空，请稍后重试。";
				logger.error(errMsg);
				return JsonWrapper.newErrorInstance(errMsg);*/
				return JsonWrapper.newDataInstance(null);
			}
		} catch (Exception e) {
			errMsg = "ajax--接口获取文献时出错。";
			e.printStackTrace();
			logger.error(errMsg, e);
			return JsonWrapper.newErrorInstance(errMsg);
		}
		return null;
	}

	/**
	 * ajax--文献统计接口
	 * @param id 项目id
	 * @param startDate 开始时间 yyyy-MM-dd
	 * @param endDate 结束时间 yyyy-MM-dd
	 * @return
	 */
	@RequestMapping("getLiteratureAnalyze")
	@ResponseBody
	public JsonElement getLiteratureAnalyze(Long id,String startDate,String endDate){
		String errMsg = "";
		try {
			//组合参数
			String url = buildParamLiterature(id,2,startDate,endDate);
			if(StringUtils.isEmpty(url)){
				return JsonWrapper.newDataInstance(null);
			}
			String result=CommonApiUtil.getUrl(url);
			if (StringUtils.isNotEmpty(result)) {
				JsonElement element = new JsonParser().parse(result);
				JsonObject jsonObj = element.getAsJsonObject();
				if(jsonObj.get("total") != null){
					JsonArray data = jsonObj.get("data").getAsJsonArray();
					return JsonWrapper.newDataInstance(data);
				}else if(!jsonObj.get("success").getAsBoolean()){
					String msg = jsonObj.get("msg").getAsString();
					errMsg = "ajax--接口统计文献时出错。msg="+msg;
					logger.error(errMsg);
					return JsonWrapper.newErrorInstance(errMsg);
				}
			}else{
				return JsonWrapper.newDataInstance(null);
			}
		} catch (Exception e) {
			errMsg = "ajax--接口统计文献时出错。";
			e.printStackTrace();
			logger.error(errMsg, e);
			return JsonWrapper.newErrorInstance(errMsg);
		}
		return null;
	}

	//文献-组合url
	private String buildParamLiterature(Long id,Integer type,String startDate,String endDate) throws UnsupportedEncodingException {
		String url = "";
		if(type == 1){
			url = PropertyConst.PAPERS_URL+"?token="+PropertyConst.NEWS_TOKEN;
		}else{
			url = PropertyConst.PAPERS_ANALYZE+"?token="+PropertyConst.NEWS_TOKEN;
		}
		//获取文献配置
		List<CatsProjectLiteratureConfig> literatureConfigs = projectService.listProjectLiteratureConfig(id);
		if(literatureConfigs != null && literatureConfigs.size()>0){
			for(CatsProjectLiteratureConfig c:literatureConfigs){
				String keywords = "";
				//标题/摘要/关键词
				if(StringUtils.isNotEmpty(c.getCplcTitle())){
					keywords = c.getCplcTitle();
				}
				if(StringUtils.isNotEmpty(c.getCplcAbstract())){
					if(StringUtils.isNotEmpty(keywords)){
						keywords = keywords +"+";
					}
					keywords = keywords+c.getCplcAbstract();
				}
				if(StringUtils.isNotEmpty(c.getCplcKeyword())){
					if(StringUtils.isNotEmpty(keywords)){
						keywords = keywords +"+";
					}
					keywords = keywords+c.getCplcKeyword();
				}
				if(StringUtils.isNotEmpty(keywords)){
					url = url+"&keyword[]="+URLEncoder.encode(keywords, "UTF-8");
				}
			}
		}else{
			return null;
		}
//		接口的关键字默认检索标题摘要关键词
//		可以加检索范围指定是标题还是摘要还是关键词
//		&field[]=title&field[]=digest&field[]=content
		url = url+"&startDate="+startDate+"&endDate="+endDate;
		if(type == 1){
			url = url+"&page=1&pageSize=10";
		}
		return url;
	}

	/**
	 * 调研报告列表
	 * @return
	 */
	@RequestMapping("surveyList/{id}")
	public String surveyList(ModelMap mm, @PathVariable("id") Long id){
		try {
			List<CatsSurveyProject> surveyProjectList = service.getSurveyFinishProjectList(id);
			mm.addAttribute("surveyProjectList", surveyProjectList);
			CatsProject project = service.getProjectInfoById(id);
			mm.addAttribute("project",project);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取调研报告列表出错", e);
			return "error";
		}
		return "admin/ent/surveyList";
	}


	/**
	 * 账号管理
	 * @return
	 */
	@RequestMapping("userManage/{id}")
	public String userManage(ModelMap mm, @PathVariable("id") Long id){
		CatsProject project = service.getProjectInfoById(id);
		mm.addAttribute("project",project);

		AdminUserModel user = AdminUserUtil.getLoginUser();
		mm.addAttribute("user", user);
		return "admin/ent/userManage";
	}

	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping("changePwd")
	@ResponseBody
	public JsonElement changePwd(String oldPwd,String newPwd){
		try {
			boolean flag = service.changePwd(oldPwd,newPwd);
			return JsonWrapper.newDataInstance(flag);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改密码时出错。", e);
			return JsonWrapper.newErrorInstance("修改密码时出错。");
		}
	}


	/**
	 * 下载报告
	 * @return
	 */
	@RequestMapping("downloadReport/{id}")
	public ResponseEntity<byte[]> download(HttpServletRequest request, @PathVariable("id") Long id) throws IOException {
		CatsSurveyProject surveyProject = service.getSurvyProjectBy(id);
		String filePpath = surveyProject.getCspSurveyReportPath();
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

}
