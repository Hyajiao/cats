package com.kingyee.cats.web.wechat;

import com.kingyee.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 指南
 * @author hanyajiao
 */

@Controller
@RequestMapping(value ="/guide/")
public class GuideController extends BaseController {

    private  final static Logger logger = LoggerFactory
            .getLogger(GuideController.class);

    /**
     * 指南列表
     * @param modelMap
     * @return
     */
    @RequestMapping("guideList")
    public String guideList(ModelMap modelMap){
        return "guide/guideList";
    }

    /**
     * 指南详情
     * @param modelMap
     * @return
     */
    @RequestMapping("guideDetail")
    public String guideDetail(ModelMap modelMap){
        return "guide/guideDetail";
    }

    /**
     * 联系我们
     * @param modelMap
     * @return
     */
    @RequestMapping("connect")
    public String relation(ModelMap modelMap){
        return "connect/connect";
    }

}
