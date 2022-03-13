package com.zgm.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zgm.server.mapper.RoleMapper;
import com.zgm.server.mapper.UserMapper;
import com.zgm.server.pojo.Role;
import com.zgm.server.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户详细信息服务
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
//            throw new BizException("用户名不能為空");
            return null;
        }
        // 查询账号是否存在
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
        .select(User::getId, User::getUsername, User::getPassword)
        .eq(User::getUsername, username));
        if (Objects.isNull(user)) {
//            throw new BizException("用户名不存在");
            return null;
        }
        // 查询账号角色
        List<String> roleList = roleMapper.listRolesByUserId(user.getId());
        user.setRoles(roleList);
        // 封装登录信息
        return user;
    }
}
