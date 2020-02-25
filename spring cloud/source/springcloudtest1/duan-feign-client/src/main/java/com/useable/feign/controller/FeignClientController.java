package com.useable.feign.controller;

import com.useable.feign.FeignClientApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FeignClientController {

    private Logger logger = LoggerFactory.getLogger(FeignClientApplication.class);

    @RequestMapping("/order")
    public Map<String,Object> getOrder() {
        Map<String ,Object> map = new HashMap();
        map.put("id","123");
        map.put("date",new Date());
        map.put("price",123.56F);
        map.put("tag","feign调用");
        logger.info("此方法被调用");
        return map;
    }
}
