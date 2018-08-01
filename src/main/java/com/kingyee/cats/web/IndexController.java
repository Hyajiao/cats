package com.kingyee.cats.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 首页
 * 
 * @author peihong
 * 2017年05月10日
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
	private final static Logger logger = LoggerFactory
			.getLogger(IndexController.class);

    /**
     * 首页
     * @return
     */
    @RequestMapping("")
    public String index(){
        return "index";
    }

    /**
     * 提示页面
     * @return
     */
    @RequestMapping("warn")
    public String warn(ModelMap mm, String msg){
        mm.addAttribute("msg", msg);
        return "warn";
    }

    /**
     * 权限不足页面
     * @return
     */
    @RequestMapping("403")
    public String notFound(ModelMap mm, String msg){
        mm.addAttribute("msg", msg);
        return "403";
    }

    /**
     * 关注页面
     * @return
     */
    @RequestMapping("subscribe")
    public String subscribe(ModelMap mm, String msg){
        return "subscribe";
    }


    @RequestMapping(value = "redirect", method = RequestMethod.GET)
    public String redirect(ModelMap mm, @RequestParam("url") String url) throws Exception{
        mm.addAttribute("url", url);
        return "redirect";
    }


}
