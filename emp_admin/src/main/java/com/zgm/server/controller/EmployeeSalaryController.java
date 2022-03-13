package com.zgm.server.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zgm.server.pojo.Employee;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Salary;
import com.zgm.server.service.IEmployeeService;
import com.zgm.server.service.ISalaryService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 薪资账套表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-03-10
 */
@Api(tags = "员工薪资模块")
@RestController
@RequestMapping("/admin/salary")
public class EmployeeSalaryController {
    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IEmployeeService employeeService;

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
     * 获取所有员工账套
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "获取所有员工账套")
    @GetMapping("/employee")
    public PageResult<Employee> getEmployeeSalary(ConditionVO conditionVO) {
        return employeeService.getEmployeeSalary(conditionVO);
    }

    /**
     * 更新员工账套
     * @param eid
     * @param sid
     * @return
     */
    @ApiOperation(value = "更新员工账套")
    @PutMapping("/")
    public Result updateEmployeeSalary(Integer eid, Integer sid) {
        if (employeeService.update(new UpdateWrapper<Employee>().set("salary_id", sid).eq("id", eid))) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
}
