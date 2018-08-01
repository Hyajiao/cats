package com.kingyee.cats.service.wechat;

import com.kingyee.cats.bean.CatsUtils;
import com.kingyee.cats.dao.wechat.BillDao;
import com.kingyee.cats.db.CmUser;
import com.kingyee.common.IBaseService;
import com.kingyee.cats.bean.BillRecordList;
import com.kingyee.common.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanyajiao
 */
@Service
public class BillService implements IBaseService {

    @Autowired
    private BillDao billDao;



    /**
     * 获取用户账户余额
     * @param Id
     */
    public CmUser getBill(Long Id) {
       return billDao.get(CmUser.class,Id);

    }

    /**
     * 交易记录
     * @param userId
     * @return
     */
    public List<BillRecordList> getRecordList(Long userId) {
        List<BillRecordList> list = new ArrayList<BillRecordList>();
        List<Object[]> objectList = new ArrayList<>();
        List<Object[]> surveyList = billDao.getSurveyRecord(userId);
        List<Object[]> withDrawList = billDao.getWithDrawRecord(userId);
        objectList.addAll(surveyList);
        objectList.addAll(withDrawList);
        CatsUtils.sortList(objectList);
        for(Object[] arr: objectList){
            BillRecordList billRecordList = new BillRecordList();
            billRecordList.setTitle(arr[0].toString());
            billRecordList.setMoney(Double.valueOf(arr[1].toString()));
            billRecordList.setTimeStr(TimeUtil.longToString(Long.valueOf(arr[2].toString()),TimeUtil.FORMAT_DATE));
            billRecordList.setStatus((Integer)arr[3]);
            list.add(billRecordList);
        }
        if(list!=null && list.size()>0){
            return list;
        }
        return null;
    }




}
