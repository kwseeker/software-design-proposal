package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserRolePO;

import java.util.List;

public interface IUserRoleRepository extends IService<UserRolePO> {

    List<UserRolePO> selectListByUserId(Long userId);
}
