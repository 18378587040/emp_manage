package com.zgm.server.controller;


import com.zgm.server.pojo.Depart;
import com.zgm.server.pojo.Result;
import com.zgm.server.service.IDepartService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 员工部门表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
@Api(tags = "部门模块")
@RestController
public class DepartController {

    @Autowired
    private IDepartService departService;

    /**
     * 获取所有部门
     * @return
     */
    @ApiOperation(value = "获取所有部门")
    @GetMapping("/admin/departs")
    public List<Depart> listDepart(){
        return departService.listDepart();
    }

    @ApiOperation(value = "添加部门")
    @PostMapping("/admin/departs")
    public Result addDep(@RequestBody Depart depart){
        return departService.addDep(depart);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/admin/departs/{id}")
    public Result deleteDep(@PathVariable Integer id){
        return departService.deleteDep(id);
    }
}
