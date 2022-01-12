package com.zgm.server.service.impl;

import com.zgm.server.pojo.Role;
import com.zgm.server.mapper.RoleMapper;
import com.zgm.server.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
