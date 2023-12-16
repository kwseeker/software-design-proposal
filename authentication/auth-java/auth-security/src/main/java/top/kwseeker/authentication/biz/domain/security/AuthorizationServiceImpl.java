package top.kwseeker.authentication.biz.domain.security;

import cn.hutool.core.collection.CollUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import top.kwseeker.authentication.biz.common.core.KeyValue;
import top.kwseeker.authentication.biz.common.util.CacheUtil;
import top.kwseeker.authentication.biz.domain.auth.service.IPermissionService;
import top.kwseeker.authentication.biz.domain.security.core.LoginUser;
import top.kwseeker.authentication.biz.domain.security.core.util.SecurityFrameworkUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义鉴权服务
 */
@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private IPermissionService permissionService;

    /**
     * 针对 {@link #hasAnyRoles(String...)} 的缓存
     */
    private final LoadingCache<KeyValue<Long, List<String>>, Boolean> hasAnyRolesCache = CacheUtil.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<Long, List<String>>, Boolean>() {
                @Override
                public Boolean load(KeyValue<Long, List<String>> key) {
                    return permissionService.hasAnyRoles(key.getKey(), key.getValue().toArray(new String[0]));
                }
            });

    /**
     * 针对 {@link #hasAnyPermissions(String...)} 的缓存
     */
    private final LoadingCache<KeyValue<Long, List<String>>, Boolean> hasAnyPermissionsCache = CacheUtil.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<Long, List<String>>, Boolean>() {

                @Override
                public Boolean load(KeyValue<Long, List<String>> key) {
                    return permissionService.hasAnyPermissions(key.getKey(), key.getValue().toArray(new String[0]));
                }

            });

    @Override
    public boolean hasPermission(String permission) {
        System.out.println(">>>>>>>>> hasPermission(), permission=" + permission);
        return hasAnyPermissions(permission);
    }

    @Override
    @SneakyThrows
    public boolean hasAnyPermissions(String... permissions) {
        return hasAnyPermissionsCache.get(new KeyValue<>(SecurityFrameworkUtils.getLoginUserId(), Arrays.asList(permissions)));
    }

    @Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    @SneakyThrows
    public boolean hasAnyRoles(String... roles) {
        return hasAnyRolesCache.get(new KeyValue<>(SecurityFrameworkUtils.getLoginUserId(), Arrays.asList(roles)));
    }

    @Override
    public boolean hasScope(String scope) {
        return hasAnyScopes(scope);
    }

    @Override
    public boolean hasAnyScopes(String... scope) {
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user == null) {
            return false;
        }
        return CollUtil.containsAny(user.getScopes(), Arrays.asList(scope));
    }
}
