package top.kwseeker.authentication.biz.domain.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import top.kwseeker.authentication.biz.common.enums.CommonStatusEnum;
import top.kwseeker.authentication.biz.common.util.CollectionUtil;
import top.kwseeker.authentication.biz.infrastructure.dal.po.RoleMenuPO;
import top.kwseeker.authentication.biz.infrastructure.dal.po.RolePO;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserRolePO;
import top.kwseeker.authentication.biz.infrastructure.repository.IRoleMenuRepository;
import top.kwseeker.authentication.biz.infrastructure.repository.IUserRoleRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class PermissionService implements IPermissionService {

    @Resource
    private IRoleService roleService;
    @Resource
    private IMenuService menuService;
    @Resource
    private IUserRoleRepository userRoleRepository;
    @Resource
    private IRoleMenuRepository roleMenuRepository;

    /**
     * 用户是否有指定的权限
     * @param userId      用户编号
     * @param permissions 要求的权限列表
     */
    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        // 如果要求的权限为空，即已经有权限
        if (ArrayUtil.isEmpty(permissions)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        List<RolePO> roles = getEnableUserRoleListByUserIdFromCache(userId);
        if (CollUtil.isEmpty(roles)) {
            return false;
        }

        // 情况一：遍历判断每个权限，如果有一满足，说明有权限
        for (String permission : permissions) {
            if (hasAnyPermission(roles, permission)) {
                return true;
            }
        }

        // 情况二：如果是超管角色（默认拥有所有权限），也说明有权限
        return roleService.hasAnySuperAdmin(CollectionUtil.convertSet(roles, RolePO::getId));
    }

    private boolean hasAnyPermission(List<RolePO> roles, String permission) {
        List<Long> menuIds = menuService.getMenuIdListByPermissionFromCache(permission);
        // 采用严格模式，如果权限找不到对应的 Menu 的话，也认为没有权限
        if (CollUtil.isEmpty(menuIds)) {
            return false;
        }

        // 判断是否有权限
        Set<Long> roleIds = CollectionUtil.convertSet(roles, RolePO::getId);
        for (Long menuId : menuIds) {
            // 获得拥有该菜单的角色编号集合
            Set<Long> menuRoleIds = getMenuRoleIdListByMenuIdFromCache(menuId);
            // 如果有交集，说明有权限
            if (CollUtil.containsAny(menuRoleIds, roleIds)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(roles)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        List<RolePO> roleList = getEnableUserRoleListByUserIdFromCache(userId);
        if (CollUtil.isEmpty(roleList)) {
            return false;
        }

        // 判断是否有角色
        Set<String> userRoles = CollectionUtil.convertSet(roleList, RolePO::getCode);
        return CollUtil.containsAny(userRoles, Sets.newHashSet(roles));
    }

    List<RolePO> getEnableUserRoleListByUserIdFromCache(Long userId) {
        // 获得用户拥有的角色编号
        Set<Long> roleIds = getUserRoleIdListByUserIdFromCache(userId);
        // 获得角色数组，并移除被禁用的
        List<RolePO> roles = roleService.getRoleListFromCache(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus()));
        return roles;
    }

    @Override
    //@Cacheable(value = RedisKeyConstants.USER_ROLE_ID_LIST, key = "#userId")
    public Set<Long> getUserRoleIdListByUserIdFromCache(Long userId) {
        return getUserRoleIdListByUserId(userId);
    }

    @Override
    public Set<Long> getUserRoleIdListByUserId(Long userId) {
        return CollectionUtil.convertSet(userRoleRepository.selectListByUserId(userId), UserRolePO::getRoleId);
    }

    @Override
    //@Cacheable(value = RedisKeyConstants.MENU_ROLE_ID_LIST, key = "#menuId")
    public Set<Long> getMenuRoleIdListByMenuIdFromCache(Long menuId) {
        return CollectionUtil.convertSet(roleMenuRepository.selectListByMenuId(menuId), RoleMenuPO::getRoleId);
    }
}
