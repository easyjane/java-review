package com.useable.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("duan-feign-client")
// @FeignClient 会将这个接口注入到SpringIOC容器中成为一个Bean
// 和Mybatis里面的@Mapper 对应的接口非常的类似
public interface FeignClientInterface {

    @RequestMapping("/order")
    public Map<String,Object> getOrder();
}
