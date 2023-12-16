package top.kwseeker.authentication.biz.infrastructure.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.kwseeker.authentication.biz.infrastructure.dal.core.BaseMapperX;
import top.kwseeker.authentication.biz.infrastructure.dal.po.MenuPO;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapperX<MenuPO> {

    default List<MenuPO> selectListByPermission(String permission) {
        return selectList(MenuPO::getPermission, permission);
    }
}
