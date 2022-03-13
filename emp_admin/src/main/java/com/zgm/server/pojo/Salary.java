package com.zgm.server.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 薪资账套表
 * </p>
 *
 * @author ming
 * @since 2022-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_salary")
@ApiModel(value="Salary对象", description="薪资账套表")
public class Salary implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "基本工资")
    @TableField("basic_salary")
    private Integer basicSalary;

    @ApiModelProperty(value = "奖金")
    private Integer bonus;

    @ApiModelProperty(value = "午餐补助")
    @TableField("lunch_salary")
    private Integer lunchSalary;

    @ApiModelProperty(value = "交通补助")
    @TableField("traffic_salary")
    private Integer trafficSalary;

    @ApiModelProperty(value = "应发工资")
    @TableField("all_salary")
    private Integer allSalary;

    @ApiModelProperty(value = "养老金基数")
    @TableField("pension_base")
    private Integer pensionBase;

    @ApiModelProperty(value = "养老金比率")
    @TableField("pension_per")
    private Float pensionPer;

    @ApiModelProperty(value = "医疗基数")
    @TableField("medical_base")
    private Integer medicalBase;

    @ApiModelProperty(value = "医疗保险比率")
    @TableField("medical_per")
    private Float medicalPer;

    @ApiModelProperty(value = "公积金基数")
    @TableField("accumulation_fund_base")
    private Integer accumulationFundBase;

    @ApiModelProperty(value = "公积金比率")
    @TableField("accumulation_fund_per")
    private Float accumulationFundPer;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT)
    private LocalDateTime updateTime;


}
