package com.rbac.service;


import com.rbac.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
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
