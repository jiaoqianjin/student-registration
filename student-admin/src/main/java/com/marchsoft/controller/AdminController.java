package com.marchsoft.controller;

import com.marchsoft.response.ResponseResult;
import com.marchsoft.response.enums.ResponseEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import com.marchsoft.service.IAdminService;
import com.marchsoft.entity.Admin;

import java.util.Map;


/**
* <p>
*  前端控制器
* </p>
* @author jiaoqianjin
* @since 2021-07-29
*/
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "管理员模块")
@RequestMapping("/api/admin")
public class AdminController {
    private final IAdminService adminService;

    @GetMapping("/login")
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号"),
            @ApiImplicitParam(name = "password", value = "密码")
    })
    public ResponseResult<Object> login(String account, String password ) {
        Map<String, Object> tokenAndStatus = adminService.login(account, password);
//        首先判断用户是否登陆过，如果登陆过，则对应token会存在
//        如果查询为空，返回错误，提示重新登录
        if (tokenAndStatus == null || tokenAndStatus.size() == 0) {
            return ResponseResult.error(ResponseEnum.LOGIN_IS_FAIL);
        }
        return ResponseResult.ok(tokenAndStatus);
    }

}
