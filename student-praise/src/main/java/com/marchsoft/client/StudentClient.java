package com.marchsoft.client;

import com.marchsoft.entity.Students;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/7/29 15:19
 **/
@FeignClient(value = "student-user")
public interface StudentClient{
    /**
     *功能描述: 根据身份证号获取该学生id
     * @param cateId 身份证号
     * @return java.lang.Integer
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @GetMapping("/api/student/getStudentId")
    Integer getStudentId(String cateId);

    /**
     *功能描述: 根据学生id获取祝福数量
     * @param studentId 学生id
     * @return com.marchsoft.entity.Students
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @GetMapping("/api/student/getWishesCount")
    Students getWishes(Integer studentId);


    /**
     *功能描述:修改学生信息
     * @param students 待修改学生信息
     * @return void
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @PutMapping("/api/student")
    void update(Students students);
}
