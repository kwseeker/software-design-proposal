package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.kwseeker.authentication.biz.infrastructure.dal.mapper.MenuMapper;
import top.kwseeker.authentication.biz.infrastructure.dal.po.MenuPO;

import java.util.List;

@Repository
public class MenuRepository extends ServiceImpl<MenuMapper, MenuPO> implements IMenuRepository {

    @Override
    public List<MenuPO> selectListByPermission(String permission) {
        return getBaseMapper().selectListByPermission(permission);
    }
}
