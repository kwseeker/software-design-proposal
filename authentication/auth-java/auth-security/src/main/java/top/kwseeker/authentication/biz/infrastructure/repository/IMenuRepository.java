package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kwseeker.authentication.biz.infrastructure.dal.po.MenuPO;

import java.util.List;

public interface IMenuRepository extends IService<MenuPO> {

    List<MenuPO> selectListByPermission(String permission);
}
