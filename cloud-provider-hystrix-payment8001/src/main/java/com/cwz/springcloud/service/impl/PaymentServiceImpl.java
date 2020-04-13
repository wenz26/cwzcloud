package com.cwz.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cwz.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    // ==============服务降级
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "--paymentInfo_OK, id：" + id + "\t" + "哈哈";
    }

    // 这里的意思是 这个线程默认的超时时间为3秒钟，超过三秒钟就会超时，就会被降级并调用 fallbackMethod 定义的方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandle", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    @Override
    public String paymentInfo_Timeout(Integer id) {

        //int a = 10/0;
        int timeout = 3;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "--paymentInfo_Timeout, id：" + id + "\t" + "呜呜，耗时"+ timeout +"秒";
    }

    public String paymentInfo_TimeoutHandle(Integer id) {

        return "线程池：" + Thread.currentThread().getName() + "--paymentInfo_TimeoutHandle, id：" + id + "\t" + "呜呜，8001调用降级方法";
    }


    // ============服务熔断

    // 这里的配置意思是：开启断路器，当达到10s内的请求次数超过10次，或者10s内请求的失败率达到60%，就自动跳闸
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期（时间范围）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id){

        if (id < 0) {
            throw new RuntimeException("********* id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id 不能为负数，请稍后再试，(┬＿┬)， id: " + id;
    }


}
