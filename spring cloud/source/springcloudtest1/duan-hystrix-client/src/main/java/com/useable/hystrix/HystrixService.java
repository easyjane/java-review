package com.useable.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class HystrixService {

    private Logger logger = LoggerFactory.getLogger(HystrixService.class);
    private String result;

    @HystrixCollapser(batchMethod = "getStorageList",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "1000")
    })
    public Future<String> getStorage(String id) {
        return null;
    }

    @HystrixCommand
    public List<String> getStorageList(List<String> idList) {
        logger.info("getStorageList方法的传入参数是"+idList.size()+"个，内容分别是："+ Arrays.toString(idList.toArray()));
        List<String> result = new ArrayList<String>();
        for (String id :
                idList) {
            result.add("id:"+id + "结果："+new Random().nextInt());
        }
        return result;
    }
}
