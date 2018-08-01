package com.kingyee.cats.dao;

import com.kingyee.common.db.CommonDao;
import com.kingyee.common.util.TimeUtil;
import com.kingyee.cats.db.CmSmsCode;
import com.kingyee.cats.enums.SmsTypeEnum;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信相关DAO
 * Created by ph on 2017/5/12
 */
@Repository
public class SmsDao extends CommonDao {


    /**
     * 取得当天的短信数据。
     * @param telNo
     * @return
     */
    public List<CmSmsCode> getTodaySmsCodeByTelNo(String telNo){
        long today = TimeUtil.getNowDate();
        long tomorrow = TimeUtil.getNextAssignDate(1);
        Map<String, Object> parp = new HashMap<>();
        parp.put("telNo", telNo);
        parp.put("today", today);
        parp.put("tomorrow", tomorrow);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmSmsCode WHERE cscTelNo =:telNo ");
        hql.append(" AND cscCreateDate >=:today ");
        hql.append(" AND cscCreateDate <=:tomorrow ");
        hql.append(" ORDER BY cscCreateDate DESC ");
        return dao.createQuery(hql.toString(), parp);
    }


    /**
     * 取得当天的短信数据。
     * @param ip
     * @return
     */
    public List<CmSmsCode> getTodaySmsCodeByIp(String ip){
        long today = TimeUtil.getNowDate();
        long tomorrow = TimeUtil.getNextAssignDate(1);
        Map<String, Object> parp = new HashMap<>();
        parp.put("ip", ip);
        parp.put("today", today);
        parp.put("tomorrow", tomorrow);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmSmsCode WHERE cscIpAddress =:ip ");
        hql.append(" AND cscCreateDate >=:today ");
        hql.append(" AND cscCreateDate <=:tomorrow ");
        return dao.createQuery(hql.toString(), parp);
    }


    /**
     * 有效的code
     * @param telNo
     * @return
     */
    public List<CmSmsCode> getSmsCodeByTelNoAndCode(String telNo, String code, SmsTypeEnum smsTypeEnum){
        Map<String, Object> parp = new HashMap<>();
        parp.put("telNo", telNo);
        parp.put("code", code);
        parp.put("cscType", smsTypeEnum.text());
        parp.put("now", System.currentTimeMillis());
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmSmsCode WHERE cscTelNo =:telNo ");
        hql.append(" AND cscCode =:code ");
        hql.append(" AND cscIsUsed = 0 ");
        hql.append(" AND cscType =:cscType ");
        hql.append(" AND cscInvalidDate >=:now");
        return dao.createQuery(hql.toString(), parp);
    }

}
