package com.zgm.server.service;

import com.zgm.server.dto.LabelOptionDTO;
import com.zgm.server.dto.UserMenuDTO;
import com.zgm.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户菜单表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据角色获取菜单列表
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询菜单列表
     * @return
     */
    List<Menu> listMenus();

    /**
     * 查看角色菜单选项
     *
     * @return 角色菜单选项
     */
    List<LabelOptionDTO> listMenuOptions();

    /**
     * 查看用户菜单
     *
     * @return 菜单列表
     */
    List<UserMenuDTO> listUserMenus();

}
