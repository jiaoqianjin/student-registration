package com.marchsoft.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description：
 *
 * @author jiaoqianjin
 * Date: 2021/7/26 13:51
 **/

@Data
public class StudentInfosDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "班级")
    private String classes;

    @ApiModelProperty(value = "祝福")
    private Integer wishes;

}
