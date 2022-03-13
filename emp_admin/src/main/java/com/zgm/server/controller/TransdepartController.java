package com.zgm.server.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zgm.server.pojo.Leave;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Transdepart;
import com.zgm.server.service.ILeaveService;
import com.zgm.server.service.ITransdepartService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门调动申请表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
@Api(tags = "部门调动模块")
@RestController
public class TransdepartController {
    @Autowired
    private ITransdepartService transdepartService;

    /**
     * 修改审批状态
     * @return
     */
    @ApiOperation(value = "修改审批状态")
    @PutMapping("/admin/approve/transdepart")
    public Result updateTransDepartStatus(Integer id, Integer status) {
        if (transdepartService.update(new UpdateWrapper<Transdepart>().set("status", status).eq("id", id))) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    /**
     * 查询调动申请申请
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询调动申请申请")
    @GetMapping("/admin/transdeparts")
    public PageResult<Leave> listLeaves(ConditionVO conditionVO) {
        return transdepartService.listTransDepart(conditionVO);
    }

    /**
     * 保存或更新申请
     * @return
     */
    @ApiOperation(value = "保存或更新申请")
    @PostMapping("/admin/transdeparts")
    public Result saveOrUpdateTransDepart(@RequestBody Transdepart transdepart) {
        transdepartService.saveOrUpdateTransDepart(transdepart);
        return Result.success("");
    }


    /**
     * 撤回申请
     * @param transDepartIdList
     * @return
     */
    @ApiOperation(value = "撤回申请")
    @DeleteMapping("/admin/transdeparts")
    public Result deleteLeave(@RequestBody List<Integer> transDepartIdList) {
        transdepartService.deleteTransDepart(transDepartIdList);
        return Result.success("");
    }
}
