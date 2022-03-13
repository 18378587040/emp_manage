package com.zgm.server.mapper;

import com.zgm.server.pojo.Jobtitle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgm.server.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工职称表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2022-03-02
 */
public interface JobtitleMapper extends BaseMapper<Jobtitle> {

    /**
     * 查询职称列表
     * @param current
     * @param size
     * @param conditionVO
     * @return
     */
    List<Jobtitle> listJobTitles(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);
}
