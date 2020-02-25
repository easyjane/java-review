package com.useable.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HystrixController {


    private Logger logger = LoggerFactory.getLogger(HystrixController.class);

    @Autowired
    private HystrixService hystrixService;

    @RequestMapping("/hello")
    @HystrixCommand(fallbackMethod = "hystrixHello")
    public String hello() {
//        throw new RuntimeException("主动报错");
        return "hello world";
    }

    public String hystrixHello() {
        return "这里是hystrix hello world";
    }

    @RequestMapping("/list")
    public List<String> list() throws ExecutionException, InterruptedException {

        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();

        Future<String> storage1 = hystrixService.getStorage(new Random().nextInt() + "");
        Thread.sleep(2000L);
        Future<String> storage2 = hystrixService.getStorage(new Random().nextInt() + "");

        List<String> list = new ArrayList<String>();
        if (storage1 == null || storage2 == null) {
            logger.error("没有值，走了没合并的方法");
            return list ;
        }

        list.add(storage1.get());
        list.add(storage2.get());
        logger.info("走了合并方法返回值："+ Arrays.toString(list.toArray()));
        return list;
    }
}
