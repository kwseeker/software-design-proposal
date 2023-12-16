package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.kwseeker.authentication.biz.infrastructure.dal.mapper.RoleMenuMapper;
import top.kwseeker.authentication.biz.infrastructure.dal.po.RoleMenuPO;

import java.util.List;

@Repository
public class RoleMenuRepository extends ServiceImpl<RoleMenuMapper, RoleMenuPO> implements IRoleMenuRepository {

    @Override
    public List<RoleMenuPO> selectListByMenuId(Long menuId) {
        return getBaseMapper().selectList(RoleMenuPO::getMenuId, menuId);
    }
}
