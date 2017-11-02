package com.rbac.service.impl;

import com.rbac.dao.WebMenuDao;
import com.rbac.model.WebMenu;
import com.rbac.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: ths
 * @date: 2017/10/23
 * @time: 12:04
 * @description:
 */
@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    WebMenuDao webMenuDao;

    @Override
    public List<WebMenu> getAll() {

        List<WebMenu> webMenus = webMenuDao.findAll();

        for(WebMenu webMenu : webMenus){
            // 为一级菜单
            if(webMenu.getMpid() == null){
                // 找到下面的二级菜单
                List<WebMenu> secordMenu = webMenus.stream().filter(s->webMenu.getId().equals(s.getMpid())).collect(Collectors.toList());
                // 找到三级菜单
                for (WebMenu webMenuThird: webMenus){
                    List<WebMenu> thirdMenu = webMenus.stream().filter(s1 -> webMenuThird.getId().equals(s1.getBpid())).collect(Collectors.toList());
                }
            }
        }

        return webMenus;
    }
}
