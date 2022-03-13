package com.zgm.server.service;

import com.zgm.server.pojo.Leave;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgm.server.pojo.Result;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;

import java.util.List;

/**
 * <p>
 * 请假申请表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
public interface ILeaveService extends IService<Leave> {

    /**
     * 查询请假申请
     * @return
     */
    PageResult<Leave> listLeaves(ConditionVO conditionVO);

    /**
     * 撤回申请
     * @param leaveIdList
     * @return
     */
    void deleteLeave(List<Integer> leaveIdList);

    /**
     * 保存或更新申请s
     * @param leave
     */
    void saveOrUpdateLeave(Leave leave);
}
