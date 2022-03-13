package com.zgm.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgm.server.pojo.Leave;
import com.zgm.server.pojo.Transdepart;
import com.zgm.server.pojo.Transsalary;
import com.zgm.server.mapper.TranssalaryMapper;
import com.zgm.server.service.ITranssalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgm.server.utils.PageUtils;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 调薪申请表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
@Service
public class TranssalaryServiceImpl extends ServiceImpl<TranssalaryMapper, Transsalary> implements ITranssalaryService {

    @Autowired
    private TranssalaryMapper transsalaryMapper;

    @Override
    public PageResult<Leave> listTransSalary(ConditionVO conditionVO) {
        List<Leave> leaveList =  transsalaryMapper.listTransSaralys(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        // 查询总量
        Integer count = transsalaryMapper.selectCount(new LambdaQueryWrapper<Transsalary>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Transsalary::getName, conditionVO.getKeywords()));
        return new PageResult<>(leaveList, count);
    }

    @Override
    public void saveOrUpdateTransSalary(Transsalary transsalary) {
        this.saveOrUpdate(transsalary);
    }

    @Override
    public void deleteTransSalary(List<Integer> transSalaryIdList) {
        transsalaryMapper.deleteBatchIds(transSalaryIdList);
    }
}
