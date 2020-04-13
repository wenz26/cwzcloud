package com.cwz.springcloud.controller;

import com.cwz.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    //@HystrixCommand
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){

        //int a = 10/0;
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    // 这里的意思是 这个线程默认的超时时间为3秒钟，超过三秒钟就会超时，就会被降级并调用 fallbackMethod 定义的方法
    @HystrixCommand(fallbackMethod = "paymentInfo_FallbackHandle", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id")Integer id){

        //int a = 10/0;
        String result = paymentHystrixService.paymentInfo_Timeout(id);
        return result;
    }

    public String paymentInfo_FallbackHandle(Integer id) {

        return "线程池：" + Thread.currentThread().getName() + "--paymentInfo_TimeoutHandle, id：" + id + "\t" +
                "呜呜，800调用降级方法，方法出错或系统繁忙，请稍后重试";
    }

    // global fallback（全局fallback方法）
    public String payment_Global_FallbackMethod() {
        return "Global异常处理信息，请稍后尝试，(┬＿┬)";
    }
}
