package com.marchsoft.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/8/10 9:46
 **/

@Configuration
public class MyRandomRule {
    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
}
