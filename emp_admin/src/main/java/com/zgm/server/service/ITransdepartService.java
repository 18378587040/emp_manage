package com.zgm.server.service;

import com.zgm.server.pojo.Leave;
import com.zgm.server.pojo.Transdepart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;

import java.util.List;

/**
 * <p>
 * 部门调动申请表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
public interface ITransdepartService extends IService<Transdepart> {

    /**
     * 查询调动申请申请
     * @param conditionVO
     * @return
     */
    PageResult<Leave> listTransDepart(ConditionVO conditionVO);

    /**
     * 保存或更新申请
     * @param transdepart
     */
    void saveOrUpdateTransDepart(Transdepart transdepart);

    /**
     * 撤回申请
     * @param transDepartIdList
     */
    void deleteTransDepart(List<Integer> transDepartIdList);
}
