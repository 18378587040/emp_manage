package com.zgm.server.mapper;

import com.zgm.server.pojo.Leave;
import com.zgm.server.pojo.Transdepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgm.server.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门调动申请表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
public interface TransdepartMapper extends BaseMapper<Transdepart> {

    /**
     * 查询调动申请申请
     * @param current
     * @param size
     * @param conditionVO
     * @return
     */
    List<Leave> listTransDeparts(@Param("current") Long current, @Param("size") Long size, ConditionVO conditionVO);
}
