package com.useable.feign.controller;

import com.netflix.discovery.converters.Auto;
import com.useable.feign.service.FeignClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FeignServerController {

    @Autowired
    private FeignClientInterface feignClientInterface;

    @RequestMapping("/order")
    public Map<String,Object> getOrder() {

        Map<String,Object> map = feignClientInterface.getOrder();
        return map;
    }
}
