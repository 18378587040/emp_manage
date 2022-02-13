package com.zgm.server.mapper;

import com.zgm.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户菜单表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据角色获取菜单列表
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 根据用户id查询菜单
     * @param id
     * @return 菜单列表
     */
    List<Menu> listMenusByUserInfoId(Integer id);
}
