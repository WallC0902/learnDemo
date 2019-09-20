package com.example.hystrix.demo.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangchangpeng on 2019/5/7.
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(
                    name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "50"
            )
    }, fallbackMethod = "fallback")
    public String sayHello(@RequestParam(required = false, defaultValue = "10") int costTime) throws InterruptedException {
        Thread.sleep(costTime);
        System.out.printf("Thread name : [%s], costTime is %d\n", Thread.currentThread().getName(), costTime);
        return "hello world";
    }


    public String fallback(int costTime){
        return "fail back";
    }
}
