package com.zgm.server.controller;
import com.zgm.server.pojo.Jobtitle;
import com.zgm.server.pojo.Result;
import com.zgm.server.service.IJobtitleService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 员工职称表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
@RestController
@Api(tags = "职称模块")
public class JobtitleController {
    @Autowired
    private IJobtitleService jobtitleService;

    /**
     * 查询职称列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询职称列表")
    @GetMapping("/admin/jobTitles")
    public PageResult<Jobtitle> listJobTitle(ConditionVO conditionVO) {
        return jobtitleService.listJobTitle(conditionVO);
    }

    /**
     * 保存或更新职称
     * @return
     */
    @ApiOperation(value = "保存或更新职称")
    @PostMapping("/admin/jobTitles")
    public Result saveOrUpdateJobTitle(@RequestBody Jobtitle jobTitle) {
        jobtitleService.saveOrUpdateJobTitle(jobTitle);
        return Result.success("");
    }

    /**
     * 删除职称
     * @return
     */
    @ApiOperation(value = "删除职称")
    @DeleteMapping("/admin/jobTitles")
    public Result deleteJobTitle(@RequestBody List<Integer> jobTitleIdList) {
        jobtitleService.deleteJobTitle(jobTitleIdList);
        return Result.success("");
    }

}
