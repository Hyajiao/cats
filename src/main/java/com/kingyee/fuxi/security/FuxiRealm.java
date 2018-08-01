package com.kingyee.fuxi.security;

import com.kingyee.cats.db.CmAdminUser;
import com.kingyee.cats.service.admin.AdminSysUserService;
import com.kingyee.fuxi.security.service.SecurityService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class FuxiRealm extends AuthorizingRealm {

    @Autowired
    private AdminSysUserService adminSysUserService;
    @Autowired
    private SecurityService securityService;

    /**
     * 获取一个全局唯一的 Realm 名称，可以自定义，最好是不容易重复的
     */
    @Override
    public String getName(){
        return this.getClass().toString();
    }

    /**
     * 权限验证的方法 - 获取用户的授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = principals.getPrimaryPrincipal().toString();
        Set<String> roleNameSet = securityService.findRoleNameByUsername(username);
        Set<String> permissionNameSet = securityService.findPermissionNameByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roleNameSet); // 将角色名集合加入验证信息
        simpleAuthorizationInfo.setStringPermissions(permissionNameSet); // 权限名加入验证信息
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证的方法 - 获取用户的认证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        // 转型
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername(); // 获取 用户名
        // 获取 密码，字符数组需要转型为 String
        String password = new String(upToken.getPassword());
        // 以下是登录认证的逻辑
        CmAdminUser user = adminSysUserService.getUserByNameAndPassword(username, password);
        if (user != null){
            // 身份认证成功，返回 SimpleAuthenticationInfo 对象
            return new SimpleAuthenticationInfo(
                    user.getAuUserName(), // 参数1：用户名
                    user.getAuPassword(), // 参数2：密码
                    this.getName() // 参数3：当前 Realm 的名称
            );
        } else {
            // 身份认证失败
            throw new AuthenticationException("用户名或密码错误！");
        }
    }
}
