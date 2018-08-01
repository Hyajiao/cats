package com.kingyee.fuxi.shorturl.service;

import com.kingyee.cats.db.CmShortUrl;
import com.kingyee.common.IBaseService;
import com.kingyee.common.util.ShortUrlUtil;
import com.kingyee.fuxi.shorturl.dao.ShortUrlDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目管理
 * 
 */
@Service
public class ShortUrlService implements IBaseService {

    private final static Logger logger = LoggerFactory.getLogger(ShortUrlService.class);

    @Autowired
    protected ShortUrlDao dao;


    /**
     * 保存短连接
     * @param url
     * @return
     */
    public CmShortUrl saveShortUrl(String url){

        CmShortUrl shortUrl = dao.getShortUrl(url);
        if(shortUrl == null){
            shortUrl = new CmShortUrl();
            shortUrl.setCsuUrl(url);
            shortUrl.setCsuHitCount(0);
            shortUrl.setCsuCreateTime(System.currentTimeMillis());
            Long id = dao.save(shortUrl);

            String code = ShortUrlUtil.getShortUrl(id);
            shortUrl.setCsuCode(code);
            dao.update(shortUrl);
        }

        return shortUrl;
    }

    /**
     * 根据code取得短连接记录
     * @return
     */
    public CmShortUrl getShortUrlByCode(String code){
        return dao.getShortUrlByCode(code);
    }

}
