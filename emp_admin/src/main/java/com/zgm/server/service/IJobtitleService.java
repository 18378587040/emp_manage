package com.zgm.server.service;

import com.zgm.server.pojo.Jobtitle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;

import java.util.List;

/**
 * <p>
 * 员工职称表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
public interface IJobtitleService extends IService<Jobtitle> {

    /**
     * 查询职称列表
     * @param conditionVO
     * @return
     */
    PageResult<Jobtitle> listJobTitle(ConditionVO conditionVO);

    /**
     * 保存或更新职称
     * @param jobTitle
     */
    void saveOrUpdateJobTitle(Jobtitle jobTitle);

    /**
     * 删除职称
     * @param jobTitleIdList
     */
    void deleteJobTitle(List<Integer> jobTitleIdList);
}
