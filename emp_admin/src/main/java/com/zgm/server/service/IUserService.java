package com.zgm.server.service;

import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 员工用户表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
public interface IUserService extends IService<User> {

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @return
     */
    Result login(String username, String password, String code, HttpServletRequest request);
}
