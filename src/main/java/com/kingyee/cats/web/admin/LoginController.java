package com.kingyee.cats.web.admin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kingyee.common.BaseController;
import com.kingyee.common.util.encrypt.AesUtil;
import com.kingyee.common.util.encrypt.EncryptUtil;
import com.kingyee.cats.common.security.AdminUserModel;
import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.db.CmAdminUser;
import com.kingyee.cats.service.admin.AdminSysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;


/**
 * 后台登录
 * 
 * @author fanyongqian
 * 2016年9月7日
 */
@Controller
@RequestMapping(value = "/admin/")
public class LoginController extends BaseController {
	
	private final static Logger logger = LoggerFactory
			.getLogger(LoginController.class);

    @Autowired
    protected AdminSysUserService logic;

    @RequestMapping(value = {"loginInit"})
    public String loginInit(ModelMap mm) {
        return "admin/login";
    }

	@RequestMapping(value = {"login"})
	public String index(ModelMap mm, CmAdminUser user, HttpServletRequest request) {
		if(StringUtils.isEmpty(user.getAuUserName())){
			mm.addAttribute("msg", "用户名不能为空！");
			return "admin/login";
		}
		if(StringUtils.isEmpty(user.getAuPassword())){
			mm.addAttribute("msg", "密码不能为空！");			
			return "admin/login";
		}
		try {

            String encodePassword = EncryptUtil.getSHA256Value(user.getAuPassword());
            UsernamePasswordToken token = new UsernamePasswordToken(user.getAuUserName(), encodePassword);
            SecurityUtils.getSubject().login(token);

            CmAdminUser db = logic.getUserByNameAndPassword(user.getAuUserName());
            AdminUserModel um = new AdminUserModel();
            um.setId(db.getAuId());
            um.setName(db.getAuUserName());
            um.setShowName(db.getAuShowName());
            um.setUser(db);

            HttpSession session = request.getSession(true);
            if (session.getAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION) != null) {
                session.removeAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION);
            }
            session.setAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION, um);

            mm.addAttribute("user", user);
            if(db.getAuRole() == 2){
				return "redirect:ent/index";
			}
            return "redirect:index";
        }catch (AuthenticationException e) {
            mm.addAttribute("msg", "用户名或密码错误！");
            return "admin/login";
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);				
			mm.addAttribute("msg", "登录过程出错，请重试！");
				return "admin/login";
		}
	}	
	
	@RequestMapping(value = {"logout"})
	public String exit(HttpServletRequest request){
        if (SecurityUtils.getSubject().isAuthenticated()) {
            SecurityUtils.getSubject().logout();
        }

		HttpSession session = request.getSession(true);
		session.removeAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION);
		
		return "admin/login";
	}
	
	
	@RequestMapping(value = {"index", ""})
	public String index(ModelMap mm, HttpServletRequest request){

        mm.addAttribute("user", AdminUserUtil.getLoginUser());
		return "admin/index";
	}


	/**
	 * 强制登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"force-login"}, method = RequestMethod.GET)
	public String forceLogin(String info, HttpServletRequest request){
		String key = "8eHuQRBtPyNssYkl";
		if(StringUtils.isEmpty(info)){
			logger.info("=========强制登录参数为空===========");
			return "admin/login";
		}else{
			try {
				//AesUtil.urlDecoder(info, key);
				info = AesUtil.decode128(info, key);
				JsonParser parser = new JsonParser();
				JsonObject jsonObject = (JsonObject) parser.parse(info);
				if(!jsonObject.has("timestamp") || !isValidTime(jsonObject.get("timestamp").getAsLong())){
					logger.info("=======invalid time!=========");
					return "admin/login";
				}else if(!jsonObject.has("username") || !"nurse".equals(jsonObject.get("username").getAsString())){
					logger.info("=======invalide username======");
					return "admin/login";
				}else{
					HttpSession session = request.getSession(true);
					if(session.getAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION) != null){
						session.removeAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION);
					}
					CmAdminUser db = logic.getUserByNameAndPassword("nurse");
					AdminUserModel um = new AdminUserModel();
					um.setId(db.getAuId());
					um.setName(db.getAuUserName());
					um.setShowName(db.getAuShowName());
					um.setUser(db);

					session.setAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION, um);

				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("强制登录出错", e);
				return "admin/login";
			}
		}
		return "redirect:index";
	}

	/**
	 * 5分钟内有效
	 * @param time UNIX时间戳
	 * @return
	 */
	private Boolean isValidTime(Long time){
		Long inputTime = time * 1000;
		Long now = System.currentTimeMillis();
		//5分钟有效
		Long duration = 5 * 60 * 1000L;
		if(inputTime <= now && inputTime >= now - duration){
			return true;
		}
		return false;
	}
}
