package com.kingyee.fuxi.dic.dao;

import com.kingyee.fuxi.dic.bean.Dic;
import com.kingyee.fuxi.dic.db.CmDic;
import com.kingyee.common.db.CommonDao;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ph on 2016/7/29.
 */
@Repository
public class DicDao extends CommonDao {

    /**
     * 字典列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CmDic> list(SearchBean searchBean, POJOPageInfo<CmDic> pageInfo){
        Map<String, Object> parp = searchBean.getParps();
        StringBuilder hql = new StringBuilder();
        hql.append("FROM CmDic AS dic WHERE 1=1 ");
        if(parp.get("type") != null && StringUtils.isNotBlank(parp.get("type").toString())){
            hql.append("AND dic.cdType = :type ");
        }
        if(parp.get("key") != null){
            hql.append("AND (dic.cdKey LIKE :key OR dic.cdKey LIKE :key) ");
        }
        if(parp.get("value") != null){
            hql.append("AND (dic.cdValue LIKE :value OR dic.cdValue LIKE :value) ");
        }
        if(parp.get("state") != null){//状态
            hql.append("AND dic.cdIsValid = :state ");
        }

        Long count = dao.createCountQuery("SELECT COUNT(*) "+hql.toString(), parp);
        if (count == null || count.intValue() == 0) {
            pageInfo.setItems(new ArrayList<CmDic>());
            pageInfo.setCount(0);
            return pageInfo;
        }
        String order = "ORDER BY dic.cdSort, dic.cdCreateDate DESC";

        List<CmDic> list = new ArrayList<CmDic>();
        list = dao.createQuery(hql.toString()+order, parp, pageInfo.getStart(), pageInfo.getRowsPerPage());
        pageInfo.setCount(count.intValue());
        pageInfo.setItems(list);

        return pageInfo;
    }

    /**
     * 取得字典的类型
     * @return
     */
    public List<Dic> listType(){
        String hql = "SELECT cdType, cdDesc FROM CmDic GROUP BY cdType, cdDesc";
        List<Object[]> list = dao.createQuery(hql);
        Dic dic = null;
        List<Dic> result = new ArrayList<>();
        if(list != null && list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                dic = new Dic();
                Object[] objs = list.get(i);
                dic.setKey((String)objs[0]);
                dic.setValue((String)objs[1]);
                result.add(dic);
            }
        }
        return result;
    }

    /**
     * 根据key取得字典
     * @return
     */
    public CmDic getByKey(String type, String key){
        Map<String, String> parp = new HashMap<>();
        parp.put("type", type);
        parp.put("key", key);
        String hql = "FROM CmDic WHERE cdType =:type AND cdKey =:key";
        List<CmDic> list = dao.createQuery(hql, parp);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }


    /**
     * 查询第几条-->第几条记录
     * @param hql
     * @param offset
     * @param maxRow
     * @return
     */
    public <E> List<E> createQuery(final String hql, final Map parp, final int offset, final int maxRow) { //添加
        return dao.createQuery(hql, parp, offset, maxRow);
    }

    /**
     * 查询总记录数
     * @param query
     * @return
     */
    public Long createCountQuery(final String query, final Map parp) {		//添加
        return dao.createCountQuery(query, parp);
    }

    /**
     * 带条件查询.
     *
     * @param query
     * @param parp
     * @return List
     */
    public <E> List<E> createQuery(final String query, final Map parp) {
        return dao.createQuery(query, parp);
    }

    /**
     * 不带.
     *
     * @param query
     * @return List
     */
    public <E> List<E> createQuery(final String query) {
        return dao.createQuery(query);
    }
	
}
