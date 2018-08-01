package com.kingyee.fuxi.security.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingyee.common.BaseController;
import com.kingyee.common.Const;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.ExtPageInfo;
import com.kingyee.fuxi.security.db.AuthModule;
import com.kingyee.fuxi.security.db.AuthRole;
import com.kingyee.fuxi.security.db.AuthRoleRes;
import com.kingyee.fuxi.security.service.ModuleService;
import com.kingyee.fuxi.security.service.RoleResourceServcie;
import com.kingyee.fuxi.security.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色管理
 *
 * @author 裴宏
 * 2018年02月09日
 */
@Controller
@RequestMapping(value = "/admin/security/role/")
public class RoleController extends BaseController {
    private final static Logger logger = LoggerFactory
            .getLogger(RoleController.class);

    @Autowired
    private RoleService service;
    @Autowired
    private RoleResourceServcie roleResourceServcie;
    @Autowired
    private ModuleService moduleService;

    /**
     * 新增角色
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public JsonElement add(@RequestBody JsonObject data) {
        JsonElement json;
        try {
            if (data == null) {
                return JsonWrapper.newErrorInstance("请从正确入口进入！");
            }
            AuthRole role = null;
            if (StringUtils.isNotEmpty(data.get("id").getAsString())) {
                role = roleResourceServcie.getRole(Long.valueOf(data.get("id").getAsString()));
            } else {
                role = new AuthRole();
            }
            role.setAroName(data.get("aroName").getAsString());

            Long rId = roleResourceServcie.saveOrUpdateRole(role);
            roleResourceServcie.delRoleRes(rId);

            //权限模块list
            List<AuthModule> moduleList = moduleService.getModuleList();
            for (AuthModule mod : moduleList) {
                Long modId = mod.getAmId();
                if (data.has("checkRes" + modId)) {

                    StringBuilder arrRes = new StringBuilder();
                    //当该模块选择一个资源时，取出对象不是JsonArray对象，所以用getLong直接获取值
                    if (data.get("checkRes" + mod.getAmId()) instanceof JsonArray) {
                        JsonArray checked = data.get("checkRes" + modId).getAsJsonArray();
                        for (int i = 0; i < checked.size(); i++) {
                            AuthRoleRes arr = new AuthRoleRes();
                            arr.setArrModule(modId);
                            arr.setArrRoleId(rId);
                            arr.setArrRes(Long.valueOf(checked.get(i).getAsString()));
                            roleResourceServcie.saveOrUpdateRoleRes(arr);
                        }
                    } else {
                        Long checked = data.get("checkRes" + modId).getAsLong();
                        AuthRoleRes arr = new AuthRoleRes();
                        arr.setArrModule(modId);
                        arr.setArrRoleId(rId);
                        arr.setArrRes(checked);
                        roleResourceServcie.saveOrUpdateRoleRes(arr);
                    }
                }
            }
            JsonObject obj = new JsonObject();
            obj.addProperty("id", rId);
            json = JsonWrapper.newDataInstance(obj);
        } catch (Exception e) {
            logger.error("保存角色出错", e);
            json = JsonWrapper.newErrorInstance(e.getMessage());
        }
        return json;
    }

    /**
     * 修改角色
     *
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public JsonElement update(String aroId) {
        JsonElement json = null;
        try {
            if (aroId == null) {
                return JsonWrapper.newErrorInstance("请从正确入口进入！");
            }
            AuthRole role = roleResourceServcie.getRole(Long.valueOf(aroId));
            List<AuthRoleRes> arr = roleResourceServcie.getRoleResByRoleId(Long.valueOf(aroId));

            JsonArray datas = new JsonArray();
            if(arr != null && arr.size() > 0){
                JsonObject obj = null;
                Long preModuleId = arr.get(0).getArrModule();
                StringBuffer arrRes = new StringBuffer();
                for (AuthRoleRes rr : arr) {
                    if(!rr.getArrModule().equals(preModuleId)){
                        obj = new JsonObject();
                        obj.addProperty("moduleId", preModuleId);
                        obj.addProperty("arrRes", arrRes.substring(0, arrRes.length() - 1));
                        datas.add(obj);

                        arrRes.setLength(0);
                        preModuleId = rr.getArrModule();
                    }
                    arrRes.append(rr.getArrRes());
                    arrRes.append(",");
                }

                obj = new JsonObject();
                obj.addProperty("moduleId", preModuleId);
                obj.addProperty("arrRes", arrRes.substring(0, arrRes.length() - 1));
                datas.add(obj);
            }

            JsonObject result = new JsonObject();
            result.add("datas", datas);
            result.addProperty("aroName", role.getAroName());
            result.addProperty("id", role.getAroId());

            json = JsonWrapper.newDataInstance(result);
        } catch (NumberFormatException e) {
            json = JsonWrapper.newErrorInstance(e.getMessage());
        } catch (Exception e) {
            json = JsonWrapper.newErrorInstance(e.getMessage());
        }
        return json;
    }

    /**
     * 删除角色
     *
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public JsonElement delete(String aroId) {
        JsonElement json = null;
        try {
            if (aroId == null) {
                return JsonWrapper.newErrorInstance("请从正确入口进入！");
            }
            boolean result = roleResourceServcie.delRole(Long.valueOf(aroId));
            if (result) {
                json = JsonWrapper.newSuccessInstance();
            } else {
                json = JsonWrapper.newErrorInstance("该角色存在用户使用！");
            }

        } catch (NumberFormatException e) {
            return JsonWrapper.newErrorInstance(e.getMessage());
        } catch (Exception e) {
            return JsonWrapper.newErrorInstance(e.getMessage());
        }
        return json;
    }

    /**
     * 角色列表
     *
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public JsonElement list(Integer start, Integer limit) {
        JsonElement json = null;
        try {
            if (start == null || start.intValue() == 0) {
                start = 0;
            }
            if (limit == null || limit.intValue() == 0) {
                limit = Const.ROWSPERPAGE;
            }
            ExtPageInfo pageInfo = new ExtPageInfo<AuthRole>(start, limit);
            List<Object> resultList = roleResourceServcie.getRoleList(pageInfo);
            if (resultList != null) {
                List<AuthRole> roleList = (List<AuthRole>) resultList.get(0);
                long totalRow = (Long) resultList.get(1);
                JsonObject obj = null;
                JsonArray datas = new JsonArray();

                for (AuthRole role : roleList) {
                    obj = new JsonObject();
                    obj.addProperty("id", role.getAroId());
                    obj.addProperty("aroName", role.getAroName());
                    datas.add(obj);
                }
                JsonObject result = new JsonObject();
                result.addProperty("totalCount", totalRow);
                result.add("datas", datas);
                json = JsonWrapper.newDataInstance(result);
            }

        } catch (Exception e) {
            return JsonWrapper.newErrorInstance(e.getMessage());
        }
        return json;
    }
}
