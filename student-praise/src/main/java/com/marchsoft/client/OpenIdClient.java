package com.marchsoft.client;

import com.marchsoft.api.OpenidApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/7/29 14:49
 **/
@FeignClient(value = "student-openId")
public interface OpenIdClient extends OpenidApi {

}
