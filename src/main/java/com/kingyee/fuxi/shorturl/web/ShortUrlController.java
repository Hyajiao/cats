package com.kingyee.fuxi.shorturl.web;

import com.kingyee.cats.db.CmShortUrl;
import com.kingyee.fuxi.shorturl.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * 
 * @author peihong
 * 2017年05月10日
 */
@Controller
@RequestMapping(value = "/s")
public class ShortUrlController {
	private final static Logger logger = LoggerFactory
			.getLogger(ShortUrlController.class);

	@Autowired
    private ShortUrlService service;

    /**
     * 短连接服务
     * @return
     */
    @RequestMapping("{code}")
    public String shortUrl(@PathVariable("code") String code){
        CmShortUrl shortUrl = service.getShortUrlByCode(code);
        if(shortUrl == null){
            return "404";
        }else{
            return "redirect:" + shortUrl.getCsuUrl();
        }
    }
}
