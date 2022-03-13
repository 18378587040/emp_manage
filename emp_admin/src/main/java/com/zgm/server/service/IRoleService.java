package com.zgm.server.service;

import com.zgm.server.dto.RoleDTO;
import com.zgm.server.dto.UserRoleDTO;
import com.zgm.server.pojo.Result;
import com.zgm.server.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import com.zgm.server.vo.RoleVO;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
public interface IRoleService extends IService<Role> {

    /**
     * 查询角色列表
     * @return
     */
    PageResult<RoleDTO> listRoles(ConditionVO conditionVO);

    /**
     * 查询用户角色列表
     * @return
     */
    List<UserRoleDTO> listUserRoles();

    /**
     * 保存或更新角色
     * @param roleVO
     */
    void saveOrUpdateRole(RoleVO roleVO);

    /**
     * 删除角色
     * @param roleIdList
     */
    void deleteRoles(List<Integer> roleIdList);
}
