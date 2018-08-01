package com.kingyee.fuxi.security.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.fuxi.security.db.AuthModule;
import com.kingyee.fuxi.security.db.AuthResource;
import com.kingyee.fuxi.security.service.ModuleService;
import com.kingyee.fuxi.security.service.RoleResourceServcie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：角色资源管理Action
 * @author 周振平
 * @createTime May 4, 2011 1:51:58 PM
 */
@Controller
@RequestMapping(value = "/admin/security/role/res/")
public class RoleResourceController {
    private final static Logger logger = LoggerFactory
            .getLogger(ModuleController.class);
	@Autowired
	private RoleResourceServcie roleResourceService;
    @Autowired
	private ModuleService moduleService;

	/**
	 * 角色资源列表
	 * @return
	 */
	@RequestMapping("list")
    @ResponseBody
	public JsonElement list() {
		try {

			//权限模块list
			List<AuthModule> moduleList = moduleService.getModuleList();
			Map<AuthModule,List<AuthResource>> map = new HashMap<AuthModule,List<AuthResource>>();
			List<AuthResource> resList = null;
			for(AuthModule mod : moduleList) {
				resList = roleResourceService.getResourceList(mod);
				map.put(mod, resList);
			}

			JsonArray datas = new JsonArray();
			JsonObject obj = null;
            JsonObject obj1 = null;
			for(AuthModule mod : moduleList) {
				obj = new JsonObject();
				obj.addProperty("moduleId", mod.getAmId());
				obj.addProperty("moduleName", mod.getAmName());
				resList = map.get(mod);
                JsonArray arr = new JsonArray();
				for(AuthResource ar : resList) {
					obj1 = new JsonObject();
					obj1.addProperty("boxLabel", ar.getArName());
//					obj1.put("boxLabel", "<span qtip='" + ar.getArDescription() + "'>" + ar.getArName() + "</span>");
					obj1.addProperty("name", "checkRes"+mod.getAmId());
					obj1.addProperty("inputValue", ar.getArId());
					arr.add(obj1);
				}
				obj.add("resList", arr);
				datas.add(obj);
			}
            JsonObject result = new JsonObject();
			result.add("datas", datas);
            return JsonWrapper.newDataInstance(result);
		} catch (Exception e) {
            logger.error("出错了", e);
            return JsonWrapper.newErrorInstance("出错了，稍后再试。");
		}
	}


}