package com.marchsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/7/13 15:41
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.marchsoft.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
