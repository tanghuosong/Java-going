package com.rbac.shiro;

import com.rbac.model.Permission;
import com.rbac.model.Role;
import com.rbac.model.User;
import com.rbac.service.UserService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ths
 * @date: 2017/10/20
 * @time: 23:05
 * @description:  自定义验证
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 认证用户的操作
     * @param authenticationToken authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        // 从数据库中拿到用户信息
        User user = userService.getUserInfo(token.getUsername());
        // 如果用户不存在，则抛出不存在异常
        if (user==null){
            throw new UnknownAccountException("未知用户");
        }
        if(!user.getUserName().equals(token.getPrincipal())){
            throw new UnknownAccountException("用户名不正确");
        }
        return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),this.getName());
    }


    /***
     *  给用户授权
     * @param principalCollection 权限集合
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username =(String) super.getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据用户名获取当前用户
        User userInfo = userService.getUserInfo(username);
        //当用户存在的时候进入判断
        if (userInfo != null){

            List<String> roleStr = new ArrayList<>();
            List<String> permissions = new ArrayList<>();
            // 获取用户的角色
            for(Role role : userInfo.getRoles()){
                roleStr.add(role.getRoleName());
                for(Permission permission : role.getPermissions()){
                    permissions.add(permission.getName());
                }
            }
            //添加当前用户的角色
            info.addRoles(roleStr);
            //添加当前用户的权限
            info.addStringPermissions(permissions);
            return info;
        }
        return null;
    }

}
