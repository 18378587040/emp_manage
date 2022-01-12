package com.zgm.server.service.impl;

import com.zgm.server.pojo.User;
import com.zgm.server.mapper.UserMapper;
import com.zgm.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工用户表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
