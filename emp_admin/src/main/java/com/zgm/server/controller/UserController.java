package com.zgm.server.controller;


import com.zgm.server.pojo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 员工用户表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @ApiOperation(value = "test")
    @PostMapping("/test")
    public Result testaa(){
        return Result.success("test!");
    }

}
