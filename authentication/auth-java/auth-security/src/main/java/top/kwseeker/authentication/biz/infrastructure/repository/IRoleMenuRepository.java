package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kwseeker.authentication.biz.infrastructure.dal.po.RoleMenuPO;

import java.util.List;

public interface IRoleMenuRepository extends IService<RoleMenuPO> {

    List<RoleMenuPO> selectListByMenuId(Long menuId);
}
