package com.marchsoft.mapper;

import com.marchsoft.base.BasicMapper;
import com.marchsoft.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jiaoqianjin
 * @since 2021-07-26
 */
@Component
public interface StudentsMapper extends BasicMapper<Students> {

    /**
     * fetch
     *
     * @param sexStr       性别
     * @param type         0查询学院 1查询专业 2 查询班级
     * @param academy      学院名
     * @param postgraduate 是否为研究生
     * @param batch        批次
     * @return java.util.List<com.marchsoft.api.dto.RegistDataDto>
     * @Description 查询报到数据
     * @Data 17:48 2020/10/6
     */
    @Select({
            "<script>",
            "SELECT",
            "<when test = \"type!=null\">",
            "<if test = \"type!='major_short_name'\">",
            "${type} name,",
            "</if>",
            "<if test = \"type=='major_short_name'\">",
            "major name,",
            "</if>",
            //名称
            "</when>",
            "<when test = \"type=='major_short_name'\">",
            "${type} majorShortName,",//名称
            "</when>",
            "COUNT(CASE WHEN conStatus = 1 THEN conStatus END) register_num,",//报道人数
            "COUNT(0) all_num,",//总录取人数
            "FORMAT(COUNT(CASE WHEN conStatus = 1 THEN conStatus END)/COUNT(*)*100,0) register_rate",//报到率
            "FROM students",
            "<where>",
            "<if test='sexStr!=null'>",//判断性别
            "and sex = '${sexStr}'",
            "</if>",
            "<if test='academy != null'>",//判断学院
            "and academy ='${academy}'",
            "</if>",
            "<if test='batch != null'>",//判断批次
            "and batch ='${batch}'",
            "</if>",
            "<choose>",//判断研究生学院
            "<when test=\"postgraduate==0\">and academy not like \"研究生学院\"</when>",
            "<when test=\"postgraduate==1\">and academy  like \"研究生学院\"</when>",
            "</choose>",
            "</where>",
            "<when test = 'type!=null'>",//判断类别
            "GROUP BY ${type}",
            "</when>",
            "<when test = \"type=='major_short_name'\">",//判断类别
            "order BY academy desc",
            "</when>",
            "</script>"
    })
    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "register_num", property = "register_num"),
            @Result(column = "all_num", property = "all_num"),
            @Result(column = "classes", property = "classes"),
            @Result(column = "register_rate", property = "register_rate"),
            @Result(column = "majorShortName", property = "majorShortName")
    })
    List<RegistDataDto> findRegisterData(@Param("sexStr") String sexStr,
                                         @Param("type") String type,
                                         @Param("academy") String academy,
                                         @Param("batch") Integer batch,
                                         @Param("postgraduate") Integer postgraduate);

    /**
     * fetch
     *
     * @param type   0查询学院 1查询专业 2 查询班级
     * @param key    学院/专业简称
     * @param status 1 0查询全部 2查询未报到人
     * @param sex    0 女生 1 男生 2 所有
     * @return java.util.List<com.marchsoft.api.dto.StudentProfileDto>
     * @Description 根据学院/专业/班级 查询已注册学生信息
     * @Data 17:40 2020/10/6
     */
    @Select({"<script>",
            "SELECT id,name,sex,classes FROM students",
            "<where>",
            "<if test=\"status!=null\">",
            "<choose>",
            "<when test=\"status==1\">AND conStatus=1</when>",
            "<when test=\"status==2\">AND conStatus=0</when>",
            "</choose>",
            "</if>",
            "<if test=\"sex!=null\">",
            "<choose>",
            "<when test=\"sex==1\">AND sex LIKE '男'</when>",
            "<when test=\"sex==0\">AND sex LIKE '女'</when>",
            "</choose>",
            "</if>",
            "<if test=\"type!=null and search!=null\">",
            "<choose>",
            "<when test=\"type==0\">AND academy</when>",
            "<when test=\"type==1\">AND major</when>",
            "<otherwise>AND classes</otherwise>",
            "</choose>",
            "LIKE #{search}",
            "</if>",
            "</where>",
            "</script>"
    })
    List<StudentProfileDto> sqlStudentByCollegeAcademyClass(Integer type, @Param("search") String key, Integer status, Integer sex);

    /**
     * fetch
     *
     * @param num 返回数据数量
     * @return java.util.List<com.marchsoft.api.dto.RecentRegisterDataDto>
     * @Description 获取最近几条的报道数据
     * @Data 20:35 2020/10/6
     */
    @Select("SELECT" +
            " name,created_at,classes" +
            " FROM students" +
            " WHERE conStatus = 1" +
            " ORDER BY created_at DESC" +
            " LIMIT 0,#{num}")
    List<RecentRegisterDataDto> findRecentData(int num);

    /**
     * fetch
     *
     * @param batch 批次
     * @return java.util.List<com.marchsoft.api.dto.TimeRegisterDataDto>
     * @Description 获取每小时研究生报道人数
     * @Data 20:34 2020/10/6
     */
    @Select("SELECT " +
            "HOUR(created_at) AS HOUR," +
            "COUNT(0) AS RegisterNum," +
            "batch " +
            "FROM students s " +
            "WHERE HOUR(created_at) IS NOT NULL " +
            "and conStatus = 1 " +
            "and batch = #{batch} " +
            "and (academy like \"研究生学院\") " +
            "GROUP BY HOUR(s.created_at) ORDER BY (hour(s.created_at))")
    List<TimeRegisterDataDto> getTimeRegisterNum(int batch);

    /**
     * fetch
     *
     * @param second 间隔时间
     * @return java.util.List<com.marchsoft.api.dto.AddressDataDto>
     * @Description 获取最近一段时间内的报到地址
     * @Data 23:12 2020/10/6
     */

    @Select("SELECT address" +
            " FROM students" +
            " WHERE created_at" +
            " BETWEEN DATE_ADD(NOW(), INTERVAL - #{second} SECOND)" +
            " AND NOW();")
    List<AddressDataDto> getAddress(int second);

    /**
     * 功能描述: 获取所有数据的经纬度
     *
     * @return java.util.List<com.marchsoft.entity.AddressDataDto>
     * @author jiaoqianjin
     * @date 2021/7/29
     */
    @Select("SELECT distinct concat(SUBSTRING_INDEX(address,'市',1),'市') address from students")
    List<AddressDataDto> getAllLatAndLng();
}
