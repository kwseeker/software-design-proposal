package top.kwseeker.authentication.biz.domain.auth.service;

import top.kwseeker.authentication.biz.infrastructure.dal.po.RolePO;

import java.util.Collection;
import java.util.List;

public interface IRoleService {

    /**
     * 角色列表中是否有超级管理员
     * @param ids 角色ID列表
     */
    boolean hasAnySuperAdmin(Collection<Long> ids);

    /**
     * 获得角色数组，从缓存中
     *
     * @param ids 角色ID列表
     * @return 角色数组
     */
    List<RolePO> getRoleListFromCache(Collection<Long> ids);

    RolePO getRoleFromCache(Long id);
}
