package com.kingyee.cats.service.admin;

import com.google.gson.JsonObject;
import com.kingyee.cats.bean.FinanceDetailModel;
import com.kingyee.cats.dao.admin.AdminFinanceDao;
import com.kingyee.cats.db.CatsWithdrawRecord;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.sms.SmsTplUtil;
import com.kingyee.common.util.PropertyConst;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 财务管理
 *
 * @author nmy
 * 2018年05月16日
 */
@Service
public class AdminFinanceService {
	private final static Logger logger = LoggerFactory.getLogger(AdminFinanceService.class);

	@Autowired
	private AdminFinanceDao dao;
	@Autowired
	protected WxMpService wxMpService;

	/**
	 * 取得财务列表
	 * @param searchBean
	 * @param pageInfo
	 * @return
	 */
	public POJOPageInfo getFinanceList(SearchBean searchBean, POJOPageInfo pageInfo) {
		return dao.getFinanceList(searchBean, pageInfo);
	}

	/**
	 * 用户账单明细列表
	 * @param searchBean
	 * @param pageInfo
	 * @return
	 */
	public POJOPageInfo getFinanceDetailList(SearchBean searchBean, POJOPageInfo pageInfo) {
		pageInfo = dao.getFinanceDetailList(searchBean, pageInfo);
		List<FinanceDetailModel> list = new ArrayList<FinanceDetailModel>();
		if(pageInfo.getCount() > 0 && pageInfo.getItems() != null && pageInfo.getItems().size() >= 0){
			for(Object[] obj :(List<Object[]>)pageInfo.getItems()){
				FinanceDetailModel model = new FinanceDetailModel();
				if(obj[0] != null){
					model.setTradeCategory(obj[0].toString());
				}
				if(obj[1] != null ){
					String type = obj[1].toString();
					//提现
					if("1".equals(type)){
						if(obj[4] != null && !"-1".equals(obj[4].toString())){
							model.setCardType("银行卡");
							model.setCardNo(obj[4].toString());
						}else if(obj[5] != null && !"-1".equals(obj[5].toString())){
							model.setCardType("支付宝");
							model.setCardNo(obj[5].toString());
						}
					}
				}
				if(obj[3] != null){
					model.setMoney(Double.parseDouble(obj[3].toString()));
				}
				if(obj[6] != null){
					model.setTime(Long.parseLong(obj[6].toString()));
				}
				list.add(model);
			}
			pageInfo.setItems(list);
		}
		return 	pageInfo;
	}

	/**
	 * 打款
	 * @param wdId
	 * @throws Exception
	 */
	public String updateWithDraw(Long wdId) throws Exception{
		if(wdId != null){
			CatsWithdrawRecord c = dao.get(CatsWithdrawRecord.class,wdId);
			if(c.getCwrStatus() != 1){
				//修改锁定余额
				CmUser user = dao.get(CmUser.class,c.getCwrCuId());
				if(user.getCuLockMoney() != null){
					if(user.getCuLockMoney() - c.getCwrWithdrawMoney() >= 0){
						//锁定余额
						user.setCuLockMoney(user.getCuLockMoney() - c.getCwrWithdrawMoney());
						dao.update(user);
					}else{
						//提现金额和余额计算不一致
						return wdId.toString();
					}
				}else{
					//锁定余额为空
					return wdId.toString();
				}

				//修改打款状态
				c.setCwrStatus(1);
				c.setCwrRemitTime(System.currentTimeMillis());
				dao.update(c);
			}
		}
		return null;
	}

	/**
	 * 退回余额
	 * @param wdId
	 * @throws Exception
	 */
	public String backMoney(Long wdId) throws Exception{
		if(wdId != null){
			CatsWithdrawRecord c = dao.get(CatsWithdrawRecord.class,wdId);
			if(c.getCwrStatus() != 1){
				CmUser user = dao.get(CmUser.class,c.getCwrCuId());
				//修改余额
				user.setCuBalance(user.getCuBalance() != null ?user.getCuBalance()+c.getCwrWithdrawMoney() :c.getCwrWithdrawMoney());
				//修改锁定余额
				if(user.getCuLockMoney() - c.getCwrWithdrawMoney()<0){
					//锁定余额与退回的钱数计算不一致
					return wdId.toString();
				}
				user.setCuLockMoney(user.getCuLockMoney() - c.getCwrWithdrawMoney());
				dao.update(user);

				//修改打款状态：已退回
				c.setCwrStatus(2);
				c.setCwrRemitTime(System.currentTimeMillis());
				dao.update(c);
			}
		}
		return null;
	}

	/**
	 * 后台-发送消息-微信模板消息和短信消息
	 * @param cwrId
	 * @throws Exception
	 */
	public void sendMsg(String cwrId) throws Exception {
		if(cwrId.indexOf(",")<0){
			CatsWithdrawRecord c = dao.get(CatsWithdrawRecord.class,Long.parseLong(cwrId));
			this.sendMsgByC(c);
		}else{
			String idarry[] = cwrId.split(",");
			for(String ida :idarry){
				if(StringUtils.isNotEmpty(ida)){
					CatsWithdrawRecord c = dao.get(CatsWithdrawRecord.class,Long.parseLong(ida));
					this.sendMsgByC(c);
				}
			}
		}
	}

	/**
	 * 后台-发送消息-微信模板消息和短信消息
	 * @throws Exception
	 */
	private void sendMsgByC(CatsWithdrawRecord c) throws Exception {
		CmUser user = dao.get(CmUser.class,c.getCwrCuId());
		//是否绑定微信
		List<CmWechatUser> wechatUser = dao.getWechatUserByUserId(user.getCuId());
		String firstStr = "";
		String alipay = "";
		String bankNo = "";
		if(StringUtils.isNotEmpty(c.getCwrAlipayUserName())){
			alipay = c.getCwrAlipayUserName();
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher alipayIsNum = pattern.matcher(alipay);
			if(alipayIsNum.matches()){
				String startNo = alipay.substring(0,3);
				String endNo = alipay.substring(alipay.length()-4);
				alipay = startNo+"****"+endNo;
			}else{
				String startNo = alipay.substring(0,2);
				String endNo ="";
				if(alipay.indexOf("@")>=0){
					endNo = alipay.substring(alipay.indexOf("@"),alipay.length());
				}else{
					endNo = alipay.substring(alipay.length()-2);
				}
				alipay = startNo+"****"+endNo;
			}
			firstStr = "提现成功！已提现至支付宝"+alipay+"账号中！";
		}else if(StringUtils.isNotEmpty(c.getCwrBankNo())){
			bankNo = c.getCwrBankNo();
			String startNo = bankNo.substring(0,4);
			String endNo = bankNo.substring(bankNo.length()-4);
			bankNo = startNo+"*******"+endNo;
			firstStr = "提现成功！已提现至银行卡"+bankNo+"账号中！";
		}
		if(wechatUser != null && wechatUser.size()>0 && StringUtils.isNotEmpty(wechatUser.get(0).getCwuOpenid()) && wechatUser.get(0).getCwuSubscribe() == 1){
			//发送模板消息
			WxMpTemplateMessage message = new WxMpTemplateMessage();
			message.setToUser(wechatUser.get(0).getCwuOpenid());
			message.setTemplateId(PropertyConst.TEMPLATE_BALANCE_CHANGE);

			WxMpTemplateData first = new WxMpTemplateData("first", firstStr, "#173177");
			message.addWxMpTemplateData(first);
			WxMpTemplateData keyword1 = new WxMpTemplateData("keyword1", "提现", "#173177");
			message.addWxMpTemplateData(keyword1);
			WxMpTemplateData keyword2 = new WxMpTemplateData("keyword2", c.getCwrWithdrawMoney().toString(), "#173177");
			message.addWxMpTemplateData(keyword2);
			WxMpTemplateData keyword3 = new WxMpTemplateData("keyword3", c.getCwrRemitTimeStr(), "#173177");
			message.addWxMpTemplateData(keyword3);
			WxMpTemplateData keyword4 = new WxMpTemplateData("keyword4", user.getCuBalance().toString(), "#173177");
			message.addWxMpTemplateData(keyword4);
			WxMpTemplateData remark = new WxMpTemplateData("remark", "感谢使用, 如有问题请及时联系我们！", "#173177");
			message.addWxMpTemplateData(remark);
			try {
				wxMpService.getTemplateMsgService().sendTemplateMsg(message);
			} catch (WxErrorException e) {
				logger.error("发送打款提示模板消息失败：openid=" + wechatUser.get(0).getCwuOpenid());
			}
		}else if(StringUtils.isNotEmpty(user.getCuTelNo())){
			//发短信
			List<String> values = new ArrayList<String>();
			values.add(user.getCuRealName());
			values.add(c.getCwrWithdrawMoney().toString());
			if(StringUtils.isNotEmpty(alipay)){
				values.add("支付宝"+alipay);
			}else{
				values.add("银行卡"+bankNo);
			}
			SmsTplUtil.sendSms(user.getCuTelNo(), PropertyConst.SMS_TEMPLATE_WITHDRAW,values,"医调研");
		}
	}

	/**
	 * 批量修改打款状态
	 * @param ids
	 * @throws Exception
	 */
	public String updateBatchWithDraw(String ids) throws Exception {
		String errorIds = "";
		if(ids != null && !ids.equals("")){
			String[] idArray = ids.split(",");
			for(int i = 0; i < idArray.length; i++){
				if(!idArray[i].trim().equals("")){
					String id = updateWithDraw(Long.parseLong(idArray[i].trim()));
					if(id != null){
						//修改余额失败
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						errorIds = errorIds+","+id;
					}
				}
			}
		}
		return errorIds;
	}

	/**
	 * 通过ID获取用户信息
	 * @param userId
	 * @throws Exception
	 */
	public CmUser getUserById(Long userId) {
		return dao.get(CmUser.class,userId);
	}

	/**
	 * 退回余额发消息
	 * @param wdId
	 * @throws Exception
	 */
	public boolean sendMsgBack(Long wdId)throws Exception {
		CatsWithdrawRecord c = dao.get(CatsWithdrawRecord.class,wdId);
		CmUser user = dao.get(CmUser.class,c.getCwrCuId());
		//是否绑定微信
		List<CmWechatUser> wechatUser = dao.getWechatUserByUserId(user.getCuId());
		String firstStr = "";
		firstStr = "提现失败！您申请的提现已退回至您的余额中！";
		if(wechatUser != null && wechatUser.size()>0 && StringUtils.isNotEmpty(wechatUser.get(0).getCwuOpenid()) && wechatUser.get(0).getCwuSubscribe() == 1){
			//发送模板消息
			WxMpTemplateMessage message = new WxMpTemplateMessage();
			message.setToUser(wechatUser.get(0).getCwuOpenid());
			message.setTemplateId(PropertyConst.TEMPLATE_BALANCE_CHANGE);

			WxMpTemplateData first = new WxMpTemplateData("first", firstStr, "#173177");
			message.addWxMpTemplateData(first);
			WxMpTemplateData keyword1 = new WxMpTemplateData("keyword1", "提现", "#173177");
			message.addWxMpTemplateData(keyword1);
			WxMpTemplateData keyword2 = new WxMpTemplateData("keyword2", c.getCwrWithdrawMoney().toString(), "#173177");
			message.addWxMpTemplateData(keyword2);
			WxMpTemplateData keyword3 = new WxMpTemplateData("keyword3", c.getCwrRemitTimeStr(), "#173177");
			message.addWxMpTemplateData(keyword3);
			WxMpTemplateData keyword4 = new WxMpTemplateData("keyword4", user.getCuBalance().toString(), "#173177");
			message.addWxMpTemplateData(keyword4);
			WxMpTemplateData remark = new WxMpTemplateData("remark", "感谢使用, 如有问题请及时联系我们！", "#173177");
			message.addWxMpTemplateData(remark);
			try {
				wxMpService.getTemplateMsgService().sendTemplateMsg(message);
			} catch (WxErrorException e) {
				logger.error("发送退回余额提示模板消息失败：openid=" + wechatUser.get(0).getCwuOpenid());
			}
			return true;
		}else{
			return false;
		}
	}
}
