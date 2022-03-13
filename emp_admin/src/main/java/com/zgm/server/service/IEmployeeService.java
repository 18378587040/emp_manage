package com.zgm.server.service;

import com.zgm.server.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgm.server.pojo.Jobtitle;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Salary;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;

import java.util.List;

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-03-10
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取所有员工账套
     * @param conditionVO
     * @return
     */
    PageResult<Employee> getEmployeeSalary(ConditionVO conditionVO);

    /**
     * 员工基本资料列表
     * @param conditionVO
     * @return
     */
    PageResult<Employee> listEmployees(ConditionVO conditionVO);

    /**
     * 保存或更新员工基本资料
     * @param employee
     */
    void saveOrUpdateEmployees(Employee employee);

    /**
     * 删除员工
     * @param employeeIdList
     */
    void deleteEmployees(List<Integer> employeeIdList);

    /**
     * 查出员工工号
     * @return
     */
    Result maxWorkID();

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);
}
