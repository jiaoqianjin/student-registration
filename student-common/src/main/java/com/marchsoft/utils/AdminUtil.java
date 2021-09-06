package com.marchsoft.utils;

import com.marchsoft.entity.Admin;
import com.marchsoft.response.ResponseResult;
import com.marchsoft.response.enums.ResponseEnum;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/7/29 14:19
 **/

public class AdminUtil {
    private static ThreadLocal<Admin> userThreadLocal = new ThreadLocal<Admin>();

    public static Admin getLoginUser() {
        Admin admin = userThreadLocal.get();
        return admin;
    }

    public static Object isAdmin() {
        Admin admin = userThreadLocal.get();
        if (admin != null) {
            if (admin.getStatus() != 2) {
                return ResponseResult.error(ResponseEnum.REQUEST_IS_FAIL);
            }
            return admin;
        }
        return ResponseResult.error(ResponseEnum.REQUEST_IS_FAIL);
    }

    /**
     * 获取当前登录用户的ID
     * 未登录返回null
     */
    public static Integer getLoginUserId() {

        Admin adminUser = userThreadLocal.get();
        if (adminUser != null && adminUser.getId() != null) {
            return adminUser.getId();
        }
        return null;
    }

    public static void setLoginUser(Admin admin) {
        userThreadLocal.set(admin);
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}
