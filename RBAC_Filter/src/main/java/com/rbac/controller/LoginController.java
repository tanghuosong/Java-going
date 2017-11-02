package com.rbac.controller;

import com.rbac.model.User;
import com.rbac.service.MenuService;
import com.rbac.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;

    //方便调试 直接用 get
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> login(String username,String password) {
        Map<String, Object> map = new HashMap<>();


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

        return map;
    }

    @RequestMapping(value = "/unAuthorized", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> unAuthorized() {
        Map<String, Object> map = new HashMap<>();
        map.put("code","401");
        map.put("status", "您没有权限访问");

        return map;
    }
    @RequestMapping(value = "/unLogin", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> unLogin() {
        Map<String, Object> map = new HashMap<>();

        map.put("code","401");
        map.put("status", "您尚未登录");
        return map;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> auth(HttpServletRequest request, HttpServletResponse response) {
        String token =  request.getParameter("token");
//        User user = userService.findByToken(token);
        User user = (User) request.getSession().getAttribute("user");

        System.out.println(token);
        Map<String, Object> map = new HashMap<>();
        map.put("code","401");
        map.put("status", "您尚未登录");
        return map;
    }
}
