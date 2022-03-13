package com.zgm.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgm.server.exception.BizException;
import com.zgm.server.mapper.PositionMapper;
import com.zgm.server.pojo.Jobtitle;
import com.zgm.server.mapper.JobtitleMapper;
import com.zgm.server.pojo.Position;
import com.zgm.server.service.IJobtitleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgm.server.utils.PageUtils;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 员工职称表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
@Service
public class JobtitleServiceImpl extends ServiceImpl<JobtitleMapper, Jobtitle> implements IJobtitleService {
    @Autowired
    private JobtitleMapper jobtitleMapper;

    @Override
    public PageResult<Jobtitle> listJobTitle(ConditionVO conditionVO) {
        List<Jobtitle> jobtitleList = jobtitleMapper.listJobTitles(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        // 查询总量
        Integer count = jobtitleMapper.selectCount(new LambdaQueryWrapper<Jobtitle>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Jobtitle::getName, conditionVO.getKeywords()));
        return new PageResult<>(jobtitleList, count);
    }

    @Override
    public void saveOrUpdateJobTitle(Jobtitle jobTitle) {
        // 判断职称名是否存在
        Jobtitle existJobtitle = jobtitleMapper.selectOne(new LambdaQueryWrapper<Jobtitle>()
                .select(Jobtitle::getId)
                .eq(Jobtitle::getName, jobTitle.getName()));
        if (Objects.nonNull(existJobtitle) && !existJobtitle.getId().equals(jobTitle.getId())) {
            throw new BizException("职称名已存在");
        }
        this.saveOrUpdate(jobTitle);
    }

    @Override
    public void deleteJobTitle(List<Integer> jobTitleIdList) {
        jobtitleMapper.deleteBatchIds(jobTitleIdList);
    }
}
