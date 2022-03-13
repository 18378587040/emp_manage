package com.zgm.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.zgm.server.mapper.DepartMapper;
import com.zgm.server.mapper.JobtitleMapper;
import com.zgm.server.pojo.*;
import com.zgm.server.service.IDepartService;
import com.zgm.server.service.IEmployeeService;
import com.zgm.server.service.IJobtitleService;
import com.zgm.server.service.IPositionService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 员工表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-03-10
 */
@Api(tags = "基本资料模块")
@RestController
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartService departService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IJobtitleService jobtitleService;

    /**
     * 员工基本资料列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "员工基本资料列表")
    @GetMapping("/admin/employee")
    public PageResult<Employee> listJobTitle(ConditionVO conditionVO) {
        return employeeService.listEmployees(conditionVO);
    }

    /**
     * 获取所有部门
     * @return
     */
    @ApiOperation(value = "获取所有部门")
    @GetMapping("/departs")
    public List<Depart> getAllDep(){
        return departService.listDepart();
    }

    /**
     * 获取所有职位
     * @return
     */
    @ApiOperation(value = "获取所有职位")
    @GetMapping("/positions")
    public List<Position> getAllPosition(){
        return positionService.list();
    }

    /**
     * 获取所有职称
     * @return
     */
    @ApiOperation(value = "获取所有职称")
    @GetMapping("/jobtitles")
    public List<Jobtitle> getAllJobLevel(){
        return jobtitleService.list();
    }

    /**
     * 获取工号
     * @return
     */
    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public Result xWorkID(){
        return employeeService.maxWorkID();
    }

    /**
     * 保存或更新员工基本资料
     * @param employee
     * @return
     */
    @ApiOperation(value = "保存或更新员工基本资料")
    @PostMapping("/admin/employee")
    public Result saveOrUpdateJobTitle(@RequestBody Employee employee) {
        employeeService.saveOrUpdateEmployees(employee);
        return Result.success("");
    }

    /**
     * 删除员工
     * @param employeeIdList
     * @return
     */
    @ApiOperation(value = "删除员工")
    @DeleteMapping("/admin/employee")
    public Result deleteJobTitle(@RequestBody List<Integer> employeeIdList) {
        employeeService.deleteEmployees(employeeIdList);
        return Result.success("");
    }

    /**
     * 导出员工数据
     * @param response
     */
    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response){
        List<Employee> list = employeeService.getEmployee(null);
        ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, list);
        ServletOutputStream outputStream = null;
        try {
            // 流形式
            response.setHeader("content-type", "application/octet-stream");
            // 防止中文乱码
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("员工表.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导入员工数据
     * @param file
     * @return
     */
    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    @Transactional
    public Result importEmployee(MultipartFile file) {
        ImportParams importParams = new ImportParams();
        // 设置第一行为标题行，避免系统将标题行作为数据导入
        importParams.setTitleRows(1);
//
//        List<Depart> departList = departService.list();
//        List<Jobtitle> jobtitleList = jobtitleService.list();
//        List<Position> positionList = positionService.list();
        try {
            List<Employee> employeeList = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, importParams);
            employeeList.forEach(employee -> {
                Integer departId = employee.getDepart().getId();
                Integer positionId = employee.getPosition().getId();
                Integer jobtileId = employee.getJobtitle().getId();
                // 设置departId
                employee.setDepartId(departId);
                // 设置jobTitleId
                employee.setJobtitleId(jobtileId);
                // 设置positionId
                employee.setPositionId(positionId);
                // 设置默认的薪资账套
                employee.setSalaryId(1);
            });
            if (employeeService.saveBatch(employeeList)) {
                return Result.success("员工导入成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("员工导入失败!");
    }
}
