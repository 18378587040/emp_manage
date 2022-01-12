package com.zgm.server.service.impl;

import com.zgm.server.pojo.Menu;
import com.zgm.server.mapper.MenuMapper;
import com.zgm.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
