package com.kingyee.fuxi.shorturl.dao;

import com.kingyee.cats.db.CmShortUrl;
import com.kingyee.common.db.CommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ph on 2016/7/29.
 */
@Repository
public class ShortUrlDao extends CommonDao {


    /**
     * 根据url取得短连接记录
     * @return
     */
    public CmShortUrl getShortUrl(String url){
        Map<String, String> param = new HashMap<>(8);
        param.put("url", url);

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmShortUrl ");
        hql.append(" WHERE csuUrl =:url ");

        return dao.findOne(hql.toString(), param);
    }

    /**
     * 根据code取得短连接记录
     * @return
     */
    public CmShortUrl getShortUrlByCode(String code){
        Map<String, String> param = new HashMap<>(8);
        param.put("code", code);

        StringBuilder hql = new StringBuilder();
        hql.append(" FROM CmShortUrl ");
        hql.append(" WHERE csuCode =:code ");

        return dao.findOne(hql.toString(), param);
    }

}
