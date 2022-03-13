package com.zgm.server.controller;


import com.zgm.server.dto.LabelOptionDTO;
import com.zgm.server.dto.ResourceDTO;
import com.zgm.server.pojo.Result;
import com.zgm.server.service.IResourceService;
import com.zgm.server.vo.ResourceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-02-17
 */
@Api(tags = "资源模块")
@RestController
public class ResourceController {
    @Autowired
    private IResourceService resourceService;

    /**
     * 导入swagger接口
     * @return
     */
    @ApiOperation(value = "导入swagger接口")
    @GetMapping("/admin/resources/import/swagger")
    public Result importSwagger() {
        resourceService.importSwagger();
        return Result.success("");
    }

    /**
     * 新增或修改资源
     * @param resourceVO
     * @return
     */
    @ApiOperation(value = "新增或修改资源")
    @PostMapping("/admin/resources")
    public Result saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.success("");
    }

    /**
     * 删除资源
     * @param resourceId
     * @return
     */
    @ApiOperation(value = "删除资源")
    @DeleteMapping("/admin/resources/{resourceId}")
    public Result deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.success("");
    }

    /**
     * 查看资源列表
     * @return
     */
    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resources")
    public List<ResourceDTO> listResources() {
        return resourceService.listResources();
    }

    /**
     * 查看角色资源选项
     * @return
     */
    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("/admin/role/resources")
    public List<LabelOptionDTO> listResourceOption() {
        return resourceService.listResourceOption();
    }
}
