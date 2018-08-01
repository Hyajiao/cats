package com.kingyee.cats.web.admin;

import com.google.gson.JsonElement;
import com.kingyee.cats.bean.FinanceDetailModel;
import com.kingyee.cats.db.CatsWithdrawRecord;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.service.admin.AdminFinanceService;
import com.kingyee.common.BaseController;
import com.kingyee.common.Const;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务管理
 *
 * @author nmy
 * 2018年05月16日
 */
@Controller
@RequestMapping(value = "/admin/finance")
public class AdminFinanceController extends BaseController {

    @Autowired
    private AdminFinanceService service;

	/**
	 * 财务列表
	 * @param keyword 用户id
	 * @param state 打款状态：0:未打款;1:已打款
	 * @param txState 提现状态：0:支付宝;1:银行卡
	 * @param startDate 开始时间
	 * @param endDate 截止时间
	 * @param page 页码
	 * @param rowsPerPage 每页几条
	 * @return
	 */
	@RequestMapping("list")
	public String list(ModelMap mm, String keyword, Integer state,Integer txState, String startDate, String endDate, Integer page, Integer rowsPerPage) {
		if (page == null || page.intValue() == 0) {
			page = 1;
		}
		if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
			rowsPerPage = Const.ROWSPERPAGE;
		}
		POJOPageInfo pageInfo = new POJOPageInfo<CatsWithdrawRecord>(rowsPerPage , page);
		try {
			SearchBean searchBean = new SearchBean();
			if (StringUtils.isNotEmpty(keyword)) {
				searchBean.addParpField("keyword", "%" + keyword + "%");
			}
			if (StringUtils.isNotEmpty(startDate)) {
				searchBean.addParpField("startDate", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE));
			}
			if (StringUtils.isNotEmpty(endDate)) {
				searchBean.addParpField("endDate", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE)+ (24 * 3600 * 1000L));
			}
			if (state != null) {
				searchBean.addParpField("state", state);
			}
			if (txState != null) {
				searchBean.addParpField("txState", txState);
			}
			pageInfo = service.getFinanceList(searchBean, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取财务管理-打款列表信息出错。", e );
			return "error";
		}
		mm.addAttribute("keyword",keyword);
		mm.addAttribute("state",state);
		mm.addAttribute("txState",txState);
		mm.addAttribute("startDate",startDate);
		mm.addAttribute("endDate",endDate);
		mm.addAttribute("page",page);
		mm.addAttribute("rowsPerPage",rowsPerPage);
		mm.addAttribute("pageInfo", pageInfo);
		return "admin/finance/financeList";
	}

	/**
	 * 用户账单明细列表
	 * @param userId 用户id
	 * @param page 页码
	 * @param rowsPerPage 每页几条
	 * @return
	 */
	@RequestMapping("detailList")
	public String list(ModelMap mm, Long userId,Integer page, Integer rowsPerPage) {
		if (userId == null) {
			return "redirect:list";
		}
		if (page == null || page.intValue() == 0) {
			page = 1;
		}
		if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
			rowsPerPage = Const.ROWSPERPAGE;
		}
		POJOPageInfo pageInfo = new POJOPageInfo<FinanceDetailModel>(rowsPerPage , page);
		try {
			SearchBean searchBean = new SearchBean();
			searchBean.addParpField("userId", userId);
			pageInfo = service.getFinanceDetailList(searchBean, pageInfo);
			CmUser user = service.getUserById(userId);
			mm.addAttribute("user",user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取财务管理-用户账单明细列表信息出错。", e );
			return "error";
		}
		mm.addAttribute("userId",userId);
		mm.addAttribute("pageInfo",pageInfo);
		return "admin/finance/financeDetailList";
	}

	/**
	 * 打款
	 * @param wdId 提现申请id
	 * @return
	 */
	@RequestMapping("updateWithDraw")
	@ResponseBody
	public JsonElement updateWithDraw(Long wdId){
		String errMsg = "";
		try{
			String errorIds = service.updateWithDraw(wdId);
			if(StringUtils.isNotEmpty(errorIds)){
				return JsonWrapper.newErrorInstance(errorIds);
			}else{
				//发消息
				service.sendMsg(wdId.toString());
			}
		}catch(Exception e){
			errMsg = "后台管理-修改打款状态异常。id="+wdId;
			e.printStackTrace();
			logger.error(errMsg, e);
			return JsonWrapper.newErrorInstance(errMsg);
		}
		return JsonWrapper.newSuccessInstance();
	}

	/**
	 * 批量打款
	 * @param ids
	 * @return
	 */
	@RequestMapping("updateBatch")
	@ResponseBody
	public JsonElement updateBatch(String ids){
		String errMsg = "";
		try{
			if(StringUtils.isNotEmpty(ids)){
				String errorIds = service.updateBatchWithDraw(ids);
				if(StringUtils.isNotEmpty(errorIds)){
					return JsonWrapper.newErrorInstance(errorIds);
				}else{
					//发消息
					service.sendMsg(ids);
				}
			}
		}catch(Exception e){
			errMsg = "后台管理-批量修改打款状态异常。ids="+ids;
			e.printStackTrace();
			logger.error(errMsg, e);
			return JsonWrapper.newErrorInstance(errMsg);
		}
		return JsonWrapper.newSuccessInstance();
	}




	/**
	 * 退回余额
	 * @param wdId 提现申请id
	 * @return
	 */
	@RequestMapping("backMoney")
	@ResponseBody
	public JsonElement backMoney(Long wdId){
		String errMsg = "";
		try{
			if(wdId != null){
				String errorIds = service.backMoney(wdId);
				if(StringUtils.isNotEmpty(errorIds)){
					return JsonWrapper.newErrorInstance(errorIds);
				}else{
					//发消息
					boolean flag = service.sendMsgBack(wdId);
					if(!flag){
						return JsonWrapper.newSuccessInstance("退回余额成功，微信模板消息发送失败，该用户已取消关注微信公众号。");
					}
				}
			}else{
				return JsonWrapper.newErrorInstance("id为空。");
			}
		}catch(Exception e){
			errMsg = "后台管理-退回余额-修改打款状态异常。id="+wdId;
			e.printStackTrace();
			logger.error(errMsg, e);
			return JsonWrapper.newErrorInstance(errMsg);
		}
		return JsonWrapper.newSuccessInstance();
	}

	/**
	 * 导出提现记录
	 * @param keyword 用户id/手机号
	 * @param state 打款状态：0:未打款;1:已打款
	 * @param txState 提现状态：0:支付宝;1:银行卡
	 * @param startDate 开始时间
	 * @param endDate 截止时间
	 * @throws IOException
	 */
	@RequestMapping("/exportFinanceList")
	public String exportRecordList(String ids,String keyword, Integer state, Integer txState, String startDate, String endDate,HttpServletResponse response) throws IOException{
		ServletOutputStream out = response.getOutputStream();
		try{
			String filename = null;
			SearchBean searchBean = new SearchBean();
			if (StringUtils.isNotEmpty(keyword)) {
				searchBean.addParpField("keyword", "%" + keyword + "%");
			}
			if (StringUtils.isNotEmpty(startDate)) {
				searchBean.addParpField("startDate", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE));
			}
			if (StringUtils.isNotEmpty(endDate)) {
				searchBean.addParpField("endDate", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE));
			}
			if (state != null) {
				searchBean.addParpField("state", state);
			}
			if (txState != null) {
				searchBean.addParpField("txState", txState);
				filename = "打款记录-支付宝账号.xls";
				if(txState == 0){
					if (StringUtils.isNotEmpty(startDate) && StringUtils.isEmpty(endDate)) {
						filename="打款记录-支付宝账号-"+startDate+"至今.xls";
					}else if (StringUtils.isEmpty(startDate) && StringUtils.isNotEmpty(endDate) ) {
						filename="打款记录-支付宝账号-"+endDate+"之前.xls";
					}else if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) ){
						filename="打款记录-支付宝账号-"+startDate+"~"+endDate+".xls";
					}
				}else if(txState == 1){
					filename = "打款记录-银行卡号.xls";
					if (StringUtils.isNotEmpty(startDate) && StringUtils.isEmpty(endDate)) {
						filename="打款记录-银行卡号-"+startDate+"至今.xls";
					}else if (StringUtils.isEmpty(startDate) && StringUtils.isNotEmpty(endDate) ) {
						filename="打款记录-银行卡号-"+endDate+"之前.xls";
					}else if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) ){
						filename="打款记录-银行卡号-"+startDate+"~"+endDate+".xls";
					}
				}
			}

			POJOPageInfo<Object[]> pageInfo = new POJOPageInfo<Object[]>(0, 1);
			pageInfo = service.getFinanceList(searchBean, pageInfo);
			List<Object[]> dbList = pageInfo.getItems();
			List<CatsWithdrawRecord> newList = new ArrayList<CatsWithdrawRecord>();
			int size = dbList.size();
			if(StringUtils.isNotEmpty(ids)){
				String idarry[] = ids.split(",");
				for(String ida :idarry){
					if(StringUtils.isNotEmpty(ida)){
						for(int i=0;i<size;i++){
							CatsWithdrawRecord bean = (CatsWithdrawRecord)dbList.get(i)[0];
							if(ida.equals(bean.getCwrId().toString())){
								newList.add(bean);
							}
						}
					}
				}
			}else{
				for(int i=0;i<size;i++){
					CatsWithdrawRecord bean = (CatsWithdrawRecord)dbList.get(i)[0];
					newList.add(bean);
				}
			}

			filename = new String(filename.getBytes(), "ISO-8859-1");
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename="+filename);
			HSSFWorkbook book = null;

			BaseXlsTemplate xls=new BaseXlsTemplate();
			String title[]=null;
			String titleCn[]=null;
			if(txState == 0){
				title = new String[]{"支付宝账号","金额"};
				titleCn = new String[]{"cwrAlipayUserName","cwrWithdrawMoney"};
			}else if(txState == 1){
				//银行卡
				title = new String[]{"开户行名称","银行卡号","金额"};
				titleCn = new String[]{"cwrBankName","cwrBankNo","cwrWithdrawMoney"};
			}
			book = xls.createExcelBook("提现记录", title,titleCn,newList);
			if(null != book){
				book.write(out);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("后台-打款记录-导出时异常。", e);
			return "error";
		}finally {
			out.close();
		}
		return null;
	}
}
