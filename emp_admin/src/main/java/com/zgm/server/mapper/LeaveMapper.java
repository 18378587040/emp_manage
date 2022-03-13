package com.zgm.server.mapper;

import com.zgm.server.pojo.Leave;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgm.server.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 请假申请表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
public interface LeaveMapper extends BaseMapper<Leave> {

    /**
     * 查询请假申请
     * @param current
     * @param size
     * @param conditionVO
     * @return
     */
    List<Leave> listLeaves(@Param("current") Long current, @Param("size") Long size, ConditionVO conditionVO);
}
