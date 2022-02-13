package com.zgm.server.controller;


import com.zgm.server.dto.LabelOptionDTO;
import com.zgm.server.dto.UserMenuDTO;
import com.zgm.server.pojo.Menu;
import com.zgm.server.pojo.Result;
import com.zgm.server.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户菜单表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
@Api(tags = "菜单模块")
@RestController
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "查看菜单列表")
    @GetMapping("/admin/menus")
    public List<Menu> listMenus() {
        return menuService.listMenus();
    }

    /**
     * 查看角色菜单选项
     * @return
     */
    @ApiOperation(value = "查看角色菜单选项")
    @GetMapping("/admin/role/menus")
    public List<LabelOptionDTO> listMenuOptions() {
        return menuService.listMenuOptions();
    }

    /**
     * 查看当前用户菜单
     *
     * @return
     */
    @ApiOperation(value = "查看当前用户菜单")
    @GetMapping("/admin/user/menus")
    public List<UserMenuDTO> listUserMenus() {
        return menuService.listUserMenus();
    }
}
