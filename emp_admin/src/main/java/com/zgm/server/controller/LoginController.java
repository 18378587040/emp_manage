package com.zgm.server.controller;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.UserLoginVO;
import com.zgm.server.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "登录模块")
@RestController
public class LoginController {
    @Autowired
    private IUserService userService;


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
