package com.cwz.springcloud.controller;

import com.cwz.springcloud.entities.CommonResult;
import com.cwz.springcloud.entities.Payment;
import com.cwz.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("********插入结果：" + result);

        if (result > 0) {
            return new CommonResult(200, "插入数据库成功, serverPort: " + serverPort, result);
        } else {
            return new CommonResult(200, "插入数据库失败, serverPort: " + serverPort, null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("********查询结果：" + paymentById);

        if (paymentById != null) {
            return new CommonResult(200, "查询结果成功, serverPort: " + serverPort, paymentById);
        } else {
            return new CommonResult(200, "没有对应记录，查询ID：" + id, null);
        }
    }

    @GetMapping(value = "/payment/lb/{id}")
    public String getServerId(@PathVariable("id") Long id) {

        log.info("********查询结果：" + serverPort);
        return "serverPort: " + serverPort + ", id: " + id;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
