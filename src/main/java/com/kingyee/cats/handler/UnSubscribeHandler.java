package com.kingyee.cats.handler;


import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.cats.service.UserService;
import com.kingyee.cats.service.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author songqian
 * @date 2017-07-13 9:40
 */
@Component
public class UnSubscribeHandler extends AbstractHandler{


    @Autowired
    protected WxMpConfigStorage configStorage;
    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    protected WechatService wechatService;
    @Autowired
    protected UserService userService;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        //更新用户信息
        try {
            CmWechatUser wechatUser = userService.getWechatUserByOpenid(wxMessage.getFromUser());
            if(wechatUser != null){
                wechatUser.setCwuSubscribe(0);
                userService.updateWechatUser(wechatUser);
                userService.removeCache();
            }
            WxMpXmlOutTextMessage message = null;
            return message;
        }
        catch (Exception e) {
            logger.error("取消关注时，更新用户信息失败。", e);
        }
        return null;
    }
}
