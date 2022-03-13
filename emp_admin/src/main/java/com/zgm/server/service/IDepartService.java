package com.zgm.server.service;

import com.zgm.server.pojo.Depart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgm.server.pojo.Result;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;

import java.util.List;

/**
 * <p>
 * 员工部门表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
public interface IDepartService extends IService<Depart> {

    /**
     * 获取所有部门
     * @return
     */
    List<Depart> listDepart();

    /**
     * 添加部门
     * @param depart
     * @return
     */
    Result addDep(Depart depart);

    /**
     * 删除部门
     * @param id
     * @return
     */
    Result deleteDep(Integer id);
}
