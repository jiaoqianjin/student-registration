package com.marchsoft.client;

import com.marchsoft.entity.*;
import com.marchsoft.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Description： 远程调用 student-user 服务
 *
 * @author jiaoqianjin
 * Date: 2021/7/27 17:35
 **/
@FeignClient(value = "student-user")
public interface StudentClient {
    @GetMapping(value = "/api/students/getRankingList")
    Object getRankingList(@RequestParam("page") Integer page, @RequestParam("size") Integer size);

    @GetMapping(value = "/api/students/getMajorInfo")
    ResponseResult<List<RegistDataDto>> findRegisterData(@RequestParam(name = "sex", required = false) Integer sex,
                                                         @RequestParam(name = "type", required = false) Integer type,
                                                         @RequestParam(name = "academy", required = false) String academy,
                                                         @RequestParam(name = "batch", required = false) Integer batch,
                                                         @RequestParam(name = "postgraduate", defaultValue = "0") Integer postgraduate);

    @GetMapping(value = "/api/students/showStuByCAC")
    List<StudentProfileDto> showStudentByCollegeAcademyClass(@RequestParam("type") Integer type,
                                                             @RequestParam("key") String key,
                                                             @RequestParam("status") Integer status,
                                                             @RequestParam("sex") Integer sex);

    @GetMapping(value = "/api/students/recentData")
    List<RecentRegisterDataDto> findRecentData(@RequestParam("num") int num);

    @GetMapping(value = "/api/students/getTimeData")
    Map<Integer, List<TimeRegisterDataDto>> getTimeRegisterNum();

    @GetMapping(value = "/api/students/getLatAndLng")
    List<AddressDataDto> getAddressAndLatAndLng(@RequestParam("second") int second);

    @GetMapping(value = "/api/students/getAllLatAndLng")
    List<AddressDataDto> getAllLatAndLng();

}
