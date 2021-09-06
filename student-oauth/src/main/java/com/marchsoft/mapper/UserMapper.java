package com.marchsoft.mapper;

import com.marchsoft.base.BasicMapper;
import com.marchsoft.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* <p>
*  Mapper 接口
* </p>
*
* @author jiaoqianjin
* @since 2021-08-27
*/
@Mapper
public interface UserMapper extends BasicMapper<User> {

}
