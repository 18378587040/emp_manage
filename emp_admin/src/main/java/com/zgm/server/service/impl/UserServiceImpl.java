package com.zgm.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zgm.server.config.security.handler.JwtTokenUtil;
import com.zgm.server.mapper.RoleMapper;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Role;
import com.zgm.server.pojo.User;
import com.zgm.server.mapper.UserMapper;
import com.zgm.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgm.server.utils.UserUtils;
import io.swagger.annotations.Authorization;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;

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

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public Result login(String username, String password, String code, HttpServletRequest request) {
        // 登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //        String captcha = (String) request.getSession().getAttribute("captcha");
        String captcha = "abcd";
        if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            return Result.error("验证码输入错误，请重新输入！");
        }
        if(null==userDetails || !passwordEncoder.matches(password,userDetails.getPassword())){
            return Result.error("用户名或密码不正确");
        }
        if(!userDetails.isEnabled()) {
            return Result.error("账号被锁定，请联系管理员！");
        }
        // 更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return Result.success("登录成功", tokenMap);
    }

    @Override
    public User getAdminByUserName(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public List<String> getRoles(Integer id) {
        return roleMapper.listRolesByUserId(id);
    }

    @Override
    public Result updateAdminPassword(String oldPass, String pass, Integer userId) {
        User user = userMapper.selectById(userId);
        // springSecurity加密了密码，所以要解码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 比较输入的密码跟老密码是否一致，一致才能修改
        if (encoder.matches(oldPass, user.getPassword())) {
            // 加密新密码
            String newPassword = encoder.encode(pass);
            user.setPassword(newPassword);
            if (userMapper.updateById(user) == 1) {
                return Result.success("密码修改成功");
            }
            return Result.error("密码修改失败");
        }
        return Result.error("密码输入错误");
    }

    @Override
    public void updataUserFace(String url, Authentication authentication) {
        User user = userMapper.selectById(UserUtils.getLoginUser().getId());
        user.setUserFace(url);
        int result = userMapper.updateById(user);
        if (result == 1) {
            // 每次更新成功后都要重新设置Authentication对象
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null,authentication.getAuthorities()));
        }
    }
}
