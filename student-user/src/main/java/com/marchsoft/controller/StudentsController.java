package com.marchsoft.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.marchsoft.entity.*;
import com.marchsoft.entity.dto.StudentDto;
import com.marchsoft.entity.dto.StudentInfosDto;
import com.marchsoft.response.ResponseResult;
import com.marchsoft.response.enums.ResponseEnum;
import com.marchsoft.response.exception.BaseException;
import com.marchsoft.service.IStudentsService;
import com.marchsoft.utils.AdminUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jiaoqianjin
 * @since 2021-07-26
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "学生模块")
@RequestMapping("/api/students")
public class StudentsController {
    private final IStudentsService studentsService;

//    @GetMapping("/getRankingList")
    @ApiOperation(value = "分页倒叙查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "Integer")
    })
    public ResponseResult getRankingList(Integer page, Integer size) {
        List<StudentInfosDto> studentInfosList = studentsService.getRankingList(page, size);
        return ResponseResult.ok(studentInfosList);
    }

    @GetMapping("/getStudentId")
    @ApiOperation(value = "根据cateId获取学生id")
    public Integer getRankingList(String cateId) {
        QueryWrapper<Students> studentsQueryWrapper = new QueryWrapper<>();
        studentsQueryWrapper.lambda().select(Students::getId).eq(Students::getCardId, cateId);
        Students students = this.studentsService.getOne(studentsQueryWrapper);
        return students.getId();
    }

    @GetMapping("/getWishesCount")
    @ApiOperation(value = "根据学生id获取祝福数量")
    public Students getWishesCount(Integer studentId) {
        //查询当前的祝福数量
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Students::getId, studentId).select(Students::getWishes);
        Students selectOne = this.studentsService.getOne(queryWrapper);
        return selectOne;
    }

    @PutMapping
    @ApiOperation(value = "修改学生信息")
    public void update(Students students) {
        boolean update = this.studentsService.updateById(students);
        if (!update) {
            log.error("【修改学生信息】失败，学生信息：{}", students);
            throw new BaseException(ResponseEnum.Exec_FAILTER);
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "学生登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardId", value = "身份证号")
    })
    public ResponseResult<StudentDto> login(@RequestParam("cardId") String cardId) {
        log.info("【用户登录：】" + cardId);
        StudentDto login = studentsService.login(cardId);
        //判断当前用户是否注册
        if (login == null) {
            return ResponseResult.ok(ResponseEnum.UNKONW_IDCARD);
        } else if (login.getStatus() == 1) {
            return ResponseResult.ok(ResponseEnum.SYDYE_OK, login);
        } else if (login.getStatus() == 0) {
            return ResponseResult.ok(ResponseEnum.SYSUC_OF, login);
        }
        return ResponseResult.error(ResponseEnum.Exec_FAILTER);
    }

    @PostMapping("/submit")
    @ApiOperation(value = "提交最新的学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "student", value = "最新的学生信息")
    })
    public ResponseResult<StudentDto> submit(Students student) {
        log.info("【用户提交：】" + student);

        Map<Integer, StudentDto> studentDtoMap = studentsService.submit(student);
        //判断该用户是否注册
        for (Integer key : studentDtoMap.keySet()) {
            if (key == 0) {
                return ResponseResult.ok(ResponseEnum.SYSUC_OF, studentDtoMap.get(0));
            }
            if (key == 1) {
                return ResponseResult.ok(ResponseEnum.SYDYE_OK, studentDtoMap.get(1));
            }
        }
        return ResponseResult.error(ResponseEnum.Exec_FAILTER);
    }

    @ApiOperation(value = "重置信息")
    @GetMapping("/resetInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardId", value = "身份证号")})
    public ResponseResult<Students> resetInfo(@RequestParam(name = "cardId") String cardId) {
        if (cardId != null) {
            //重置为初始值
            Students students = new Students();
            students.setStatus(0);
            students.setConStatus(0);
            students.setReligiousBelief(0);
            students.setFamilyNum("");
            students.setFamilyNumtwo("");
            students.setWechatId("");
            students.setQqId("");
            students.setAddress("");
            students.setPolApp("");
            students.setPhoneNum("");
            UpdateWrapper<Students> wrapper = new UpdateWrapper<>();
            wrapper.eq("card_id", cardId);
            boolean is = studentsService.update(students, wrapper);
            if (!is) {
                return ResponseResult.error(ResponseEnum.CARD_ERROR);
            }
        } else {
            return ResponseResult.error(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return ResponseResult.ok();
    }

    @GetMapping("/conRegist")
    @ApiImplicitParam(name = "key", value = "条件查询：班级、姓名、电话")
    @ApiOperation(value = "查询已经报道，正在等待管理员确认注册的本院人员")
    public ResponseResult<Object> conRegist(String key) {

        log.info("【未注册学生查找关键字】" + key);
        if (!(AdminUtil.isAdmin() instanceof Admin)) {
            return (ResponseResult<Object>) AdminUtil.isAdmin();
        }
        Admin admin = (Admin) AdminUtil.isAdmin();
        log.info("admin = " + admin);
        List<Students> list = studentsService.selByKey(key, admin.getType());
        if (list.size() != 0) {
            return ResponseResult.ok(list);
        }
        return ResponseResult.error(ResponseEnum.NO_STUDENT);
    }

    @ApiOperation(value = "确认注册，修改conStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardId", value = "身份证号")
    })
    @PostMapping("/updRegist")
    public ResponseResult<Object> updRegist(@RequestParam(required = true, value = "cardId") String cardId) {
        //MTYwbMjA3MzQwOTI3Ng==   411381200110026577
        log.info("【确认注册】" + cardId);
        if (!(AdminUtil.isAdmin() instanceof Admin)) {
            return (ResponseResult<Object>) AdminUtil.isAdmin();
        }
        Admin admin = (Admin) AdminUtil.isAdmin();
        if (cardId != null) {
            QueryWrapper<Students> studentsQueryWrapper = new QueryWrapper<>();
            studentsQueryWrapper.lambda().select(Students::getId).eq(Students::getCardId, cardId);
            Students stu = this.studentsService.getOne(studentsQueryWrapper);
            log.info("当前管理员管理的院系为：】" + admin.getType() + "【当前学生的院系为：】" + stu.getAcademy());
            if (stu.getAcademy().equals(admin.getType())) {
                if (stu.getConStatus() == 1) {
                    return ResponseResult.error(ResponseEnum.ALREADY_REGISTER);
                }
                int b = studentsService.updStatus(cardId);
                if (b == 1) {
                    return ResponseResult.ok(ResponseEnum.SUCCESS);
                }
                return ResponseResult.error(ResponseEnum.Exec_FAILTER);
            }
            return ResponseResult.error(ResponseEnum.INSUFFICIENT_PRIVILEGEX);
        }
        return ResponseResult.error(ResponseEnum.Exec_FAILTER);
    }

    @GetMapping("/selRegAllStu")
    @ResponseBody
    @ApiOperation(value = "人员检索查询管理员所在学院所有新生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "条件查询：姓名、手机号、宿舍号、已报到（未报到）"),
    })
    public ResponseResult<Object> selRegAllStu(String key) {
        log.info("【查看学院所有学生】"+key);
        if (!(AdminUtil.isAdmin() instanceof Admin)) {
            return (ResponseResult<Object>) AdminUtil.isAdmin();
        }
        Admin admin = (Admin) AdminUtil.isAdmin();
        IPage<Students> studentsIPage =  studentsService.selRegAllStu(key,admin.getType());
        if (studentsIPage.getSize() != 0) {
            return ResponseResult.ok(studentsIPage);
        }
        return ResponseResult.ok("查询数据为0");
    }

    @GetMapping("/getMajorInfo")
    public ResponseResult<List<RegistDataDto>> getRegisterData(
            @RequestParam(name = "sex",required = false) Integer sex,
            @RequestParam(name = "type", required = false) Integer type,
            @RequestParam(name = "academy", required = false) String academy,
            @RequestParam(name = "batch", required = false) Integer batch,
            @RequestParam(name = "postgraduate",defaultValue = "0") Integer postgraduate) {
        ResponseResult<List<RegistDataDto>> result;
        int sexNum  = 2;
        List<RegistDataDto> registerData ;
        if (sex != null && sex <= sexNum && sex >= 0) {
            registerData = studentsService.findRegisterData(sex, type, academy, batch,postgraduate);
            if (registerData != null) {
                result = ResponseResult.ok(registerData);
            } else {
                result = ResponseResult.error(ResponseEnum.SEVER_ERROR);
            }
        } else {
            result = ResponseResult.error(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return result;
    }

    @GetMapping("/showStuByCAC")
    @ResponseBody
    public List<StudentProfileDto> showStudentByCollegeAcademyClass(Integer type, String key, Integer status, Integer sex ) {
        return studentsService.showStudentByCollegeAcademyClass(type, key, status, sex);
    }

    @GetMapping("/recentData")
    @ApiOperation(value = "获取最近num条注册完成数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num",value = "获取数据量")
    })
    public List<RecentRegisterDataDto> getRecentData(@RequestParam(defaultValue = "0") int num ){
       return studentsService.findRecentData(num);
    }

    @GetMapping("/getTimeData")
    @ApiOperation(value = "获取每小时报道数据")
    public Map<Integer, List<TimeRegisterDataDto>> getTimeRegisterData() throws InterruptedException {
        return studentsService.getTimeRegisterNum();
    }

    @GetMapping("/getLatAndLng")
    @ApiOperation(value = "获取用户地址和经纬度")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "second",value = "间隔时间（秒）默认为5")
    })
    public List<AddressDataDto> getAddressAndLatAndLng(@RequestParam(defaultValue = "5") int second){
        return studentsService.getAddressAndLatAndLng(second);
    }

    @GetMapping("/getAllLatAndLng")
    @ApiOperation(value = "获取所有数据的经纬度")
    public List<AddressDataDto> getAllLatAndLng(){
        return studentsService.getAllLatAndLng();
    }
}
