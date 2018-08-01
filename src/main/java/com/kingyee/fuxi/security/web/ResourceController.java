package com.kingyee.fuxi.security.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingyee.cats.db.CmWechatUser;
import com.kingyee.common.Const;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.ExtPageInfo;
import com.kingyee.fuxi.security.AuthUtil;
import com.kingyee.fuxi.security.db.AuthResource;
import com.kingyee.fuxi.security.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：权限资源管理
 * @author 周振平
 * @author ph
 * @CreateTime 2011-5-30 下午04:34:08
 */
@Controller
@RequestMapping(value = "/admin/security/resource/")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;


	/**
	 * 保存权限资源
	 */
    @RequestMapping("save")
    @ResponseBody
	public JsonElement save(@RequestBody JsonObject data) {
		try {
            if(data == null) {
                return JsonWrapper.newErrorInstance("请从正确入口进入！");
            }

            AuthResource res = null;
            if(StringUtils.isNotEmpty(data.get("arId").getAsString())) {
                res = resourceService.getResource(Long.valueOf(data.get("arId").getAsString()));
            } else {
                res = new AuthResource();
                res.setArModule(data.get("arModule").getAsLong());
                res.setArPermission(data.get("arPermission").getAsString());
                //判断权限代码在当前模块下是否唯一
                boolean isExist = resourceService.checkUniqueName(data.get("arPermission").getAsString());
                if(isExist) {
                    return JsonWrapper.newErrorInstance("该权限标示已存在！");
                }
            }
            res.setArName(data.get("arName").getAsString());
            res.setArUrl(data.get("arUrl").getAsString());
            res.setArDescription(data.get("arDescription").getAsString());

            Long resId = resourceService.saveOrUpdateRole(res);
            JsonObject obj = new JsonObject();
            obj.addProperty("arId", resId);
            return JsonWrapper.newDataInstance(obj);
        } catch (Exception e) {
            return JsonWrapper.newErrorInstance(e.getMessage());
        }


	}

	/**
	 * 加载权限资源
	 * @return
	 */
    @RequestMapping("load")
    @ResponseBody
	public JsonElement load(Long resId) {
		try {
			if(resId == null) {
                return JsonWrapper.newErrorInstance("请从正确入口进入！");
			}
			AuthResource res = resourceService.getResource(resId);
			JsonObject result = new JsonObject();
			result.addProperty("arId", res.getArId());
			result.addProperty("arModule", res.getArModule().toString());
			result.addProperty("arName", res.getArName());
			result.addProperty("arPermission", res.getArPermission());
			result.addProperty("arUrl", res.getArUrl());
			result.addProperty("arDescription", res.getArDescription());

            return JsonWrapper.newDataInstance(result);
		} catch (Exception e) {
            return JsonWrapper.newErrorInstance("加载权限资源出错");
		}
	}

	/**
	 * 权限资源列表
	 * @return
	 */
    @RequestMapping("list")
    @ResponseBody
	public JsonElement list(Integer start, Integer limit, Long resourceModule, String resourceName, String resourceUniqueName, String resourceUrl) {
		try {
            JsonObject result = new JsonObject();
            if (start == null || start.intValue() == 0) {
                start = 0;
            }
            if (limit == null || limit.intValue() == 0) {
                limit = Const.ROWSPERPAGE;
            }
            ExtPageInfo pageInfo = new ExtPageInfo<CmWechatUser>(start, limit);
			Map<String, Object> parp = new HashMap<String, Object>();
			if(resourceModule != null) {
				parp.put("arModule", resourceModule);
			}
			if(StringUtils.isNotEmpty(resourceName.trim())) {
				parp.put("arName", "%" + resourceName.trim() + "%");
			}
			if(StringUtils.isNotEmpty(resourceUniqueName.trim())) {
				parp.put("arPermission", "%" + resourceUniqueName.trim() + "%");
			}
			if(StringUtils.isNotEmpty(resourceUrl.trim())) {
				parp.put("arUrl", "%" + resourceUrl.trim() + "%");
			}
            List<Object> resultList = resourceService.getResList(pageInfo, parp);
			if(resultList != null) {
				List<Object[]> roleList = (List<Object[]>)resultList.get(0);
				long totalRow = (Long)resultList.get(1);
				JsonObject obj = null;
				JsonArray datas = new JsonArray();

				for(Object[] res : roleList) {
					obj = new JsonObject();
					obj.addProperty("arId", String.valueOf(res[0]));
					obj.addProperty("arName", String.valueOf(res[2]));
					obj.addProperty("arUrl", String.valueOf(res[3]));
					obj.addProperty("amName", String.valueOf(res[4]));
					obj.addProperty("arPermission", String.valueOf(res[5]));
					obj.addProperty("arDescription", String.valueOf(res[6]));
					datas.add(obj);
				}
				result.addProperty("totalCount", totalRow);
				result.add("datas", datas);
                return JsonWrapper.newDataInstance(result);
			}else{
                result.addProperty("totalCount", 0);
                result.add("datas", new JsonArray());
                return JsonWrapper.newDataInstance(result);
            }
		} catch (Exception e) {
            return JsonWrapper.newErrorInstance("权限资源列表出错");
		}

	}

	/**
	 * 删除权限资源
	 * @return
	 */
    @RequestMapping("delete")
    @ResponseBody
	public JsonElement delete(Long resId) {
		try {
			if(resId == null) {
                return JsonWrapper.newErrorInstance("请从正确入口进入！");
			}
			boolean result = resourceService.delRes(resId);
			if(result) {
                return JsonWrapper.newSuccessInstance();
			} else {
                return JsonWrapper.newErrorInstance("该权限资源已被相关角色使用，不能删除！");
			}

		} catch (NumberFormatException e) {
            return JsonWrapper.newErrorInstance("删除权限资源出错");
		} catch (Exception e) {
            return JsonWrapper.newErrorInstance("删除权限资源出错");
		}
	}


	/**
	 * 检查该权限标示是否唯一
	 * @return
	 */
    @RequestMapping("checkUniqueName")
    @ResponseBody
	public JsonElement checkUniqueName(String arPermission) {
        JsonElement json = null;
		try {
			if(arPermission == null) {
                return JsonWrapper.newErrorInstance("权限标示为空！");
			}
			boolean result = resourceService.checkUniqueName(arPermission);
            json = JsonWrapper.newDataInstance(result);
		} catch (Exception e) {
            json = JsonWrapper.newErrorInstance(e.getMessage());
		}
		return json;
	}


    /**
     * 刷入资源到内存
     * @return
     */
    @RequestMapping("flush")
    @ResponseBody
    public JsonElement flush() {
        try {
            List<AuthResource> arList = resourceService.listAuthResource();
            if(arList != null){
                // 初始化 资源hashMap
                AuthUtil.initHashMap(arList);
            }
            return JsonWrapper.newSuccessInstance();
        } catch (NumberFormatException e) {
            return JsonWrapper.newErrorInstance("刷入权限资源出错");
        } catch (Exception e) {
            return JsonWrapper.newErrorInstance("刷入权限资源出错");
        }
    }
}
