package com.zgm.server.controller;


import com.zgm.server.dto.RoleDTO;
import com.zgm.server.dto.UserRoleDTO;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Role;
import com.zgm.server.service.IRoleService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import com.zgm.server.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
@Api(tags = "角色模块")
@RestController
public class RoleController {
    @Autowired
    private IRoleService roleService;

    /**
     * 查询用户角色选项
     * @return
     */
    @ApiOperation(value = "查询用户角色选项")
    @GetMapping("/admin/users/role")
    public List<UserRoleDTO> listUserRoles() {
        return roleService.listUserRoles();
    }

    /**
     * 查询角色列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询角色列表")
    @GetMapping("/admin/roles")
    public PageResult<RoleDTO> listRoles(ConditionVO conditionVO) {
        return roleService.listRoles(conditionVO);
    }

    /**
     * 保存或更新角色
     * @param roleVO
     * @return
     */
    @ApiOperation(value = "保存或更新角色")
    @PostMapping("/admin/role")
    public Result saveOrUpdateRole(@RequestBody @Valid RoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return Result.success("");
    }

    /**
     * 删除角色
     * @param roleIdList
     * @return
     */
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/admin/roles")
    public Result deleteRoles(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return Result.success("");
    }
}
