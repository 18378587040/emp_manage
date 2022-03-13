package com.zgm.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.concurrent.BlockingDeque;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工职位表
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@TableName("t_position")
@ApiModel(value="Position对象", description="员工职位表")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "职位ID")
    private Integer id;

    @ApiModelProperty(value = "职位名")
    @NonNull
    @Excel(name = "职位名")
    private String name;

    @ApiModelProperty(value = "是否启用")
    @TableField("is_enabled")
    private Integer isEnabled;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT)
    private LocalDateTime updateTime;


}
