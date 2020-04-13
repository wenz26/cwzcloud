package com.cwz.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// 开启服务发现(发现服务的各种信息)
@EnableDiscoveryClient
public class OrderZKMain800 {

    public static void main(String[] args) {
        SpringApplication.run(OrderZKMain800.class, args);
    }
}
