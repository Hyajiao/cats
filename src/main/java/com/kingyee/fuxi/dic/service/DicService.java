package com.kingyee.fuxi.dic.service;

import com.kingyee.fuxi.dic.bean.Dic;
import com.kingyee.fuxi.dic.dao.DicDao;
import com.kingyee.fuxi.dic.db.CmDic;
import com.kingyee.common.IBaseService;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.cats.common.security.AdminUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * 
 * @author fanyongqian
 * 2016年10月31日
 */
@Service
public class DicService implements IBaseService{
	
    @Autowired
    private DicDao dao;

    /**
     * 字典列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CmDic> list(SearchBean searchBean, POJOPageInfo<CmDic> pageInfo){
    	return dao.list(searchBean, pageInfo);
    }
    

    /**
     * 保存字典
     * @param dic
     * @return
     */
    public Long save(CmDic dic){
        dic.setCdIsValid(1);
        dic.setCdDesc(dic.getCdType());
        dic.setCdCreateUserId(AdminUserUtil.getUserId());
        dic.setCdCreateUserName(AdminUserUtil.getShowName());
        dic.setCdCreateDate(new Date().getTime());
    	return dao.save(dic);
    }

    /**
     * 取得字典的类型
     * @return
     */
    public List<Dic> listType(){
        return dao.listType();
    }

    
    /**
     * 根据id获取字典信息
     * @param id
     * @return
     */
    public CmDic getById(Long id){
    	return dao.get(CmDic.class, id);
    }

    /**
     * 根据key获取字典信息
     * @param type 类型
     * @param key key
     * @return
     */
    public CmDic getByKey(String type, String key){
        return dao.getByKey(type, key);
    }

    /**
     * 更新字典信息
     * @param dic
     */
    public void edit(CmDic dic){
    	CmDic db = getById(dic.getCdId());
    	db.setCdType(dic.getCdType());
    	db.setCdDesc(dic.getCdType());
    	db.setCdKey(dic.getCdKey());
    	db.setCdValue(dic.getCdValue());
    	db.setCdMemo(dic.getCdMemo());
    	
    	db.setCdUpdateUserId(AdminUserUtil.getUserId());
    	db.setCdUpdateUserName(AdminUserUtil.getShowName());
    	db.setCdUpdateDate(new Date().getTime());
    	
    	dao.update(db);    
    }
    
    /**
     * 更新信息--删除时使用
     * @param table
     */
    public void update(Object table) { 
    	
		dao.update(table);
	}  
    
    /**
     * 查看key是否已经存在
     * @param name
     * @return
     */
    public boolean checkKey(String name,Long id,String type){
    	StringBuilder hql = new StringBuilder(); 
    	Map<String, Object> parp = new HashMap<String,Object>();
		parp.put("cdKey", name);
		parp.put("cdType", type);
	    hql.append("FROM CmDic c WHERE c.cdKey = :cdKey AND c.cdType = :cdType");
	    List<CmDic> cs = dao.createQuery(hql.toString(),parp);
		if(cs != null && cs.size() > 0){
			if(id != null){//编辑时
		    	if(cs.get(0).getCdId().equals(id)){//一切判断条件都正常时，只会搜索出一条数据
		    		return false;//此种情况代表编辑时，没有修改key
		    	}else{
		    		return true;//此种情况代表编辑时，修改了key，但是此key别的记录已经占用了。
		    	}
		    }else{//新增时
				//key已存在
				return true;
		    }
		}else{
			return false;
		}		
    	
    }
    
    /**
     * 查看value是否已经存在
     * @param name
     * @return
     */
    public boolean checkValue(String name,Long id,String type){
    	StringBuilder hql = new StringBuilder(); 
    	Map<String, Object> parp = new HashMap<String,Object>();
		parp.put("cdValue", name);
		parp.put("cdType", type);
	    hql.append("FROM CmDic c WHERE c.cdValue = :cdValue AND c.cdType = :cdType");
	    List<CmDic> cs = dao.createQuery(hql.toString(),parp);
		if(cs != null && cs.size() > 0){
			if(id != null){//编辑时
		    	if(cs.get(0).getCdId().equals(id)){//一切判断条件都正常时，只会搜索出一条数据
		    		return false;//此种情况代表编辑时，没有修改value
		    	}else{
		    		return true;//此种情况代表编辑时，修改了value，但是此value别的字典记录已经占用了。
		    	}
		    }else{//新增时
				//value已存在
				return true;
		    }
		}else{
			return false;
		}		
    	
    }
}
