package com.zgm.server.config.security.handler;

import com.zgm.server.dto.ResourceRoleDTO;
import com.zgm.server.mapper.RoleMapper;
import com.zgm.server.pojo.Menu;
import com.zgm.server.pojo.Role;
import com.zgm.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求url分析所需的角色
 * 过滤器
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {
    /**
     * 资源角色列表
     */
    private static List<ResourceRoleDTO> resourceRoleList;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 加载资源角色信息
     */
    @PostConstruct
    private void loadDataSource() {
        resourceRoleList = roleMapper.listResourceRoles();
    }

    /**
     * 清空接口角色信息
     */
    public void clearDataSource() {
        resourceRoleList = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 修改接口角色关系后重新加载
        if (CollectionUtils.isEmpty(resourceRoleList)) {
            this.loadDataSource();
        }
        FilterInvocation fi = (FilterInvocation) object;
        // 获取用户请求方式
        String method = fi.getRequest().getMethod();
        // 获取用户请求Url
        String url = fi.getRequest().getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 获取接口角色信息，若为匿名接口则放行，若无对应角色则禁止
        for (ResourceRoleDTO resourceRoleDTO : resourceRoleList) {
            if (antPathMatcher.match(resourceRoleDTO.getUrl(), url) && resourceRoleDTO.getRequestMethod().equals(method)) {
                List<String> roleList = resourceRoleDTO.getRoleList();
                if (CollectionUtils.isEmpty(roleList)) {
                    return SecurityConfig.createList("ROLE_LOGIN");
                }
                return SecurityConfig.createList(roleList.toArray(new String[]{}));
            }
        }
        return null;
    }

//    @Autowired
//    private IMenuService menuService;
//
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
//        // 获取请求的url
//        String requestUrl = ((FilterInvocation) o).getRequestUrl();
//        List<Menu> menus = menuService.getMenusWithRole();
//        for (Menu menu : menus) {
//            // 判断请求url与菜单角色是否匹配
//            if (new AntPathMatcher().match(menu.getUrl(), requestUrl)) {
//                String[] str = menu.getRoles().stream().map(Role::getRoleName).toArray(String[]::new);
//                return SecurityConfig.createList(str);
//            }
//        }
//        // 没匹配的url默认登录即可访问
//        return SecurityConfig.createList("ROLE_LOGIN");
//    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
