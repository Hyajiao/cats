package com.kingyee.cats.dao.wechat;

import com.kingyee.cats.db.CatsPayeeInfo;
import com.kingyee.common.db.CommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hanyajiao
 */


@Repository
public class UserInfoDao extends CommonDao {

    /**
     * 查询用户账号信息
     * @param userId
     * @return
     */
    public List<CatsPayeeInfo> getPayeeInfo(Long userId,Integer cpiWithdrawMode) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> parp = new HashMap<String,Object>();
        parp.put("userId",userId);
        parp.put("cpiWithdrawMode",cpiWithdrawMode);
        sb.append(" FROM CatsPayeeInfo WHERE 1=1 ");
        sb.append(" AND cpiCuId =:userId");
        sb.append(" AND cpiWithdrawMode=:cpiWithdrawMode");
        return dao.createQuery(sb.toString(),parp);
    }

    /**
     * 查询默认账号信息
     * @param userId
     */
    public CatsPayeeInfo getUserPayInfo(Long userId,Integer cpiWithdrawMode) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> parp = new HashMap<String,Object>();
        parp.put("userId",userId);
        parp.put("cpiWithdrawMode",cpiWithdrawMode);
        sb.append(" FROM CatsPayeeInfo WHERE 1=1 ");
        sb.append(" AND cpiCuId =:userId");
        sb.append(" AND cpiIsDefault=0");
        sb.append(" AND cpiWithdrawMode=:cpiWithdrawMode");
        return dao.findOne(sb.toString(),parp);
    }

}
