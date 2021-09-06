package com.marchsoft.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marchsoft.response.ResponseResult;
import com.marchsoft.response.enums.ResponseEnum;
import com.marchsoft.utils.WeiXinUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import com.marchsoft.service.IOpenIdService;
import com.marchsoft.entity.OpenId;


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
@Api(tags = "微信模块")
@RequestMapping("/api/openId")
public class OpenIdController {
    private final IOpenIdService openIdService;

    private final WeiXinUtil weiXinUtil;

    @GetMapping("/weiXinLogin")
    public ResponseResult weiXinLogin(String code ) {
        String openId = weiXinUtil.getOpenId(code);
        if (openId == null) {
            return ResponseResult.ok(ResponseEnum.CODEISFAIL);
        }

        QueryWrapper<OpenId> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OpenId> openIdLambdaQueryWrapper = queryWrapper.lambda().eq(OpenId::getOpenid, openId);
        int count = openIdService.count(openIdLambdaQueryWrapper);
        if (count == 0) {
            OpenId openIdEntity = new OpenId();
            openIdEntity.setOpenid(openId);
            openIdService.save(openIdEntity);
        }
        return ResponseResult.ok(openId);
    }

    @GetMapping("/getCount")
    public Integer getCount(String openId) {
        QueryWrapper<OpenId> openIdQueryWrapper = new QueryWrapper<>();
        openIdQueryWrapper.lambda().eq(OpenId::getOpenid, openId);
        return this.openIdService.count(openIdQueryWrapper);
    }

}
