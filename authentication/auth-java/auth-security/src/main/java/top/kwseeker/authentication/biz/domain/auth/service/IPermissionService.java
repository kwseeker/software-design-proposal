package top.kwseeker.authentication.biz.domain.auth.service;

import java.util.Set;

public interface IPermissionService {

    /**
     * 判断是否有权限，任一一个即可
     *
     * @param userId      用户编号
     * @param permissions 权限
     * @return 是否
     */
    boolean hasAnyPermissions(Long userId, String... permissions);

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param roles 角色数组
     * @return 是否
     */
    boolean hasAnyRoles(Long userId, String... roles);

    Set<Long> getUserRoleIdListByUserIdFromCache(Long userId);

    Set<Long> getUserRoleIdListByUserId(Long userId);

    Set<Long> getMenuRoleIdListByMenuIdFromCache(Long menuId);
}
