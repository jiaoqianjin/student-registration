package com.marchsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marchsoft.entity.*;
import com.marchsoft.entity.dto.*;
import lombok.RequiredArgsConstructor;
import com.marchsoft.mapper.StudentsMapper;
import com.marchsoft.service.IStudentsService;
import com.marchsoft.base.BasicServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiaoqianjin
 * @since 2021-07-26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StudentsServiceImpl extends BasicServiceImpl<StudentsMapper, Students> implements IStudentsService {

    private final StudentsMapper studentsMapper;

    /**
     * 功能描述: 分页倒叙查询
     *
     * @param page 页码
     * @param size 每页大小
     * @return java.util.List<com.marchsoft.entity.dto.StudentInfosDto>
     * @author jiaoqianjin
     * @date 2021/7/26
     */
    @Override
    public List<StudentInfosDto> getRankingList(Integer page, Integer size) {
        IPage<Students> studentPage = new Page<>(page, size);
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(Students::getName, Students::getClasses, Students::getWishes)
                .orderByDesc(Students::getWishes)
                .orderByAsc(Students::getId);
        studentsMapper.selectPage(studentPage, queryWrapper);
        List<Students> records = studentPage.getRecords();
        return records.stream().map(record -> {
            StudentInfosDto studentInfos = new StudentInfosDto();
            BeanUtil.copyProperties(record, studentInfos);
            return studentInfos;
        }).collect(Collectors.toList());
    }

    /**
     * 功能描述: 学生登录
     *
     * @param cardId 身份证号
     * @return com.marchsoft.entity.dto.StudentDto 当前用户的基本信息
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @Override
    public StudentDto login(String cardId) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        Students student = studentsMapper.selectOne(queryWrapper);
        //判断学生是否存在
        if (student == null) {
            return null;
        }
        //判断学生是否报道
        StudentDto judgeStatusSure = this.judgeStatus(studentsMapper, cardId);
        if (judgeStatusSure != null) {
            return judgeStatusSure;
        }
        StudentDto studentDto = new StudentDto();
        BeanUtil.copyProperties(student, studentDto);
        return studentDto;
    }

    /**
     * 功能描述: 用户的最新数据提交
     *
     * @param student 学生信息
     * @return java.util.Map<java.lang.Integer, com.marchsoft.entity.dto.StudentDto>
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @Override
    public Map<Integer, StudentDto> submit(Students student) {
        Map<Integer, StudentDto> map = new HashMap<>();
        Integer statusSure = 1;
        Integer unStatusSure = 0;
        //判断学生是否已经报道
        StudentDto judgeStatusSure = this.judgeStatus(studentsMapper, student.getCardId());
        if (judgeStatusSure != null) {
            map.put(statusSure, judgeStatusSure);
            return map;
        }
        //设置未报到学生的信息
        student.setStatus(statusSure);
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", student.getCardId());
        studentsMapper.update(student, queryWrapper);
        Students studentMessage = studentsMapper.selectOne(queryWrapper);
        StudentDto studentDto = new StudentDto();
        BeanUtil.copyProperties(studentMessage, studentDto);
        map.put(unStatusSure, studentDto);
        return map;
    }

    /**
     * 功能描述: 查找管理员本院已经报到没有注册人员
     *
     * @param key  关键字
     * @param type /
     * @return java.util.List<com.marchsoft.entity.Students>
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @Override
    public List<Students> selByKey(String key, String type) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("conStatus", 0);
        queryWrapper.like("academy", type);
        if ((key != null)) {
            if (key.contains("-")) {
                key = key.replace("-", "#");
            }
            String finalKey = key;
            //使用and嵌套
            queryWrapper.and(Wrapper -> Wrapper.like("name", finalKey).or().like("phone_num", finalKey).or().like("classes", finalKey));
        }
        List<Students> list = list(queryWrapper);
        return list;
    }

    /**
     * 功能描述: 确认注册
     *
     * @param cardId 证件号
     * @return int
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @Override
    public int updStatus(String cardId) {
        log.info("【需要更新的状态身份证号：】" + cardId);
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        Students students = new Students();
        students.setConStatus(1);
        students.setCreatedAt(LocalDateTime.now());
        return studentsMapper.update(students, queryWrapper);
    }

    /**
     * 功能描述: 人员检索查询管理员所在学院所有新生
     *
     * @param key  关键字
     * @param type 管理员学院
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.marchsoft.entity.Students>
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @Override
    public IPage<Students> selRegAllStu(String key, String type) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        //MTYwKMjExMTk2MjkxMQ==
        queryWrapper.like("academy", type);
        if (key != null) {
            if (key.contains("-")) {
                key = key.replace("-", "#");
            }
            if (key.equals("已报到")) {
                queryWrapper.eq("conStatus", 1);
            } else if (key.equals("未报到")) {
                queryWrapper.eq("conStatus", 0);
            } else {
                String finalKey = key;
                //使用and嵌套
                queryWrapper.and(Wrapper -> Wrapper.like("name", finalKey).or().like("phone_num", finalKey).or().like("classes", finalKey));
            }
        }
        Page<Students> page = new Page<>(0, 1000);
        return studentsMapper.selectPage(page, queryWrapper);
    }

    /**
     * 功能描述: 查询报到数据
     *
     * @param sex          性别
     * @param type         0查询学院 1查询专业 2 查询班级
     * @param academy      学院名
     * @param batch        /
     * @param postgraduate /
     * @return java.util.List<com.marchsoft.entity.dto.RegistDataDto>
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @Override
    public List<RegistDataDto> findRegisterData(Integer sex, Integer type, String academy, Integer batch, Integer postgraduate) {
        String sexStr = null;
        String typeStr = null;
        //判断性别
        if (sex == 0) {
            sexStr = "女";
        } else if (sex == 1) {
            sexStr = "男";
        }
        if (type != null) {
            if (type == 0) {
                typeStr = "academy";
            } else if (type == 1) {
                typeStr = "major_short_name";
            } else if (type == 2) {
                typeStr = "classes";
            }
        }
        List<RegistDataDto> registerData = studentsMapper.findRegisterData(sexStr, typeStr, academy, batch,postgraduate);
        List<RegistDataDto> academyData = new ArrayList<>();
        String [] academyNames={
                "生命科技学院","经济与管理学院","机电学院",
                "食品学院","动物科技学院","园艺园林学院",
                "资源与环境学院","信息工程学院","化学化工学院",
                "文法学院","教育科学学院","艺术学院","服装学院",
                "数学科学学院","外国语学院", "体育学院",
                "人工智能学院","国际教育学院"
        };
        if (type != null) {
            //学院按上面集合顺序排序
            if (type == 0) {
                for (int i = 0; i < academyNames.length; i++) {
                    for (RegistDataDto data : registerData) {
                        if (academyNames[i].equals(data.getName())) {
                            academyData.add(data);
                        }
                    }
                }
                return academyData;
            }
        }
        return registerData;
    }

    @Override
    public List<StudentProfileDto> showStudentByCollegeAcademyClass(Integer type, String key, Integer status, Integer sex) {
        if (type != null && key != null && !key.equals("")) {
            key = "%" + key + "%";
            return studentsMapper.sqlStudentByCollegeAcademyClass(type, key, status, sex);
        }
        return null;
    }

    @Override
    public List<RecentRegisterDataDto> findRecentData(int num) {
        return studentsMapper.findRecentData(num);
    }

    @Override
    public Map<Integer, List<TimeRegisterDataDto>> getTimeRegisterNum() {
        Map<Integer, List<TimeRegisterDataDto>> batchMap = new HashMap<>(16);
        int batchNum = 3;
        for (int i = 1; i <= batchNum; i++) {
            batchMap.put(i, studentsMapper.getTimeRegisterNum(i));
        }
        return batchMap;
    }

    @Override
    public List<AddressDataDto> getAddressAndLatAndLng(int second) {
        return studentsMapper.getAddress(second);
    }

    @Override
    public List<AddressDataDto> getAllLatAndLng() {
        return  studentsMapper.getAllLatAndLng();
    }

    /**
     * 功能描述: 判断学生是否报道
     *
     * @param studentsMapper mapper
     * @param cardId         当前报道者的身份证号
     * @return com.marchsoft.entity.dto.StudentDto
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    private StudentDto judgeStatus(StudentsMapper studentsMapper, String cardId) {
        //确认报道  1
        Integer statusSure = 1;
        //查询条件
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        Students students = studentsMapper.selectOne(queryWrapper);
        Integer status = students.getStatus();
        if (status.equals(statusSure)) {
            StudentDto studentDto = new StudentDto();
            BeanUtil.copyProperties(students, studentDto);
            return studentDto;
        } else {
            return null;
        }
    }
}

