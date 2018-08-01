package com.kingyee.cats.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingyee.common.IBaseService;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.sms.SmsTplUtil;
import com.kingyee.common.util.RandomUtil;
import com.kingyee.common.util.ValidateUtil;
import com.kingyee.cats.common.SmsConst;
import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.dao.SmsDao;
import com.kingyee.cats.db.CmSmsCode;
import com.kingyee.cats.enums.SmsTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信验证码管理
 * 
 * @author peihong
 * 2017年05月12日
 */
@Service
public class SmsService implements IBaseService {
	
    @Autowired
    private SmsDao smsDao;

    /**
     * 保存验证码功能
     *
     * 1.一个手机每天最多三回
     * 2.一个ip每天最多10回
     * 3.60秒内，不允许重复发送
     * @param telNo
     */
    public JsonElement saveSmsCode(String ip, String telNo, SmsTypeEnum smsTypeEnum){

        // 如果不为手机号
        if(!ValidateUtil.isMobile(telNo)){
            return JsonWrapper.newErrorInstance("请输入正确的手机号码");
        }
        // 1.一个手机每天最多三回
        List<CmSmsCode> smsList = smsDao.getTodaySmsCodeByTelNo(telNo);
        if(smsList != null && smsList.size() >= 10){
            return JsonWrapper.newErrorInstance("此手机号已超过了最大限制。");
        }
        // 2.60秒内，不允许重复发送
        CmSmsCode smsCode = null;
        if(smsList != null && smsList.size() > 0){
            smsCode = smsList.get(0);
            if((System.currentTimeMillis() - smsCode.getCscCreateDate()) < 60000){
                return JsonWrapper.newErrorInstance("您发送的太频繁了，请稍后再试。");
            }
        }
        // 3.一个ip每天最多10回
        smsList = smsDao.getTodaySmsCodeByIp(ip);
        if(smsList != null && smsList.size() >= 50){
            return JsonWrapper.newErrorInstance("此IP已超过了最大限制。");
        }

        smsCode = new CmSmsCode();
        smsCode.setCscTelNo(telNo);
        smsCode.setCscType(smsTypeEnum.text());
        smsCode.setCscCode(RandomUtil.generateIntString(6));

        // 有效期5分钟。
        long time = System.currentTimeMillis();
        time = time + 5 * 60 * 1000;
        smsCode.setCscUserId(UserUtil.getUserId());
        smsCode.setCscInvalidDate(time);
        smsCode.setCscIpAddress(ip);
        smsCode.setCscIsUsed(0);
        smsCode.setCscCreateDate(System.currentTimeMillis());
        smsDao.save(smsCode);

        List<String> values = new ArrayList<>();
        values.add(smsCode.getCscCode());
        JsonObject obj = SmsTplUtil.sendSms(telNo, SmsConst.TEMPLATE_CODE, values, SmsConst.SIGN);
        if(obj.get("success").getAsString().equals("true")){
            return JsonWrapper.newSuccessInstance();
        }else{
            return JsonWrapper.newErrorInstance("短信发送失败，请稍后再试。");
        }
    }


    /**
     * 验证是否是正确的验证码，并更新
     *
     * @param telNo
     */
    public boolean updateSmsCode(String telNo, String code, SmsTypeEnum smsTypeEnum){
        List<CmSmsCode> list = smsDao.getSmsCodeByTelNoAndCode(telNo, code, smsTypeEnum);
        if(list != null && list.size() > 0){
            CmSmsCode smsCode = list.get(0);
            smsCode.setCscIsUsed(1);
            smsDao.update(smsCode);
            return true;
        }else{
            return false;
        }
    }

}
