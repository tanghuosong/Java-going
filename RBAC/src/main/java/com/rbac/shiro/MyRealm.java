package com.rbac.shiro;

import com.rbac.model.User;
import com.rbac.service.UserService;
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

    /**
     * 认证用户的操作
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //从假数据源中拿当前用户数据
        User userDao = userService.getUserInfo(token.getUsername());
        if (userDao==null){
            //扔出个异常，currentUser.login(token);可以 catch 到
            throw new UnknownAccountException("未知用户");
        }

        //如果数据库中存在这个用户名
        if(userDao.getUserName().equals(token.getUsername())){

            /*
            之前的凭证对象
            //创建认证对象，传入数据库存在的对象账号和密码，内部会根据 currentUser.login(token) 的 token 进行比较
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
                    userDao.getUserName(),
                    userDao.getUserPassword(),
                    this.getName());*/

             /*
             现在用的加密的平针对象
            通过HashedCredentialsMatcher类中的doCredentialsMatch进行认证
            根据 token 里的值  和  构造的 authcInfo 的值进行比对.
            如果实现这个SaltedAuthenticationInfo接口的话，可以 get 到 salt，但是我觉得不好，不能把 salt 暴露出去
             */
            AuthenticationInfo authcInfo1 = new SimpleAuthenticationInfo(
                    userDao.getUserName(),
                    userDao.getPassword(),
                    ByteSource.Util.bytes(userDao.getSalt()),
                    this.getName());
            return authcInfo1;
        }
        return null;
    }

}
