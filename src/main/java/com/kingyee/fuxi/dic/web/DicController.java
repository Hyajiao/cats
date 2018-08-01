package com.kingyee.fuxi.dic.web;

import com.google.gson.JsonElement;
import com.kingyee.fuxi.dic.bean.Dic;
import com.kingyee.fuxi.dic.db.CmDic;
import com.kingyee.fuxi.dic.service.DicService;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 字典管理
 * 
 * @author peihong
 * 2017年01月05日
 */
@Controller
@RequestMapping(value = "/cm/dic")
public class DicController {
	private final static Logger logger = LoggerFactory.getLogger(DicController.class);

    @Autowired
    private DicService logic;
    
    /**
     * 字典列表
     * @param mm
     * @param key
     * @param page
     * @param rowsPerPage
     * @return
     */
    @RequestMapping("list")
    public String list(ModelMap mm, String type, String key, String value, Integer state, Integer page, Integer rowsPerPage){
    	if (page == null || page.intValue() == 0) {
			page = 1;
		}
		if (rowsPerPage == null || rowsPerPage.intValue() == 0) {
			rowsPerPage = 20;
		}
		POJOPageInfo pageInfo = new POJOPageInfo<CmDic>(rowsPerPage , page);
		try {
			SearchBean searchBean = new SearchBean();
            searchBean.addParpField("type", type);
			if (StringUtils.isNotEmpty(key)) {
				searchBean.addParpField("key", "%" + key.trim() + "%");
			}
            if (StringUtils.isNotEmpty(value)) {
                searchBean.addParpField("value", "%" + value.trim() + "%");
            }
			searchBean.addParpField("state", state);
			pageInfo = logic.list(searchBean, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取字典列表信息出错。"+ e );
			return "error";
		}
		
		List<Dic> typeList =  logic.listType();
	    mm.addAttribute("typeList", typeList);
	
		mm.addAttribute("pageInfo",pageInfo);
    	mm.addAttribute("type", type);
    	mm.addAttribute("key", key);
    	mm.addAttribute("value", value);
        mm.addAttribute("state", state);
        mm.addAttribute("page",page);
    	mm.addAttribute("rowsPerPage",rowsPerPage);
    	    	
    	return "cm/dic/dic_list";
    }
    
    /**
     * 添加字典界面
     * @return
     */
    @RequestMapping("addInit")
    public String addInit(ModelMap mm){
        List<Dic> typeList =  logic.listType();
        mm.addAttribute("typeList", typeList);
        //mm.addAttribute("selectId", "");
    	return "cm/dic/dic_add";
    }
    
    /**
     * 保存字典
     * @param dic
     * @return
     */
    @RequestMapping("add")
    public String add(CmDic dic){
    	try{
        	logic.save(dic);
    	}catch(Exception e){
			logger.error("保存字典时出错。"+ e );
    		return "error";
    	}
    	return "redirect:list";
    }

    /**
     * 编辑字典页面
     * @param mm
     * @param id
     * @return
     */
    @RequestMapping("editInit")
    public String editInit(ModelMap mm,Long id){
    	try{   	    		 
    		CmDic dic = logic.getById(id);
        	mm.addAttribute("dic",dic);  
        	//字典类型
        	List<Dic> typeList =  logic.listType();
        	mm.addAttribute("typeList", typeList);
   	     	//mm.addAttribute("selectId", dic.getCdType());
    	}catch(Exception e){
    		e.printStackTrace();
			logger.error("获取字典信息时出错。"+ e );
    		return "error";
    	}
    	
    	return "cm/dic/dic_edit";
    }

    /**
     * 编辑字典页面
     * @param dic
     * @return
     */
    @RequestMapping("edit")
    public String edit(CmDic dic){
    	try{
    		logic.edit(dic);
    	}catch(Exception e){
    		e.printStackTrace();
			logger.error("编辑字典信息时出错。"+ e );
    		return "error";
    	}
    	return "redirect:list";
    }

    /**
     * 删除、还原字典
     * @param id
     * @return
     */
    @RequestMapping("reset")
    public String reset(Long id){
    	try{
    		CmDic dic = logic.getById(id);    		
    		if(dic.getCdIsValid() == 0){
    			dic.setCdIsValid(1);	    			    			
    		}else{
    			dic.setCdIsValid(0);    			
    		}
        	logic.update(dic);        	
    	}catch(Exception e){
    		e.printStackTrace();
			logger.error("删除/复原字典信息时出错。"+ e );
    		return "error";
    	}
    	
    	return "redirect:list";
    }    


    /**
     * 查看key是否已经存在
     * @param ajaxName
     * @return
     */
    @ResponseBody
	@RequestMapping(value = { "checkKey" }, method = RequestMethod.POST)
    public JsonElement checkKey(String ajaxName,Long id,String type){
    	if(logic.checkKey(ajaxName,id,type)){//已存在
    		return JsonWrapper.newSuccessInstance();
    	}else{
    		return JsonWrapper.newErrorInstance("key值已存在");
    	}
    }
    
    /**
     * 查看value是否已经存在
     * @param ajaxName
     * @return
     */
    @ResponseBody
	@RequestMapping(value = { "checkValue" }, method = RequestMethod.POST)
    public JsonElement checkValue(String ajaxName,Long id,String type){
    	if(logic.checkValue(ajaxName,id,type)){//已存在
    		return JsonWrapper.newSuccessInstance();
    	}else{
    		return JsonWrapper.newErrorInstance("value已存在");
    	}
    }
       

}
