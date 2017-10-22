package com.rbac.controller;

import com.rbac.service.UserService;
import com.rbac.utils.Encrypt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;


    //方便调试 直接用 get
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        logger.info("开始登录");
        //构建登录 token

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //设置记住我
        token.setRememberMe(true);
        //获取当前登录用户
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //登录
            currentUser.login(token);
            //判断用户状态是否已经被认证
            if (currentUser.isAuthenticated()) {
                map.put("msg", "登录成功");
            } else {
                map.put("msg", "系统异常，请重试");
            }
        } catch (UnknownAccountException uae) {
            map.put("msg", "您还未登录，无法操作");
        } catch (IncorrectCredentialsException ice) {
            map.put("msg", "密码错误");
        } catch (LockedAccountException lae) {
            map.put("msg", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            map.put("msg", "登录失败次数过多");
        } catch (AuthenticationException ae) {
            map.put("msg", ae.getMessage());
        }

        return map;
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserList() {
        logger.info("user/getUserList init");
        Map<String, Object> map = new HashMap<>();
        map.put("users", userService.getUserList());
        return map;
    }

    @RequestMapping(value = "/getStatusInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getStatusInfo() {
        Map<String, Object> map = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();

        map.put("isRemembered", currentUser.isRemembered() ? "session过期了，我是被记住的" : currentUser.isRemembered());
        map.put("isAuthenticated", currentUser.isAuthenticated() ? "session 还在，我是被认证的" : currentUser.isAuthenticated());

        //登录过，可以看到自己的名字
        //session 过期的时候，会触发当前判断，前提是登录过的用户设置了token.setRememberMe(true);
        if (currentUser.isRemembered()){
            map.put("name", "sunpeng");
        }
        //已认证，可以看到手机号
        if (currentUser.isAuthenticated()){
            map.put("phone", "xxxxx");
        }
        //未登录的情况下，只能看到其他信息
        map.put("weather", "sunny");

        return map;
    }

    @RequestMapping(value = "/unAuthorized", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> unAuthorized() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "您没有权限访问");

        return map;
    }
}
