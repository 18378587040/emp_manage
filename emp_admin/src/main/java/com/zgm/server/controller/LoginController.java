package com.zgm.server.controller;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.User;
import com.zgm.server.service.IUserService;
import com.zgm.server.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Api(tags = "登录模块")
@RestController
public class LoginController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public User getAdminInfo(Principal principal) {
        if (null == principal){
            return null;
        }
        String username = principal.getName();
        User user = userService.getAdminByUserName(username);
        user.setPassword(null);
        user.setRoles(userService.getRoles(user.getId()));
        return user;
    }

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginVO userLoginVO, HttpServletRequest request){
        return userService.login(userLoginVO.getUsername(), userLoginVO.getPassword(),userLoginVO.getCode(),request);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public Result logout(){
        return Result.success("注销成功!");
    }

}
