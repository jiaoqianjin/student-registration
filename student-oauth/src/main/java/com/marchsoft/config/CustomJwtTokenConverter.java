package com.marchsoft.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/9/3 17:25
 **/
@Configuration
public class CustomJwtTokenConverter extends JwtAccessTokenConverter {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication authentication) {
            final Map<String, Object> addToJWTInformation = new HashMap<>();
            JSONObject json = new JSONObject();
            json.put("自定义信息1", "职业：软件开发");
            json.put("自定义信息2", "岗位：架构师");
            addToJWTInformation.put("enhance", json.toJSONString());
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(addToJWTInformation);
            OAuth2AccessToken enhancedToken = super.enhance(oAuth2AccessToken, authentication);
            return enhancedToken;

    }
}
