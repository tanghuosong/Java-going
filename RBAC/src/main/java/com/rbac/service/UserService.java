package com.rbac.service;


import com.rbac.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    /**
     * 获取用户信息
     * @param userName 用户名
     * @return user
     */
    User getUserInfo(String userName);

    /**
     * 获取用户列表
     * @return list
     */
    List<User> getUserList();
}
