package com.marchsoft.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/7/13 17:25
 **/
@RestController
@Slf4j
@RequestMapping("/api/data")
public class ReportsController {
    public static final String INVOKE_URL = "http://student-user";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/getTimeData")
    public String paymentInfo()
    {
        String result = restTemplate.getForObject(INVOKE_URL+"/api/students/getTimeData",String.class);
        return result;
    }
}
