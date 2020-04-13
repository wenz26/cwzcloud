package com.cwz.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "---PaymentFallbackService fallback---paymentInfo_OK, 这是实现接口的fallback方法";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "---PaymentFallbackService fallback---paymentInfo_Timeout, 这是实现接口的fallback方法";
    }
}
