package com.rbac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);


    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getInfo() {
        logger.info("admin/getInfo init");
        Map<String, Object> map = new HashMap<>();
        map.put("name","admin");
        return map;
    }


}
