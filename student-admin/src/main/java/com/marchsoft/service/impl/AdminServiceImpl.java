package com.marchsoft.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marchsoft.utils.AdminUtil;
import com.marchsoft.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import com.marchsoft.entity.Admin;
import com.marchsoft.mapper.AdminMapper;
import com.marchsoft.service.IAdminService;
import com.marchsoft.base.BasicServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiaoqianjin
 * @since 2021-07-29
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends BasicServiceImpl<AdminMapper, Admin> implements IAdminService {

    private final AdminMapper adminMapper;

    private final TokenUtil tokenUtil;

    /**
     * 功能描述: 管理员登录
     *
     * @param account  账号
     * @param password 密码
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @Override
    public Map<String, Object> login(String account, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Admin::getAccount, account).eq(Admin::getPassword, password);
        int count = count(queryWrapper);
        if (count == 1) {
            Admin admin = getOne(queryWrapper);
            String token = admin.getToken();
//            检查token如果为空、或token登录时间已经超时
            if (StrUtil.isEmpty(token) || tokenUtil.isExpire(token)) {
//                重新创建token。或者刷新登录时间
                token = tokenUtil.createToken();
                admin.setToken(token);
                saveOrUpdate(admin);
            }
            AdminUtil.setLoginUser(admin);
            Map<String, Object> map = new HashMap<>(16);
            map.put("status", admin.getStatus());
            map.put("type", admin.getType());
            map.put("token", token);
            return map;
        }
        return null;
    }
}

