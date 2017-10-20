package com.rbac.service.impl;

import com.rbac.dao.UserDao;
import com.rbac.model.User;
import com.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public User getUserInfo(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    public List<User> getUserList() {
        return userDao.findAll();
    }
}
