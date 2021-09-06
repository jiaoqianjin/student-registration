package com.marchsoft.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/8/26 21:24
 **/

@Slf4j
@Component
public class LogoutSuccessEventHandler extends AbstractLogoutSuccessEventHandler {

    /**
     * 处理退出成功方法
     * <p>
     * 获取到登录的authentication 对象
     * @param authentication 登录对象
     */
    @Override
    public void handle(Authentication authentication) {
        log.info("用户：{} 退出成功", authentication.getPrincipal());
    }

}
