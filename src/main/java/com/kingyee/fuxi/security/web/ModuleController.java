package com.kingyee.fuxi.security.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingyee.common.BaseController;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.fuxi.security.db.AuthModule;
import com.kingyee.fuxi.security.service.ModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 模块管理
 *
 * @author 裴宏
 * 2018年02月09日
 */
@Controller
@RequestMapping(value = "/admin/security/module/")
public class ModuleController extends BaseController {
	private final static Logger logger = LoggerFactory
			.getLogger(ModuleController.class);

    @Autowired
    private ModuleService moduleLogic;

    @RequestMapping("list")
    @ResponseBody
    public JsonElement list() {
        try {
            List<AuthModule> moduleList = moduleLogic.getModuleList();
            if(moduleList != null) {
                JsonObject obj = null;
                JsonArray datas = new JsonArray();
                for(AuthModule mod : moduleList) {
                    obj = new JsonObject();

                    obj.addProperty("moduleId", mod.getAmId().toString());
                    obj.addProperty("moduleName", mod.getAmName());
                    datas.add(obj);
                }
                JsonObject result = new JsonObject();
                result.add("modules", datas);
                return JsonWrapper.newDataInstance(result);
            }
        } catch (Exception e) {
            logger.error("取得模块列表出错。", e);
            return JsonWrapper.newErrorInstance("取得模块列表出错。");
        }
        return JsonWrapper.newSuccessInstance();
    }
}
