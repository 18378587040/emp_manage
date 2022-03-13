package com.zgm.server.mapper;

import com.zgm.server.pojo.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgm.server.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工职位表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
public interface PositionMapper extends BaseMapper<Position> {

    /**
     * 查询职位列表
     * @param current
     * @param size
     * @param conditionVO
     * @return
     */
    List<Position> listPositions(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);
}
