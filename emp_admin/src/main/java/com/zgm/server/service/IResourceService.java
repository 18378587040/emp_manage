package com.zgm.server.service;

import com.zgm.server.dto.LabelOptionDTO;
import com.zgm.server.dto.ResourceDTO;
import com.zgm.server.pojo.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgm.server.vo.ResourceVO;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author ming
 * @since 2022-02-17
 */
public interface IResourceService extends IService<Resource> {

    /**
     * 查看资源列表
     * @return
     */
    List<ResourceDTO> listResources();

    /**
     * 查看角色资源选项
     * @return
     */
    List<LabelOptionDTO> listResourceOption();

    /**
     * 导入swagger接口
     */
    void importSwagger();

    /**
     * 新增或修改资源
     * @param resourceVO
     */
    void saveOrUpdateResource(ResourceVO resourceVO);

    /**
     * 删除资源
     * @param resourceId
     */
    void deleteResource(Integer resourceId);
}
