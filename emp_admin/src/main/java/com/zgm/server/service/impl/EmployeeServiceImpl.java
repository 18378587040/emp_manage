package com.zgm.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zgm.server.pojo.Employee;
import com.zgm.server.mapper.EmployeeMapper;
import com.zgm.server.pojo.Jobtitle;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Salary;
import com.zgm.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgm.server.utils.PageUtils;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-03-10
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public PageResult<Employee> getEmployeeSalary(ConditionVO conditionVO) {
        List<Employee> employeeList = employeeMapper.listEmployeeSalary(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        // 查询总量
        Integer count = employeeMapper.selectCount(new LambdaQueryWrapper<Employee>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Employee::getName, conditionVO.getKeywords()));
        return new PageResult<>(employeeList, count);
    }

    @Override
    public PageResult<Employee> listEmployees(ConditionVO conditionVO) {
        List<Employee> employeeList = employeeMapper.listEmployees(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        // 查询总量
        Integer count = employeeMapper.selectCount(new LambdaQueryWrapper<Employee>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Employee::getName, conditionVO.getKeywords()));
        return new PageResult<>(employeeList, count);
    }

    @Override
    public void saveOrUpdateEmployees(Employee employee) {
        this.saveOrUpdate(employee);
    }

    @Override
    public void deleteEmployees(List<Integer> employeeIdList) {
        employeeMapper.deleteBatchIds(employeeIdList);
    }

    @Override
    public Result maxWorkID() {
        // 通过sql语句查询到最后一个workId
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(work_id)"));
        // 将查询到的最后一个workId + 1
        int maxWorkId = Integer.parseInt(maps.get(0).get("max(work_id)").toString()) + 1;
        // 此格式化目的是不让前面的零消失，比如00000101，如果不设置此格式化，输出结果就为101
        String formatMaxWorkId = String.format("%08d", maxWorkId);
        return Result.success(null,formatMaxWorkId);
    }

    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }
}
