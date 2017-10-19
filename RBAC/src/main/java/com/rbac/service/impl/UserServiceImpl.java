package com.rbac.service.impl;

import com.rbac.dao.User1Dao;
import com.rbac.model.User;
import com.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private User1Dao user1Dao;

    @Override
    public User getUserInfo(String userName) {
        return user1Dao.getUserInfo(userName);
    }

    @Override
    public List<User> getUserList() {
        return user1Dao.getUserList();
    }
}
