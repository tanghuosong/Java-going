package com.rbac.dao;


import com.rbac.model.User;

import java.util.List;


public interface User1Dao {

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    User getUserInfo(String userName);

    /**
     * 获取用户列表
     * @return
     */
    List<User> getUserList();

}
