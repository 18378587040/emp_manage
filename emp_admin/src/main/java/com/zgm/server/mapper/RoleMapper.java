package com.zgm.server.mapper;

import com.zgm.server.dto.ResourceRoleDTO;
import com.zgm.server.dto.RoleDTO;
import com.zgm.server.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgm.server.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id查询角色
     * @param id
     * @return
     */
    List<String> listRolesByUserId(Integer id);

    /**
     * 查询路由角色列表
     * @return
     */
    List<ResourceRoleDTO> listResourceRoles();

    /**
     * 查询角色列表
     * @param current
     * @param size
     * @param conditionVO
     * @return
     */
    List<RoleDTO> listRoles(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);
}
