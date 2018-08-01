package com.kingyee.cats.config;

import com.kingyee.common.util.PropertyConst;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 个性化菜单生成
 * Created by peihong.
 *
 *
 */
public class MenuConfig {

    /**
     * 定义菜单结构
     * @param type 0:游客;1:患者;2:医生
     *
     * @return
     */
    protected static WxMenu getMenu(WxMpService wxMpService, int type, Long tagid) {
        WxMenu menu = new WxMenu();
        String domain = PropertyConst.DOMAIN_URL;

        WxMenuButton button1 = new WxMenuButton();
        button1.setName("问卷中心");
        button1.setType(WxConsts.BUTTON_VIEW);
        button1.setUrl(domain+"/user/survey/surveyNew");


        WxMenuButton button2 = new WxMenuButton();
        button2.setName("操作指南");
        button2.setType(WxConsts.BUTTON_VIEW);
        button2.setUrl(domain+"/guide/guideList");


        WxMenuButton button3 = new WxMenuButton();
        button3.setName("会员中心");

        WxMenuButton button31 = new WxMenuButton();
        button31.setName("个人信息");
        button31.setType(WxConsts.BUTTON_VIEW);
        button31.setUrl(domain+"/user/userInfo/infoList");


        WxMenuButton button32 = new WxMenuButton();
        button32.setName("我的钱包");
        button32.setType(WxConsts.BUTTON_VIEW);
        button32.setUrl(domain+"/user/wallet/recordList");


        WxMenuButton button33 = new WxMenuButton();
        button33.setName("联系我们");
        button33.setType(WxConsts.BUTTON_VIEW);
        button33.setUrl(domain+"/guide/connect");


        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        menu.getButtons().add(button3);
        button3.getSubButtons().add(button31);
        button3.getSubButtons().add(button32);
        button3.getSubButtons().add(button33);
        return menu;
    }
    /**
     * 运行此main函数即可生成公众号自定义菜单
     *
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context
                = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});


        WxMpService wxMpService = context.getBean(WxMpService.class);
        try {
            // ==== 生成菜单 =====
            wxMpService.getMenuService().menuDelete();
            if(wxMpService.getMenuService().menuGet() == null){
                wxMpService.getMenuService().menuCreate(getMenu(wxMpService, 0, 0L));
            }

            System.out.println("目前菜单：" + wxMpService.getMenuService().menuGet().toJson());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
