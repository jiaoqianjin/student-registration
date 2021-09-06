package com.marchsoft.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.marchsoft.entity.*;
import com.marchsoft.base.IBasicService;
import com.marchsoft.entity.dto.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiaoqianjin
 * @since 2021-07-26
 */
public interface IStudentsService extends IBasicService<Students> {

    /**
     *功能描述: 分页倒叙查询
     * @param page 页码
     * @param size 每页大小
     * @return java.util.List<com.marchsoft.entity.dto.StudentInfosDto>
     * @author jiaoqianjin
     * @date 2021/7/26
    */
    List<StudentInfosDto> getRankingList(Integer page, Integer size);

    /**
     *功能描述: 学生登录
     * @param cardId 身份证号
     * @return com.marchsoft.entity.dto.StudentDto 当前用户的基本信息
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    StudentDto login(String cardId);

    /**
     *功能描述: 用户的最新数据提交
     * @param student 学生信息
     * @return java.util.Map<java.lang.Integer,com.marchsoft.entity.dto.StudentDto>
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    Map<Integer, StudentDto> submit(Students student);

    /**
     *功能描述: 查找管理员本院已经报到没有注册人员
     * @param key 关键字
     * @param type /
     * @return java.util.List<com.marchsoft.entity.Students>
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    List<Students> selByKey(String key, String type);

    /**
     *功能描述: 确认注册
     * @param cardId 证件号
     * @return int
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    int updStatus(String cardId);

    /**
     *功能描述: 人员检索查询管理员所在学院所有新生
     * @param key 关键字
     * @param type 管理员学院
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.marchsoft.entity.Students>
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    IPage<Students> selRegAllStu(String key, String type);

    /**
     *功能描述: 查询报到数据
     * @param sex 性别
     * @param type 0查询学院 1查询专业 2 查询班级
     * @param academy 学院名
     * @param batch /
     * @param postgraduate /
     * @return java.util.List<com.marchsoft.entity.dto.RegistDataDto>
     * @author jiaoqianjin
     * @date 2021/7/29
    */
    List<RegistDataDto> findRegisterData(Integer sex, Integer type, String academy, Integer batch, Integer postgraduate);

    List<StudentProfileDto> showStudentByCollegeAcademyClass(Integer type, String key, Integer status, Integer sex);

    List<RecentRegisterDataDto> findRecentData(int num);

    Map<Integer, List<TimeRegisterDataDto>> getTimeRegisterNum();

    List<AddressDataDto> getAddressAndLatAndLng(int second);

    List<AddressDataDto> getAllLatAndLng();
}
