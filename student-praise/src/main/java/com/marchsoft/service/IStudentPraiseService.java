package com.marchsoft.service;

import com.marchsoft.entity.StudentPraise;
import com.marchsoft.base.IBasicService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiaoqianjin
 * @since 2021-07-29
 */
public interface IStudentPraiseService extends IBasicService<StudentPraise> {

    /**
     *功能描述: 点击祝福
     * @param cartId 身份证id
     * @param openId 微信openid
     * @return java.lang.Boolean
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    Boolean clickWish(String cartId, String openId);
}
