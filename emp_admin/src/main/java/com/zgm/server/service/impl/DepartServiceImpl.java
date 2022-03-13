package com.zgm.server.service.impl;

import com.zgm.server.constant.CommonConst;
import com.zgm.server.pojo.Depart;
import com.zgm.server.mapper.DepartMapper;
import com.zgm.server.pojo.Result;
import com.zgm.server.service.IDepartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgm.server.utils.PageUtils;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 员工部门表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
@Service
public class DepartServiceImpl extends ServiceImpl<DepartMapper, Depart> implements IDepartService {

    @Autowired
    private DepartMapper departMapper;

    @Override
    public List<Depart> listDepart() {
        return departMapper.listDeparts(-1);
    }

    @Override
    public Result addDep(Depart depart) {
        depart.setIsEnabled(CommonConst.TRUE);
        departMapper.addDep(depart);
        if (depart.getResult() == 1) {
            return Result.success("添加成功", depart);
        }
        return Result.error("添加失败");
    }

    @Override
    public Result deleteDep(Integer id) {
        Depart depart = new Depart();
        depart.setId(id);
        departMapper.deleteDep(depart);
        if (-1==depart.getResult()) {
            return Result.error("该部门有员工存在，删除失败");
        }
        if (-2==depart.getResult()) {
            return Result.error("该部门有子部门存在，删除失败");
        }
        if (1==depart.getResult()) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
