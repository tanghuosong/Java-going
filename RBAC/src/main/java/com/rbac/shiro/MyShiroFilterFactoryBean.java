package com.rbac.shiro;

import com.rbac.controller.LoginController;
import com.rbac.dao.PermissionDao;
import com.rbac.dao.RoleDao;
import com.rbac.model.Permission;
import com.rbac.model.Role;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(LoginController.class);

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    private MyShiroFilterFactoryBean myShiroFilterFactoryBean;

    /**
     *  可以配置加载数据库的备注，省去了配置 xml，初始化的时候会加载
     * @param filterChainDefinitionMap
     *
     * Map<String,String> filterMap=new HashMap<>();
     * 再细致的拼装可以自己改
     * filterMap.put("/user/login**","anon");
     * filterMap.put("/admin/**","authc,roles[admin],perms[admin:info]");
     * filterMap.put(" /super/**","authc,roles[super],perms[super:info]");
     */
    @Override
    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {

        Map<String,String> filterMap = new LinkedHashMap<>();

        // 获得所有的资源，以及所有的角色，权限信息
        List<Role> roles = roleDao.getRoles();

        for(Role role : roles){
            // 获取相对应的权限
            List<Permission> permissions = permissionDao.getPermissionsByRoleId(role.getId());
            for (Permission permission : permissions){
                filterMap.put(permission.getUrlStr(),"authc,"+"roles["+role.getRoleName()+"],perms["+permission.getName()+"]");
            }
        }
        super.setFilterChainDefinitionMap(filterMap);
    }


    /**
     * 动态更新新的权限
     * @param filterMap
     */
    public synchronized void updatePermission(Map<String, String> filterMap) {

        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) myShiroFilterFactoryBean.getObject();

            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager filterManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            //清空拦截管理器中的存储
            filterManager.getFilterChains().clear();
            /*
            清空拦截工厂中的存储,如果不清空这里,还会把之前的带进去
            ps:如果仅仅是更新的话,可以根据这里的 map 遍历数据修改,重新整理好权限再一起添加
             */
            myShiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

            // 相当于新建的 map, 因为已经清空了
            Map<String, String> chains = myShiroFilterFactoryBean.getFilterChainDefinitionMap();
            //把修改后的 map 放进去
            chains.putAll(filterMap);

            //这个相当于是全量添加
            for (Map.Entry<String, String> entry : filterMap.entrySet()) {
                //要拦截的地址
                String url = entry.getKey().trim().replace(" ", "");
                //地址持有的权限
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                //生成拦截
                filterManager.createChain(url, chainDefinition);
            }
        } catch (Exception e) {
            logger.error("updatePermission error,filterMap=" + filterMap, e);
        }
    }

}
