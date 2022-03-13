package com.zgm.server.mapper;

import com.zgm.server.pojo.Depart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;

import java.util.List;

/**
 * <p>
 * 员工部门表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
public interface DepartMapper extends BaseMapper<Depart> {

    /**
     * 获取所有部门
     * @param parentId
     * @return
     */
    List<Depart> listDeparts(Integer parentId);

    /**
     * 添加部门
     * @param depart
     */
    void addDep(Depart depart);

    /**
     * 删除部门
     * @param depart
     */
    void deleteDep(Depart depart);
}