package com.marchsoft.controller;

import com.marchsoft.response.ResponseResult;
import com.marchsoft.response.enums.ResponseEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import com.marchsoft.service.IStudentPraiseService;
import com.marchsoft.entity.StudentPraise;


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
@Api(tags = "模块")
@RequestMapping("/api/studentPraise")
public class StudentPraiseController {
    private final IStudentPraiseService studentPraiseService;

    @GetMapping("/clickWish")
    @ApiOperation(value = "点击祝福")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "身份证id", dataType = "String"),
            @ApiImplicitParam(name = "openId", value = "用户openid", dataType = "String")
    })
    public ResponseResult clickWish(String cartId, String openId ) {
        Boolean clickWish = studentPraiseService.clickWish(cartId, openId);
        if (clickWish == null) {
            return ResponseResult.ok(ResponseEnum.NOOPENID);
        } else if (clickWish) {
            return ResponseResult.ok();
        } else {
            return ResponseResult.ok(ResponseEnum.HASWISHED);
        }
    }
}
