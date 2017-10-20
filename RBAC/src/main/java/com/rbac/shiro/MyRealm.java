package com.rbac.shiro;

import com.rbac.model.User;
import com.rbac.service.UserService;
import com.rbac.utils.Encrypt;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


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
//        String password = Encrypt.createSalt(user.getPassword());
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword().toCharArray(),
                ByteSource.Util.bytes(user.getSalt()),this.getName());

        return authcInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username =(String) super.getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
        //根据用户名获取当前用户
        User userInfo = userService.getUserInfo(username);
        //当用户存在的时候进入判断
        if (userInfo!=null){
            //添加当前用户的角色
            info.addRoles(userInfo.getRoleList());
            //添加当前用户的权限
            info.addStringPermissions(userInfo.getPermissionList());
            return info;
        }
        return null;
    }

}
