package com.kingyee.cats.web.wechat;

import com.google.gson.JsonElement;
import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.db.CatsPayeeInfo;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.service.wechat.UserInfoService;
import com.kingyee.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 个人信息
 * @author hanyajiao
 */


@Controller
@RequestMapping(value = "/user/userInfo/")
public class UserInfoController extends BaseController {

    private  final static Logger logger = LoggerFactory
            .getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 详细资料
     * @return
     */
    @RequestMapping("infoList")
    public String getUserInfo(ModelMap modelMap){
        Long  userId = UserUtil.getUserId();
        CmUser cmUser = userInfoService.getUserInfo(userId);
        modelMap.addAttribute("cmUser",cmUser);
        return "userInfo/userInfo";
    }


    /**
     * 银行卡列表
     * @param modelMap
     * @return
     */
    @RequestMapping("bankInfo")
    public String getBankInfo(ModelMap modelMap){
        Long userId = UserUtil.getUserId();
        Integer cpiWithdrawMode = 0;
        List<CatsPayeeInfo> payeeInfoList = userInfoService.getUsePayeeList(userId,cpiWithdrawMode);
        modelMap.addAttribute("bankInfoList",payeeInfoList);
        return "userInfo/bankInfo";
    }

    /**
     * 支付宝账号列表
     * @param modelMap
     * @return
     */
    @RequestMapping("aliPayInfo")
    public String getAliPayInfo(ModelMap modelMap){
        Long userId = UserUtil.getUserId();
        Integer cpiWithdrawMode = 1;
        List<CatsPayeeInfo> payeeInfoList = userInfoService.getUsePayeeList(userId,cpiWithdrawMode);
        modelMap.addAttribute("aliPayInfoList",payeeInfoList);
        return "userInfo/aliPayInfo";
    }
    /**
     * 支付宝账号列表
     * @param modelMap
     * @return
     */
    @RequestMapping("aliPayList")
    public String getAliPayList(ModelMap modelMap){
        Long userId = UserUtil.getUserId();
        Integer cpiWithdrawMode = 1;
        List<CatsPayeeInfo> payeeInfoList = userInfoService.getUsePayeeList(userId,cpiWithdrawMode);
        modelMap.addAttribute("aliPayInfoList",payeeInfoList);
        return "userInfo/aliPayList";
    }

    /**
     * 银行卡列表
     * @param modelMap
     * @return
     */
    @RequestMapping("bankList")
    public String getBankList(ModelMap modelMap){
        Long userId = UserUtil.getUserId();
        Integer cpiWithdrawMode = 0;
        List<CatsPayeeInfo> payeeInfoList = userInfoService.getUsePayeeList(userId,cpiWithdrawMode);
        modelMap.addAttribute("bankInfoList",payeeInfoList);
        return "userInfo/bankList";
    }


    /**
     * 编辑银行卡信息初始化
     * @param modelMap
     * @param bId
     * @return
     */
    @RequestMapping("editBank")
    public String getBankDetail(ModelMap modelMap,Long bId){
        CatsPayeeInfo catsPayeeInfo = userInfoService.getDetail(bId);
        modelMap.addAttribute("catsPayeeInfo",catsPayeeInfo);
        return "userInfo/editBankInfo";
    }


    /**
     * 编辑支付宝信息初始化
     * @param modelMap
     * @param bId
     * @return
     */
    @RequestMapping("editAliPay")
    public String addAliPayInfo(ModelMap modelMap,Long bId){
        CatsPayeeInfo catsPayeeInfo = userInfoService.getDetail(bId);
        modelMap.addAttribute("catsPayeeInfo",catsPayeeInfo);
        return "userInfo/editAliPayInfo";
    }


    /**
     * 保存信息
     * @param catsPayeeInfo
     * @param bId
     * @return
     */
    @RequestMapping("saveBankInfo")
    public String saveBankInfo(CatsPayeeInfo catsPayeeInfo,Long bId,Integer type){
        try {
            userInfoService.saveOrUpdate(catsPayeeInfo,bId,type);
            if(type==0){
                return "redirect:bankInfo";
            }
            if(type ==1){
                return "redirect:aliPayInfo";
            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "更新信息出错";
            logger.error(msg,e);
            return "error";

        }
        return null;
    }

    /**
     * 删除信息
     * @param bId
     * @param type
     * @return
     */
    @RequestMapping("deleteInfo")
    public String deleteInfo(Long bId,Integer type){
        try {
            userInfoService.deleteInfo(bId);
            if(type==0){
                return "redirect:bankInfo";
            }
            if(type ==1){
                return "redirect:aliPayInfo";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除出错！",e);
            return "error";
        }
        return null;
    }


    /**
     * 添加银行卡初始化
     * @param
     * @return
     */
    @RequestMapping("addBankInfo")
    public String addBankInit(){
        return "userInfo/addBankInfo";
    }
    /**
     * 添加支付宝初始化
     * @param
     * @return
     */
    @RequestMapping("addApliPayInfo")
    public String addAliPayInit(){
        return "userInfo/addAliPayInfo";
    }


    /**
     * 添加银行卡或支付宝账号
     * @param modelMap
     * @param type 0：银行 1：支付宝
     * @param catsPayeeInfo
     * @return
     */
    @RequestMapping("addBankOrAliPay")
    public String addBankOrAliPay(ModelMap modelMap,Integer type,CatsPayeeInfo catsPayeeInfo){
        try {
            Long userId = UserUtil.getUserId();
            List<CatsPayeeInfo> usePayeeList = userInfoService.getUsePayeeList(userId, type);
            if(null==usePayeeList||usePayeeList.size()==0){
                catsPayeeInfo.setCpiIsDefault(0);
            }
            catsPayeeInfo.setCpiCuId(userId);
            userInfoService.saveBankOrAliPay(type,catsPayeeInfo);
            if(type==0){
                return "redirect:bankList";
            }
            if(type ==1){
                return "redirect:aliPayList";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加出错",e);
            return "error";
        }
        return  null;
    }


    /**
     * 默认
     * 账号修改
     * @param bId
     * @return
     */
    @RequestMapping("changeDefault")
    public String changeDefault(Long bId,Integer type ){
        Long userId = UserUtil.getUserId();
        CatsPayeeInfo defaultInfo = userInfoService.getDefaultInfo(userId,type);
        CatsPayeeInfo nondefaultInfo = userInfoService.getDetail(bId);
        if(defaultInfo!=null){
            Long cpId = defaultInfo.getCpiId();
            if(!cpId.equals(bId)){
                nondefaultInfo.setCpiIsDefault(0);
                defaultInfo.setCpiIsDefault(1);
                userInfoService.updateDefault(nondefaultInfo);
                userInfoService.updateDefault(defaultInfo);
            }
        }else{
            nondefaultInfo.setCpiIsDefault(0);
            userInfoService.updateDefault(nondefaultInfo);
        }
        if(type==0){
            return "redirect:bankInfo";
        }
        if(type ==1){
            return "redirect:aliPayInfo";
        }
        return null;
    }
}
