package com.marchsoft.service.impl;

import lombok.RequiredArgsConstructor;
import com.marchsoft.entity.OpenId;
import com.marchsoft.mapper.OpenIdMapper;
import com.marchsoft.service.IOpenIdService;
import com.marchsoft.base.BasicServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
*  服务实现类
* </p>
*
* @author jiaoqianjin
* @since 2021-07-29
*/
@Service
@RequiredArgsConstructor
public class OpenIdServiceImpl extends BasicServiceImpl<OpenIdMapper, OpenId> implements IOpenIdService {

    private final OpenIdMapper openIdMapper;

}

