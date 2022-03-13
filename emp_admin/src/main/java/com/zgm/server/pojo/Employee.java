package com.zgm.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author ming
 * @since 2022-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee")
@RequiredArgsConstructor
@ApiModel(value="Employee对象", description="员工表")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工姓名")
    @Excel(name = "员工姓名")
    private String name;

    @ApiModelProperty(value = "工号")
    @TableField("work_id")
    @Excel(name = "工号")
    private String workId;

    @ApiModelProperty(value = "性别")
    @Excel(name = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    @Excel(name = "年龄")
    private Integer age;

    @ApiModelProperty(value = "邮箱")
    @Excel(name = "邮箱")
    private String email;

    @ApiModelProperty(value = "地址")
    @Excel(name = "地址")
    private String adress;

    @ApiModelProperty(value = "手机号码")
    @Excel(name = "手机号码")
    private String phone;

    @ApiModelProperty(value = "部门id")
    @TableField("depart_id")
    private Integer departId;

    @ApiModelProperty(value = "职位id")
    @TableField("position_id")
    private Integer positionId;

    @ApiModelProperty(value = "职称id")
    @TableField("jobtitle_id")
    private Integer jobtitleId;

    @ApiModelProperty(value = "薪资id")
    @TableField("salary_id")
    private Integer salaryId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "部门")
    @TableField(exist = false)
    @ExcelEntity(name = "部门")
    private Depart depart;

    @ApiModelProperty(value = "职位等级")
    @TableField(exist = false)
    @ExcelEntity(name = "职位等级")
    private Jobtitle jobtitle;

    @ApiModelProperty(value = "职位")
    @TableField(exist = false)
    @ExcelEntity(name = "职位")
    private Position position;

    @ApiModelProperty(value = "工资账套")
    @TableField(exist = false)
    private Salary salary;

}
