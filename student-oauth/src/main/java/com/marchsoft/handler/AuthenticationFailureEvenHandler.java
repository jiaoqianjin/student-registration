package com.marchsoft.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/8/26 21:19
 **/

@Slf4j
@Component
public class AuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {

    /**
     * 处理登录失败方法
     * <p>
     * @param authenticationException 登录的authentication 对象
     * @param authentication 登录的authenticationException 对象
     */
    @Override
    public void handle(AuthenticationException authenticationException, Authentication authentication) {
        log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
    }

}
