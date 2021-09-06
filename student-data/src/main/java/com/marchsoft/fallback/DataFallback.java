package com.marchsoft.fallback;

import com.marchsoft.client.StudentClient;
import com.marchsoft.entity.*;
import com.marchsoft.response.ResponseResult;
import com.marchsoft.response.enums.ResponseEnum;
import com.marchsoft.response.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/8/9 9:25
 **/
@Component
@Slf4j
public class DataFallback implements StudentClient {
    @Override
    public Object getRankingList(Integer page, Integer size) {
        return "null";
    }

    @Override
    public ResponseResult<List<RegistDataDto>> findRegisterData(Integer sex, Integer type, String academy, Integer batch, Integer postgraduate) {
        log.error("/api/students/getMajorInfo 异常");
        throw new BaseException(ResponseEnum.ERROR);
    }

    @Override
    public List<StudentProfileDto> showStudentByCollegeAcademyClass(Integer type, String key, Integer status, Integer sex) {
        throw new BaseException(ResponseEnum.ERROR);
    }

    @Override
    public List<RecentRegisterDataDto> findRecentData(int num) {
        throw new BaseException(ResponseEnum.ERROR);
    }

    @Override
    public Map<Integer, List<TimeRegisterDataDto>> getTimeRegisterNum() {
        throw new BaseException(ResponseEnum.ERROR);
    }

    @Override
    public List<AddressDataDto> getAddressAndLatAndLng(int second) {
        throw new BaseException(ResponseEnum.ERROR);
    }

    @Override
    public List<AddressDataDto> getAllLatAndLng() {
        throw new BaseException(ResponseEnum.ERROR);
    }
}
