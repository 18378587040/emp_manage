package com.zgm.server.service;

import com.zgm.server.pojo.Leave;
import com.zgm.server.pojo.Transsalary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;

import java.util.List;

/**
 * <p>
 * 调薪申请表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
public interface ITranssalaryService extends IService<Transsalary> {

    /**
     * 查询薪资调动申请
     * @param conditionVO
     * @return
     */
    PageResult<Leave> listTransSalary(ConditionVO conditionVO);

    /**
     * 保存或更新申请
     * @param transsalary
     */
    void saveOrUpdateTransSalary(Transsalary transsalary);

    /**
     * 撤回申请
     * @param transSalaryIdList
     */
    void deleteTransSalary(List<Integer> transSalaryIdList);
}
