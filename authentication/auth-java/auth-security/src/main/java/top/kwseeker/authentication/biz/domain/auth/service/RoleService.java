package top.kwseeker.authentication.biz.domain.auth.service;

import org.springframework.stereotype.Service;
import top.kwseeker.authentication.biz.common.util.CollectionUtil;
import top.kwseeker.authentication.biz.infrastructure.dal.po.RolePO;
import top.kwseeker.authentication.biz.infrastructure.repository.IRoleRepository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Resource
    private IRoleRepository roleRepository;

    @Override
    public boolean hasAnySuperAdmin(Collection<Long> ids) {
        return false;
    }

    @Override
    public List<RolePO> getRoleListFromCache(Collection<Long> ids) {
        if (cn.hutool.core.collection.CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return CollectionUtil.convertList(ids, this::getRoleFromCache);
    }

    @Override
    //@Cacheable(value = RedisKeyConstants.ROLE, key = "#id", unless = "#result == null")
    public RolePO getRoleFromCache(Long id) {
        return roleRepository.getById(id);
    }
}
