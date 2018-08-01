package com.kingyee.cats.handler;

import com.kingyee.cats.service.UserService;
import com.kingyee.cats.service.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户扫描带参数二维码事件：用户已关注时的事件推送Handler
 * <p>
 */
@Component
public class ScanSubscribeHandler extends AbstractHandler {

    @Autowired
    protected WxMpConfigStorage configStorage;
    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    protected WechatService coreService;
    @Autowired
    protected UserService userService;


    //用户已关注时 扫带参二维码
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        try {
            WxMpUser wxMpUser = coreService.getUserInfo(wxMessage.getFromUser(), "zh_CN");
            String messageContent = "";
            // 扫描带参数二维码事件：用户已关注时，进行关注后的事件推送
            String qrscene = wxMessage.getEventKey();
            messageContent = "敬请期待~";
            WxMpXmlOutTextMessage message = WxMpXmlOutMessage.TEXT()
                    //首行空两个中文字符，使用中文全角空格两个
                    .content(messageContent)
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .build();
            return message;

        } catch (Exception e) {
            logger.error("scan_push：扫码推事件的事件推送处理时，出现错误", e);
        }
        return null;
    }

};
