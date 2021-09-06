package com.marchsoft.service;

import com.marchsoft.entity.Admin;
import com.marchsoft.base.IBasicService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiaoqianjin
 * @since 2021-07-29
 */
public interface IAdminService extends IBasicService<Admin> {

    /**
     *功能描述: 管理员登录
     * @param account 账号
     * @param password 密码
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    Map<String, Object> login(String account, String password);
}
