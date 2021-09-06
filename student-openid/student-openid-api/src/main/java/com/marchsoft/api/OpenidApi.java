package com.marchsoft.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/8/9 17:30
 **/
@RequestMapping(value = "student-openId")
public interface OpenidApi {
    /**
     *功能描述: 判断当前openid是否存在
     * @param openId 微信openId
     * @return java.lang.Integer
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @GetMapping("/api/openId/getCount")
    Integer getCount(String openId);
}
