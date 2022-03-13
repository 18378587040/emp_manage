package com.zgm.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zgm.server.config.security.handler.CustomFilter;
import com.zgm.server.constant.CommonConst;
import com.zgm.server.dto.RoleDTO;
import com.zgm.server.dto.UserRoleDTO;
import com.zgm.server.exception.BizException;
import com.zgm.server.mapper.UserRoleMapper;
import com.zgm.server.pojo.MenuRole;
import com.zgm.server.pojo.ResourceRole;
import com.zgm.server.pojo.Role;
import com.zgm.server.mapper.RoleMapper;
import com.zgm.server.pojo.UserRole;
import com.zgm.server.service.IMenuRoleService;
import com.zgm.server.service.IResourceRoleService;
import com.zgm.server.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgm.server.utils.BeanCopyUtils;
import com.zgm.server.utils.PageUtils;
import com.zgm.server.vo.ConditionVO;
import com.zgm.server.vo.PageResult;
import com.zgm.server.vo.RoleVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2022-01-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IResourceRoleService resourceRoleService;
    @Autowired
    private IMenuRoleService menuRoleService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private CustomFilter filterInvocationSecurityMetadataSource;

    @Override
    public PageResult<RoleDTO> listRoles(ConditionVO conditionVO) {
        List<RoleDTO> roleDTOList = roleMapper.listRoles(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        // 查询总量
        Integer count = roleMapper.selectCount(new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Role::getRoleName, conditionVO.getKeywords()));
        return new PageResult<>(roleDTOList, count);
    }

    @Override
    public List<UserRoleDTO> listUserRoles() {
        // 查询角色列表
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>()
                .select(Role::getId, Role::getRoleName));
        return BeanCopyUtils.copyList(roleList, UserRoleDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        // 判断角色名重复
        Role existRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, roleVO.getRoleName()));
        if (Objects.nonNull(existRole) && !existRole.getId().equals(roleVO.getId())) {
            throw new BizException("角色名已存在");
        }
        // 保存或更新角色信息
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .roleLabel(roleVO.getRoleLabel())
                .isDisable(CommonConst.FALSE)
                .build();
        this.saveOrUpdate(role);
        // 更新资源列表
        if (CollectionUtils.isNotEmpty(roleVO.getResourceIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                resourceRoleService.remove(new LambdaQueryWrapper<ResourceRole>()
                        .eq(ResourceRole::getRoleId, roleVO.getId()));
            }
            List<ResourceRole> roleResourceList = roleVO.getResourceIdList().stream()
                    .map(resourceId -> ResourceRole.builder()
                            .roleId(role.getId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            resourceRoleService.saveBatch(roleResourceList);
            // 重新加载角色资源信息
            filterInvocationSecurityMetadataSource.clearDataSource();
        }
        // 更新菜单列表
        if (CollectionUtils.isNotEmpty(roleVO.getMenuIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                menuRoleService.remove(new LambdaQueryWrapper<MenuRole>().eq(MenuRole::getRoleId, roleVO.getId()));
            }
            List<MenuRole> roleMenuList = roleVO.getMenuIdList().stream()
                    .map(menuId -> MenuRole.builder()
                            .roleId(role.getId())
                            .menuId(menuId)
                            .build())
                    .collect(Collectors.toList());
            menuRoleService.saveBatch(roleMenuList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        // 判断角色下是否有用户
        Integer count = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getRoleId, roleIdList));
        if (count > 0) {
            throw new BizException("该角色下存在用户");
        }
        // 判断角色下是否有资源（如果有进行删除资源）
        if (CollectionUtils.isNotEmpty(roleIdList)) {
            roleIdList.stream()
                    .map(roleId -> {
                        return resourceRoleService.remove(new LambdaQueryWrapper<ResourceRole>()
                                .eq(ResourceRole::getRoleId, roleId));
                    }).collect(Collectors.toList());
        }
        roleMapper.deleteBatchIds(roleIdList);
    }
}

