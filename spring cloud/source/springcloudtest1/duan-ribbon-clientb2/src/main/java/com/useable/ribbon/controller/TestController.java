package com.useable.ribbon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @RequestMapping("/b")
    public Map<String,Object> b() {
        Map map = new HashMap();
        map.put("date",System.currentTimeMillis());
        map.put("port","7002");

        return map;
    }
}
