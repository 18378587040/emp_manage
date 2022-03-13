package com.zgm.server.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zgm.server.pojo.Leave;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Transdepart;
import com.zgm.server.pojo.Transsalary;
import com.zgm.server.service.ITransdepartService;
import com.zgm.server.service.ITranssalaryService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 调薪申请表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
@Api(tags = "薪资调动模块")
@RestController
public class TranssalaryController {
    @Autowired
    private ITranssalaryService transsalaryService;

    /**
     * 修改审批状态
     * @return
     */
    @ApiOperation(value = "修改审批状态")
    @PutMapping("/admin/approve/transsalary")
    public Result updateTransDepartStatus(Integer id, Integer status) {
        if (transsalaryService.update(new UpdateWrapper<Transsalary>().set("status", status).eq("id", id))) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    /**
     * 查询薪资调动申请
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询薪资调动申请")
    @GetMapping("/admin/transsalarys")
    public PageResult<Leave> listSalarys(ConditionVO conditionVO) {
        return transsalaryService.listTransSalary(conditionVO);
    }

    /**
     * 保存或更新申请
     * @return
     */
    @ApiOperation(value = "保存或更新申请")
    @PostMapping("/admin/transsalarys")
    public Result saveOrUpdateTransSalary(@RequestBody Transsalary transsalary) {
        transsalaryService.saveOrUpdateTransSalary(transsalary);
        return Result.success("");
    }


    /**
     * 撤回申请
     * @param transSalaryIdList
     * @return
     */
    @ApiOperation(value = "撤回申请")
    @DeleteMapping("/admin/transsalarys")
    public Result deleteTransSalary(@RequestBody List<Integer> transSalaryIdList) {
        transsalaryService.deleteTransSalary(transSalaryIdList);
        return Result.success("");
    }
}
