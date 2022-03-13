package com.zgm.server.service;

import com.zgm.server.pojo.Position;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;

import java.util.List;

/**
 * <p>
 * 员工职位表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
public interface IPositionService extends IService<Position> {

    /**
     * 查询职位列表
     * @param conditionVO
     * @return
     */
    PageResult<Position> listPositions(ConditionVO conditionVO);

    /**
     * 保存或更新职位
     * @param position
     */
    void saveOrUpdatePosition(Position position);

    /**
     * 删除职位
     * @param positionIdList
     */
    void deletePosition(List<Integer> positionIdList);
}
