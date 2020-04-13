package com.cwz.springcloud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentMain8001Test {

    @Test
    void test01() {
        System.out.println("aaa");
    }
}
