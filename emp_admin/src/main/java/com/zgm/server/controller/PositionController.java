package com.zgm.server.controller;


import com.zgm.server.pojo.Position;
import com.zgm.server.pojo.Result;
import com.zgm.server.service.IPositionService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.Condition;

/**
 * <p>
 * 员工职位表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
@RestController
@Api(tags = "职位模块")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    /**
     * 查询职位列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询职位列表")
    @GetMapping("/admin/positions")
    public PageResult<Position> listPositions(ConditionVO conditionVO) {
        return positionService.listPositions(conditionVO);
    }

    /**
     * 保存或更新职位
     * @return
     */
    @ApiOperation(value = "保存或更新职位")
    @PostMapping("/admin/positions")
    public Result saveOrUpdatePosition(@RequestBody Position position) {
        positionService.saveOrUpdatePosition(position);
        return Result.success("");
    }

    /**
     * 删除职位
     * @return
     */
    @ApiOperation(value = "删除职位")
    @DeleteMapping("/admin/positions")
    public Result deletePosition(@RequestBody List<Integer> positionIdList) {
        positionService.deletePosition(positionIdList);
        return Result.success("");
    }
}
