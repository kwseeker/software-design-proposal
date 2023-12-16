package top.kwseeker.authentication.biz.domain.auth.service;

import org.springframework.stereotype.Service;
import top.kwseeker.authentication.biz.common.util.CollectionUtil;
import top.kwseeker.authentication.biz.infrastructure.dal.po.MenuPO;
import top.kwseeker.authentication.biz.infrastructure.repository.IMenuRepository;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuService implements IMenuService {

    @Resource
    private IMenuRepository menuRepository;

    @Override
    public List<Long> getMenuIdListByPermissionFromCache(String permission) {
        List<MenuPO> menus = menuRepository.selectListByPermission(permission);
        return CollectionUtil.convertList(menus, MenuPO::getId);
    }
}
