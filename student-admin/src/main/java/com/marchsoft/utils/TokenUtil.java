package com.marchsoft.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/7/29 14:14
 **/
@Component
public class TokenUtil {

    @Value("${jwt.header}")
    private String startWith;


    public String createToken() {
        String encode = Base64Util.encode(System.currentTimeMillis() + "");
        return encode;
    }

    public Boolean isExpire(String token) {
        //时间为一个月
        long monthTime = 2592000000L;
        //如果token字符串发生错误
        try {
            Long tokenDate = Long.valueOf(Base64Util.decode(token));
            return (System.currentTimeMillis() - tokenDate) >= monthTime;
        } catch (Exception ignored) {
        }
        return true;
    }
}
