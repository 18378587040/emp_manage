package com.zgm.server.service.impl;

import com.zgm.server.pojo.Menu;
import com.zgm.server.mapper.MenuMapper;
import com.zgm.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户菜单表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }
}
