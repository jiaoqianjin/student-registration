//package com.marchsoft.endpoint;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.provider.AuthorizationRequest;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.Map;
//
///**
// * Description：
// *
// * @author jiaoqianjin
// * Date: 2021/8/26 21:40
// **/
//@RestController
//@RequestMapping("/token")
//@RequiredArgsConstructor
//public class StudentTokenEndpoint {
//
//    private final ClientDetailsService clientDetailsService;
//
//    /**
//     * 认证页面
//     * @param modelAndView 视图
//     * @param error 表单登录失败处理回调的错误信息
//     * @return ModelAndView
//     */
//    @GetMapping("/login")
//    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
//        modelAndView.setViewName("ftl/login");
//        modelAndView.addObject("error", error);
//        return modelAndView;
//    }
//    /**
//     * test
//     */
//    @GetMapping("/test")
//    public String test() {
//        return "test";
//    }
//
//    /**
//     * 确认授权页面
//     * @param request
//     * @param session
//     * @param modelAndView
//     * @return
//     */
//    @GetMapping("/confirm_access")
//    public ModelAndView confirm(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
//        Map<String, Object> scopeList = (Map<String, Object>) request.getAttribute("scopes");
//        modelAndView.addObject("scopeList", scopeList.keySet());
//
//        Object auth = session.getAttribute("authorizationRequest");
//
//        if (auth != null) {
//            AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
//            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
//            modelAndView.addObject("app", clientDetails.getAdditionalInformation());
//            modelAndView.addObject("user", null);
//        }
//
//        modelAndView.setViewName("ftl/confirm");
//        return modelAndView;
//    }
//}
