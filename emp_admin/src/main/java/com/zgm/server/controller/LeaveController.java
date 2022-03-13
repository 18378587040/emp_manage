package com.zgm.server.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rabbitmq.client.Return;
import com.zgm.server.pojo.Jobtitle;
import com.zgm.server.pojo.Leave;
import com.zgm.server.pojo.Result;
import com.zgm.server.service.ILeaveService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.UID;
import java.util.List;

/**
 * <p>
 * 请假申请表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
@RestController
@Api(tags = "请假申请模块")
public class LeaveController {
    @Autowired
    private ILeaveService leaveService;

    /**
     * 修改审批状态
     * @return
     */
    @ApiOperation(value = "修改审批状态")
    @PutMapping("/admin/approve/leave")
    public Result updateLeaveStatus(Integer id, Integer status) {
        if (leaveService.update(new UpdateWrapper<Leave>().set("status", status).eq("id", id))) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    /**
     * 查询请假申请
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询请假申请")
    @GetMapping("/admin/leaves")
    public PageResult<Leave> listLeaves(ConditionVO conditionVO) {
        return leaveService.listLeaves(conditionVO);
    }

    /**
     * 保存或更新申请
     * @return
     */
    @ApiOperation(value = "保存或更新申请")
    @PostMapping("/admin/leaves")
    public Result saveOrUpdateLeave(@RequestBody Leave leave) {
        leaveService.saveOrUpdateLeave(leave);
        return Result.success("");
    }


    /**
     * 撤回申请
     * @param leaveIdList
     * @return
     */
    @ApiOperation(value = "撤回申请")
    @DeleteMapping("/admin/leaves")
    public Result deleteLeave(@RequestBody List<Integer> leaveIdList) {
        leaveService.deleteLeave(leaveIdList);
        return Result.success("");
    }
}
