//package com.marchsoft.config;
//
//import com.marchsoft.manage.AccessManager;
//import com.marchsoft.manage.ReactiveJdbcAuthenticationManager;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
//import org.springframework.web.cors.reactive.CorsUtils;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.io.IOException;
//
///**
// * Description：
// *
// * @author jiaoqianjin
// * Date: 2021/8/28 10:26
// **/
////@Configuration
//@Slf4j
//public class SecurityConfig {
//    private static final String MAX_AGE = "18000L";
//
//    @Autowired
//    private AccessManager accessManager;
//
//    /**
//     * 跨域配置
//     */
//    public WebFilter corsFilter() {
//        return (ServerWebExchange ctx, WebFilterChain chain) -> {
//            ServerHttpRequest request = ctx.getRequest();
//            if (CorsUtils.isCorsRequest(request)) {
//                HttpHeaders requestHeaders = request.getHeaders();
//                ServerHttpResponse response = ctx.getResponse();
//                HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
//                HttpHeaders headers = response.getHeaders();
//                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
//                headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
//                if (requestMethod != null) {
//                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
//                }
//                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
//                headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
//                if (request.getMethod() == HttpMethod.OPTIONS) {
//                    response.setStatusCode(HttpStatus.OK);
//                    return Mono.empty();
//                }
//            }
//            return chain.filter(ctx);
//        };
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        // 对称签名密钥
////        jwtAccessTokenConverter.setSigningKey("SIGNING_KEY");
//        // 公钥
//        ClassPathResource classPathResource = new ClassPathResource("public.txt");
//        String publicKey = null;
//        try {
//            publicKey= IOUtils.toString(classPathResource.getInputStream(),"UTF-8");
//            log.info("publicKey:{}" , publicKey);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        jwtAccessTokenConverter.setVerifierKey(publicKey);
//        return jwtAccessTokenConverter;
//    }
//
//    @Bean
//    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
//        //token管理器
//        ReactiveAuthenticationManager tokenAuthenticationManager = new ReactiveJdbcAuthenticationManager(new JwtTokenStore(jwtAccessTokenConverter()));
//        //认证过滤器
//        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(tokenAuthenticationManager);
//        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());
//
//        http.httpBasic().disable()
//                .csrf().disable()
//                .authorizeExchange()
//                .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                .anyExchange().access(accessManager)
//                .and()
//                // 跨域过滤器
//                .addFilterAt(corsFilter(), SecurityWebFiltersOrder.CORS)
//                //oauth2认证过滤器
//                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
//        return http.build();
//    }
//}
