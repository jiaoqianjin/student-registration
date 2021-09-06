package com.marchsoft.service.impl;

import lombok.RequiredArgsConstructor;
import com.marchsoft.entity.User;
import com.marchsoft.mapper.UserMapper;
import com.marchsoft.service.IUserService;
import com.marchsoft.base.BasicServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
*  服务实现类
* </p>
*
* @author jiaoqianjin
* @since 2021-08-27
*/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BasicServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

}

