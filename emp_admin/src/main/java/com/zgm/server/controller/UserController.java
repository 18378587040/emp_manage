package com.zgm.server.controller;


import com.zgm.server.pojo.FastDFSFile;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.User;
import com.zgm.server.service.IUserService;
import com.zgm.server.utils.FastDFSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 员工用户表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
@Api(tags = "用户模块")
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 更新当前用户信息
     * @param user
     * @param authentication
     * @return
     */
    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/user/info")
    public Result updateAdminInfo(@RequestBody User user, Authentication authentication) {
        if (userService.updateById(user)) {
            // 每次更新成功后都要重新设置Authentication对象
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null,authentication.getAuthorities()));
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    /**
     * 更新用户密码
     * @param info
     * @return
     */
    @ApiOperation(value = "更新用户密码")
    @PutMapping(value = "/user/pass")
    public Result updateAdminPassword(@RequestBody Map<String,Object> info) {
        String oldPass = (String)info.get("oldPass");
        String pass = (String)info.get("pass");
        Integer userId = (Integer) info.get("userId");
        return userService.updateAdminPassword(oldPass, pass, userId);
    }

    /**
     * 修改用户头像
     * @param file
     * @return
     * @throws IOException
     * @throws MyException
     */
    @ApiOperation(value = "修改用户头像")
    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file")MultipartFile file, Authentication authentication) throws IOException, MyException {
        FastDFSFile fastDFSFile = new FastDFSFile();
        fastDFSFile.setName(file.getOriginalFilename());
        fastDFSFile.setContent(file.getBytes());
        fastDFSFile.setExt(StringUtils.getFilenameExtension(file.getOriginalFilename()));
        String[] uploads = FastDFSUtil.upload(fastDFSFile);
        String url = "http://192.168.6.131:8080/" + uploads[0] + "/" + uploads[1];
        userService.updataUserFace(url, authentication);
        return Result.success("上传成功",url);
    }
}
