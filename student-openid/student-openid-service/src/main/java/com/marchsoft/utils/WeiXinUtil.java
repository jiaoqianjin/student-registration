package com.marchsoft.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/7/29 14:35
 **/
@Component
public class WeiXinUtil {
    /**
     * https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     * 属性	   类型	   默认值	必填	说明
     * appid	    string		    是	    小程序 appId
     * secret	    string		    是	    小程序 appSecret
     * js_code	string		    是	    登录时获取的 code
     * grant_type	string		    是	    授权类型，此处只需填写 authorization_code
     */
    @Value("${app.appid}")
    String APPID;

    @Value("${app.secret}")
    String SECRET;

    @Value("${app.grant_type}")
    String AUTHORIZATION_CODE;

    /**
     *功能描述: 获取openid和session_key
     * @param code code
     * @return java.lang.String
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    public String getOpenId( String code ) {
        //利用hutool发送https请求
        HashMap<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("appid", APPID);
        paramMap.put("secret", SECRET);
        paramMap.put("code", code);
        paramMap.put("grant_type", AUTHORIZATION_CODE);
        Object result = HttpUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token?", paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject.getStr("openid");
    }
}
