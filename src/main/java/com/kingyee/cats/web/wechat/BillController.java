package com.kingyee.cats.web.wechat;

import com.kingyee.cats.common.security.UserUtil;
import com.kingyee.cats.db.CmUser;
import com.kingyee.cats.service.wechat.BillService;
import com.kingyee.common.BaseController;
import com.kingyee.cats.bean.BillRecordList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 我的钱包
 * @author hanyajiao
 */


@Controller
@RequestMapping("/user/wallet/")
public class BillController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BillController.class);


    @Autowired
    private BillService wallService;

    /**
     * 交易记录列表
     * @return
     */
    @RequestMapping("recordList")
    public  String getRecordList(ModelMap modelMap){
        try {
            Long userId = UserUtil.getUserId();
            List<BillRecordList>  recordList = wallService.getRecordList(userId);
            CmUser user = wallService.getBill(userId);
            String balance = String.valueOf(user.getCuBalance()==null?0L:user.getCuBalance());
            modelMap.addAttribute("recordList",recordList);
            modelMap.addAttribute("balance",balance);
            return "userInfo/bills/wallet";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取记录列表出错！",e);
            return "error";
        }
    }
}
