package com.marchsoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marchsoft.client.OpenIdClient;
import com.marchsoft.client.StudentClient;
import com.marchsoft.entity.Students;
import lombok.RequiredArgsConstructor;
import com.marchsoft.entity.StudentPraise;
import com.marchsoft.mapper.StudentPraiseMapper;
import com.marchsoft.service.IStudentPraiseService;
import com.marchsoft.base.BasicServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@Slf4j
public class StudentPraiseServiceImpl extends BasicServiceImpl<StudentPraiseMapper, StudentPraise> implements IStudentPraiseService {

    private final StudentPraiseMapper studentPraiseMapper;

    private final OpenIdClient openIdClient;

    private final StudentClient studentClient;

    /**
     * 功能描述: 点击祝福
     *
     * @param cartId 身份证id
     * @param openId 微信openid
     * @return java.lang.Boolean
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @Override
    public Boolean clickWish(String cartId, String openId) {
        log.info("【学生身份证为：】" + cartId + "【当前用户的openid为】" + openId);
        Integer openIdCount = openIdClient.getCount(openId);
        if (openIdCount != 0) {

            Integer id = studentClient.getStudentId(cartId);
            //将openid和考生id插入
            QueryWrapper<StudentPraise> studentPraiseWrapper = new QueryWrapper<>();
            studentPraiseWrapper.lambda().eq(StudentPraise::getStudentId, id).eq(StudentPraise::getOpenid, openId);
            Integer count = studentPraiseMapper.selectCount(studentPraiseWrapper);
            if (count == 0) {
                StudentPraise studentPraise = new StudentPraise();
                studentPraise.setStudentId(id);
                studentPraise.setOpenid(openId);
                studentPraiseMapper.insert(studentPraise);
                log.info("【插入数据成功】");
                //查询当前的祝福数量
                Students selectOne = this.studentClient.getWishes(id);
                int wishes = 0;
                if (selectOne != null) {
                    wishes = selectOne.getWishes();
                }
                log.info("【当前的祝福数量为：】" + wishes);
                //更新库中的祝福数量
                Students student = new Students();
                student.setId(id);
                student.setWishes(wishes + 1);
                this.studentClient.update(student);
                return true;
            } else {
                return false;
            }
        } else {
            return null;
        }
    }
}

