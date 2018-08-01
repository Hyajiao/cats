package com.kingyee.cats.handler;

import com.kingyee.cats.service.UserService;
import com.kingyee.cats.service.WechatService;
import com.kingyee.common.spring.mvc.WebUtil;
import com.kingyee.common.util.PropertyConst;
import com.kingyee.common.util.http.HttpUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 用户关注公众号Handler/用户扫描带参数的二维码关注公众号
 * <p>
 */
@Component
public class SubscribeHandler extends AbstractHandler {

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
        try {
            WxMpUser wxMpUser = wechatService.getUserInfo(wxMessage.getFromUser(), "zh_CN");
            // 更新用户
            userService.saveOrUpdateWechatUser(wxMpUser);

            String backUrl = HttpUtil.getBasePath(WebUtil.getRequest()) + "user/reg";
            String loginUrl = PropertyConst.MEDLIVE_URL + "/auth/login?service=" + URLEncoder.encode(backUrl, "UTF-8");
            String messageContent = "请绑定医脉通账号，参与问卷调研，<a href ='"+loginUrl+"'>点此绑定</a>!";
//            if(StringUtils.isNotEmpty(wxMessage.getEventKey())){
//                // 扫描带参数二维码事件：用户未关注时，进行关注后的事件推送
//                String eventKey = wxMessage.getEventKey();
//                // qrscene_123123
//                String qrscene = eventKey.substring(8);
//                //扫描了其他待处理二维码
//                messageContent ="请绑定医脉通账号，参与问卷调研，<a href ='"+loginUrl+"'>点此绑定</a>!";
//            }
            WxMpXmlOutMessage.TEXT();
            WxMpXmlOutTextMessage message = WxMpXmlOutMessage.TEXT()
                    .content(messageContent)
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .build();
            return message;

        } catch (Exception e) {
            logger.error("关注时，保存用户信息失败。", e);
        }
        return null;
    }
}
