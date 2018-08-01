package com.kingyee.cats.service.wechat;


import com.kingyee.cats.dao.UserDao;
import com.kingyee.cats.dao.wechat.BillDao;
import com.kingyee.cats.dao.wechat.UserInfoDao;
import com.kingyee.cats.dao.wechat.WallatDrawDao;
import com.kingyee.cats.db.CatsPayeeInfo;
import com.kingyee.cats.db.CatsWithdrawRecord;
import com.kingyee.cats.db.CmBills;
import com.kingyee.cats.db.CmUser;
import com.kingyee.common.IBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author hanyajiao
 */


@Service
public class WalletDrawService  implements IBaseService {

    @Autowired
    private WallatDrawDao wallatDrawDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BillDao billDao;
    @Autowired
    private UserInfoDao userInfoDao;


    /**
     * 保存提现记录
     * @param catsWithdrawRecord
     */
    public void save(CatsWithdrawRecord catsWithdrawRecord,Long userId) {
        CmUser user = userDao.get(CmUser.class,userId);
        if(catsWithdrawRecord.getCwrAlipayUserName()!=null && StringUtils.isNotEmpty(catsWithdrawRecord.getCwrAlipayUserName())){
            catsWithdrawRecord.setCwrWithdrawMode(1);
        }
        if(catsWithdrawRecord.getCwrBankNo()!=null && StringUtils.isNotEmpty(catsWithdrawRecord.getCwrBankNo()) &&
                catsWithdrawRecord.getCwrBankName()!=null && StringUtils.isNotEmpty(catsWithdrawRecord.getCwrBankName())){
            catsWithdrawRecord.setCwrWithdrawMode(0);
        }
        catsWithdrawRecord.setCwrCuId(userId);
        if(user.getCuTelNo()!=null){
            catsWithdrawRecord.setCwrTelNo(user.getCuTelNo());
        }
        catsWithdrawRecord.setCwrStatus(0);
        catsWithdrawRecord.setCwrWithdrawTime(System.currentTimeMillis());
        wallatDrawDao.save(catsWithdrawRecord);
        Long cwId = catsWithdrawRecord.getCwrId();
        Double balance = user.getCuBalance();
        Double cwrWithdrawMoney = catsWithdrawRecord.getCwrWithdrawMoney();
        //添加账单记录
        CmBills cmBills = new CmBills();
        cmBills.setCbCuId(userId);
        cmBills.setCbBizType(1);
        cmBills.setCbBizId(cwId);
        cmBills.setCbTradeMoney(-cwrWithdrawMoney);
        cmBills.setCbBizName("提现");
        cmBills.setCbTradeTime(System.currentTimeMillis());
        billDao.save(cmBills);
        //计算余额
        BigDecimal bd1 = new BigDecimal(Double.toString(balance));
        BigDecimal bd2 = new BigDecimal(Double.toString(cwrWithdrawMoney));
        user.setCuBalance(bd1.subtract(bd2).doubleValue());
        if(user.getCuLockMoney()!=null){
            user.setCuLockMoney(cwrWithdrawMoney+user.getCuLockMoney());
        }else{
            user.setCuLockMoney(cwrWithdrawMoney);
        }
        userDao.update(user);
    }


    /**
     * 查询账号默认信息
     * @param userId
     * @param cpiWithdrawMode
     * @return
     */
    public CatsPayeeInfo getUserBankInfo(Long  userId, Integer cpiWithdrawMode) {
        CatsPayeeInfo payeeInfo = userInfoDao.getUserPayInfo(userId,cpiWithdrawMode);
        if(payeeInfo!=null){
            return  payeeInfo;
        }
        return null;
    }

    /**
     * 查询账号信息
     * @param bId
     * @return
     */
    public CatsPayeeInfo getBankCard(Long bId) {
        return  userInfoDao.get(CatsPayeeInfo.class,bId);
    }
}
