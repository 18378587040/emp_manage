package com.zgm.server.controller;


import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Salary;
import com.zgm.server.service.ISalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 薪资账套表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-03-10
 */
@Api(tags = "账套管理模块")
@RestController
@RequestMapping("/admin/salarymanage")
public class SalaryController {
    @Autowired
    private ISalaryService salaryService;

    /**
     * 获取所有工资账套
     * @return
     */
    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/")
    public List<Salary> getAllSalaries() {
        return salaryService.list();
    }

    /**
     * 添加工资账套
     * @param salary
     * @return
     */
    @ApiOperation(value = "添加工资账套")
    @PostMapping("/")
    public Result addSalary(@RequestBody Salary salary) {

        if (salaryService.save(salary)) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    /**
     * 更新工资账套
     * @param salary
     * @return
     */
    @ApiOperation(value = "更新工资账套")
    @PutMapping("/")
    public Result updateSalary(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    /**
     * 删除工资账套
     * @param id
     * @return
     */
    @ApiOperation(value = "删除工资账套")
    @DeleteMapping("/{id}")
    public Result deleteSalary(@PathVariable Integer id) {
        if (salaryService.removeById(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
