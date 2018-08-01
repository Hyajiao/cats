package com.kingyee.fuxi.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能：跳转到后台管理的主界面
 * @author 周振平
 * @createTime May 24, 2011 9:51:58 AM
 */
@Controller
@RequestMapping(value = "/admin/security/")
public class IndexController {

	private static final long serialVersionUID = 7964557458390416167L;

    @RequestMapping(value = {"index"})
	public String index() {
		return "admin/security/index";
	}

}
