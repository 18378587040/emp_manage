package com.zgm.server.service;

import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Role;
import com.zgm.server.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Authorization;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 获取当前登录用户的信息
     * @param username
     * @return
     */
    User getAdminByUserName(String username);

    /**
     * 根据用户id获取角色列表
     * @param id
     * @return
     */
    List<String> getRoles(Integer id);

    /**
     * 更新用户密码
     * @param oldPass
     * @param pass
     * @param userId
     * @return
     */
    Result updateAdminPassword(String oldPass, String pass, Integer userId);

    /**
     * 更新用户头像
     */
    void updataUserFace(String url, Authentication authentication);
}
