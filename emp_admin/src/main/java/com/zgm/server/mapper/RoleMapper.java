package com.zgm.server.mapper;

import com.zgm.server.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
    List<Role> listRolesByUserId(Integer id);
}
