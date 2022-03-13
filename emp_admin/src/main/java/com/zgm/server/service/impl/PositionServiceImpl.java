package com.zgm.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgm.server.constant.CommonConst;
import com.zgm.server.exception.BizException;
import com.zgm.server.pojo.Position;
import com.zgm.server.mapper.PositionMapper;
import com.zgm.server.service.IPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgm.server.utils.PageUtils;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 员工职位表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public PageResult<Position> listPositions(ConditionVO conditionVO) {
        List<Position> positionList = positionMapper.listPositions(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        // 查询总量
        Integer count = positionMapper.selectCount(new LambdaQueryWrapper<Position>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Position::getName, conditionVO.getKeywords()));
        return new PageResult<>(positionList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdatePosition(Position position) {
        // 判断职位名是否存在
        Position existPosition = positionMapper.selectOne(new LambdaQueryWrapper<Position>()
                .select(Position::getId)
                .eq(Position::getName, position.getName()));
        if (Objects.nonNull(existPosition) && !existPosition.getId().equals(position.getId())) {
            throw new BizException("职位名已存在");
        }
        // 保存或更新职位信息
//        Position positionInfo = Position.builder()
//                .id(position.getId())
//                .name(position.getName())
//                .isEnabled(CommonConst.TRUE)
//                .build();
        this.saveOrUpdate(position);
    }

    @Override
    public void deletePosition(List<Integer> positionIdList) {
        positionMapper.deleteBatchIds(positionIdList);
    }
}
