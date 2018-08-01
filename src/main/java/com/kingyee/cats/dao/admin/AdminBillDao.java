package com.kingyee.cats.dao.admin;

import com.kingyee.cats.db.CmBills;
import com.kingyee.common.db.CommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ph on 2016/7/29.
 */
@Repository
public class AdminBillDao extends CommonDao {

    /**
     * 根据业务id取得账单表
     * @return
     */
    public List<CmBills> getBillByBizId(Integer bizType, Long bizId){
        Map<String, Object> parp = new HashMap<>();
        parp.put("bizId", bizId);
        parp.put("bizType", bizType);

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmBills ");
        hql.append(" WHERE cbBizId =:bizId AND cbBizType =:bizType");
        return dao.createQuery(hql.toString(), parp);
    }



}
