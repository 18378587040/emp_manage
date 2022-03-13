package com.zgm.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工部门表
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_depart")
@ApiModel(value="Depart对象", description="员工部门表")
public class Depart implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "部门ID")
    private Integer id;

    @ApiModelProperty(value = "部门名称")
    @NonNull
    @Excel(name = "部门名称")
    private String name;

    @ApiModelProperty(value = "父id")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "部门路径")
    @TableField("dep_path")
    private String depPath;

    @ApiModelProperty(value = "是否启用 0否 1是")
    @TableField("is_enabled")
    private Integer isEnabled;

    @ApiModelProperty(value = "是否上级部门 0否 1是")
    @TableField("is_parent")
    private Integer isParent;

//    @ApiModelProperty(value = "创建时间")
//    @TableField(value = "create_time", fill = FieldFill.INSERT)
//    private LocalDateTime createTime;
//
//    @ApiModelProperty(value = "更新时间")
//    @TableField(value = "update_time", fill = FieldFill.INSERT)
//    private LocalDateTime updateTime;

    @ApiModelProperty(value = "子部门列表")
    @TableField(exist = false)
    private List<Depart> children;

    @ApiModelProperty(value = "返回结果，存储过程使用")
    @TableField(exist = false)
    private Integer result;
}
