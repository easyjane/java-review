package com.useable.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/a")
    public Map<String ,Object> test1() {
        String url = "http://duan-ribbon-b/b";
        Map<String ,Object> map = restTemplate.getForObject(url,Map.class);

        return map;
    }

    @GetMapping("/zuul/test")
    public Map<String ,Object> zuulTest() {
        Map<String ,Object> map = new HashMap<String, Object>();

        map.put("result","zuul-test");
        return map;
    }
}
