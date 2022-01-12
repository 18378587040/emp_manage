package com.zgm.server.service.impl;

import com.zgm.server.config.security.handler.JwtTokenUtil;
import com.zgm.server.mapper.RoleMapper;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.User;
import com.zgm.server.mapper.UserMapper;
import com.zgm.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
        String captcha = (String) request.getSession().getAttribute("captcha");
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
}
