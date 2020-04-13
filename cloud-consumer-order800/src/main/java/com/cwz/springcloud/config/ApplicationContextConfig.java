package com.cwz.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: 陈文振
 * @date: 2020/3/19
 * @description: RestTemplate提供了多种便捷访问远程Https服务的方法
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    // 使用 @LoadBalanced 注释赋予RestTemplate负载均衡的能力
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
