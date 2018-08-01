package com.kingyee.cats.service.wechat;

import com.kingyee.cats.dao.UserDao;
import com.kingyee.cats.dao.wechat.UserInfoDao;
import com.kingyee.cats.db.CatsPayeeInfo;
import com.kingyee.cats.db.CmUser;
import com.kingyee.common.IBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hanyajiao
 */


@Service
public class UserInfoService implements IBaseService{


    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserDao  userDao;

    /**
     * 获取用户的支付信息
     * @param userId
     * @return
     */
    public List<CatsPayeeInfo> getUsePayeeList(Long userId,Integer cpiWithdrawMode) {
        List<CatsPayeeInfo> payeeInfoList = userInfoDao.getPayeeInfo(userId,cpiWithdrawMode);
        if(payeeInfoList!=null&&payeeInfoList.size()>0){
            return payeeInfoList;
        }
        return null;
    }

    /**
     * 获取详细信息
     * @param bId
     * @return
     */
    public CatsPayeeInfo getDetail(Long bId) {
        return userInfoDao.get(CatsPayeeInfo.class,bId);
    }


    /**
     * 保存或更新信息
     * @param catsPayeeInfo
     */
    public void saveOrUpdate(CatsPayeeInfo catsPayeeInfo,Long Id,Integer type) {
        if(Id == null){
            userInfoDao.save(catsPayeeInfo);
            List<CatsPayeeInfo> usePayeeList = userInfoDao.getPayeeInfo(catsPayeeInfo.getCpiCuId(), type);
            if(usePayeeList==null&&usePayeeList.size()==0){
                catsPayeeInfo.setCpiIsDefault(0);
            }
        }else{
            CatsPayeeInfo payeeInfo = userInfoDao.get(CatsPayeeInfo.class, Id);
            if(type==0){
                payeeInfo.setCpiBankName(catsPayeeInfo.getCpiBankName());
                payeeInfo.setCpiBankNo(catsPayeeInfo.getCpiBankNo());
                payeeInfo.setCpiWithdrawMode(0);
            }
            if(type==1){
                payeeInfo.setCpiAlipayUserName(catsPayeeInfo.getCpiAlipayUserName());
                payeeInfo.setCpiWithdrawMode(1);
            }
            payeeInfo.setCpiRealName(catsPayeeInfo.getCpiRealName());
            payeeInfo.setCpiUpdateTime(System.currentTimeMillis());
            userInfoDao.update(payeeInfo);
        }
    }

    /**
     * 删除信息
     * @param bId
     */
    public void deleteInfo(Long bId) {
        CatsPayeeInfo payeeInfo = userInfoDao.get(CatsPayeeInfo.class, bId);
        userInfoDao.del(payeeInfo);
    }

    /**
     * 添加银行卡或支付宝
     * @param type
     * @param catsPayeeInfo
     */
    public void saveBankOrAliPay(Integer type, CatsPayeeInfo catsPayeeInfo) {
        if(type==0){
            catsPayeeInfo.setCpiWithdrawMode(0);
        }
        if(type ==1){
            catsPayeeInfo.setCpiWithdrawMode(1);
        }
        catsPayeeInfo.setCpiCreateTime(System.currentTimeMillis());
        userInfoDao.save(catsPayeeInfo);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    public CmUser getUserInfo(Long userId) {
        return userDao.get(CmUser.class,userId);
    }

    /**
     * 获取默认信息
     * @param userId
     * @param type
     */
    public CatsPayeeInfo getDefaultInfo(Long userId, Integer type) {
       return userInfoDao.getUserPayInfo(userId, type);
    }

    /**
     * 更新状态
     * @param payeeInfo
     */
    public void updateDefault(CatsPayeeInfo payeeInfo) {
        userInfoDao.update(payeeInfo);
    }


}
