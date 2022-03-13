package com.zgm.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgm.server.pojo.Leave;
import com.zgm.server.pojo.Transdepart;
import com.zgm.server.mapper.TransdepartMapper;
import com.zgm.server.service.ITransdepartService;
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
 * 部门调动申请表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
@Service
public class TransdepartServiceImpl extends ServiceImpl<TransdepartMapper, Transdepart> implements ITransdepartService {
    @Autowired
    private TransdepartMapper transdepartMapper;

    @Override
    public PageResult<Leave> listTransDepart(ConditionVO conditionVO) {
        List<Leave> leaveList =  transdepartMapper.listTransDeparts(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        // 查询总量
        Integer count = transdepartMapper.selectCount(new LambdaQueryWrapper<Transdepart>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Transdepart::getName, conditionVO.getKeywords()));
        return new PageResult<>(leaveList, count);
    }

    @Override
    public void saveOrUpdateTransDepart(Transdepart transdepart) {
        this.saveOrUpdate(transdepart);
    }

    @Override
    public void deleteTransDepart(List<Integer> transDepartIdList) {
        transdepartMapper.deleteBatchIds(transDepartIdList);
    }
}
