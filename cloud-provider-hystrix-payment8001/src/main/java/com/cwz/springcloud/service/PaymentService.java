package com.cwz.springcloud.service;

public interface PaymentService {

    String paymentInfo_OK(Integer id);

    String paymentInfo_Timeout(Integer id);

    String paymentCircuitBreaker(Integer id);
}
