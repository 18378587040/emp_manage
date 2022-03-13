package com.zgm.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgm.server.pojo.Leave;
import com.zgm.server.mapper.LeaveMapper;
import com.zgm.server.service.ILeaveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgm.server.utils.PageUtils;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 请假申请表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements ILeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public PageResult<Leave> listLeaves(ConditionVO conditionVO) {
        List<Leave> leaveList =  leaveMapper.listLeaves(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        // 查询总量
        Integer count = leaveMapper.selectCount(new LambdaQueryWrapper<Leave>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Leave::getName, conditionVO.getKeywords()));
        return new PageResult<>(leaveList, count);
    }

    @Override
    public void deleteLeave(List<Integer> leaveIdList) {
        leaveMapper.deleteBatchIds(leaveIdList);
    }

    @Override
    public void saveOrUpdateLeave(Leave leave) {
        this.saveOrUpdate(leave);
    }
}
