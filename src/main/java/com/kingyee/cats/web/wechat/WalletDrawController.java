package com.kingyee.cats.web.wechat;

import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.db.CatsPayeeInfo;
import com.kingyee.cats.db.CatsWithdrawRecord;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.service.wechat.UserInfoService;
import com.kingyee.cats.service.wechat.WalletDrawService;
import com.kingyee.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 提现
 * @author hanyajiao
 */


@Controller
@RequestMapping("/user/walletDraw/")
public class WalletDrawController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(WalletDrawController.class);

    @Autowired
    private WalletDrawService walletDrawService;
    @Autowired
    private UserInfoService userInfoDService;


    /**
     * 银行卡提现初始化
     * @return
     */
    @RequestMapping("backCard")
    public String bankCard(ModelMap modelMap,Integer type){
        Long userId = UserUtil.getUserId();
        CatsPayeeInfo payeeInfo = walletDrawService.getUserBankInfo(userId,type);
        CmUser userInfo = userInfoDService.getUserInfo(userId);
        String balance = String.valueOf(userInfo.getCuBalance()==null?0L:userInfo.getCuBalance());
        modelMap.addAttribute("catsPayeeInfo",payeeInfo);
        modelMap.addAttribute("balance",balance);
        if(type==0){
            return "userInfo/bills/withdrawto";
        }
        if(type==1){
            return "userInfo/bills/aliPay";
        }
        return null;
    }

    /**
     * 支付宝提现初始化
     * @return
     */
    @RequestMapping("aliPay")
    public String aliPay(ModelMap modelMap){
        Long userId = UserUtil.getUserId();
        Integer cpiWithdrawMode = 1;
        CatsPayeeInfo payeeInfo = walletDrawService.getUserBankInfo(userId,cpiWithdrawMode);
        modelMap.addAttribute("catsPayeeInfo",payeeInfo);
        return "userInfo/bills/aliPay";
    }


    @RequestMapping("newBankOrAlipay")
    public String getBankCard(ModelMap modelMap,Long bId,Integer type){
        CatsPayeeInfo payeeInfo = walletDrawService.getBankCard(bId);
        modelMap.addAttribute("catsPayeeInfo",payeeInfo);
        if(type==0){
            return "userInfo/bills/withdrawto";
        }
        if(type==1){
            return "userInfo/bills/aliPay";
        }
        return null;
    }


    /**
     * 提现
     * @param modelMap
     * @param catsWithdrawRecord
     * @return
     */
    @RequestMapping("toBackCard")
    public String getToBackCard(ModelMap modelMap,CatsWithdrawRecord catsWithdrawRecord,Long defaultId,Integer type){
        try {
            Long userId = UserUtil.getUserId();
            Double withdrawMoney = catsWithdrawRecord.getCwrWithdrawMoney();
            CmUser userInfo = userInfoDService.getUserInfo(userId);
            if(withdrawMoney<=userInfo.getCuBalance()){
                CatsPayeeInfo catsPayeeInfo = new CatsPayeeInfo();
                catsPayeeInfo.setCpiCuId(userId);
                catsPayeeInfo.setCpiWithdrawMode(type);
                catsPayeeInfo.setCpiRealName(catsWithdrawRecord.getCwrRealName());
                catsPayeeInfo.setCpiCreateTime(System.currentTimeMillis());
                if(type ==0){
                    catsPayeeInfo.setCpiBankName(catsWithdrawRecord.getCwrBankName());
                    catsPayeeInfo.setCpiBankNo(catsWithdrawRecord.getCwrBankNo());
                }
                if(type==1){
                    catsPayeeInfo.setCpiAlipayUserName(catsWithdrawRecord.getCwrAlipayUserName());
                }
                if(defaultId==null){
                    catsPayeeInfo.setCpiIsDefault(0);
                }
                userInfoDService.saveOrUpdate(catsPayeeInfo,defaultId,type);
                walletDrawService.save(catsWithdrawRecord,userId);
            }else{
                String msg = "提现金额不能超过账户余额！";
                modelMap.addAttribute("msg",msg);
                return "warn";
            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg= "提现出错！";
            logger.error(msg,e);
            modelMap.addAttribute("msg",msg);
            return "error";
        }
        return "redirect:/user/wallet/recordList";
    }
}
