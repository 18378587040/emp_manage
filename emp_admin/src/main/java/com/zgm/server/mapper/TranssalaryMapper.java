package com.zgm.server.mapper;

import com.zgm.server.pojo.Leave;
import com.zgm.server.pojo.Transsalary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgm.server.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 调薪申请表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2022-03-13
 */
public interface TranssalaryMapper extends BaseMapper<Transsalary> {

    /**
     * 查询调动申请申请
     * @param current
     * @param size
     * @param conditionVO
     * @return
     */
    List<Leave> listTransSaralys(@Param("current") Long current, @Param("size") Long size, ConditionVO conditionVO);
}
