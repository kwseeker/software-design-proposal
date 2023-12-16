package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.kwseeker.authentication.biz.infrastructure.dal.mapper.UserRoleMapper;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserRolePO;

import java.util.List;

@Repository
public class UserRoleRepository extends ServiceImpl<UserRoleMapper, UserRolePO> implements IUserRoleRepository {

    @Override
    public List<UserRolePO> selectListByUserId(Long userId) {
        return getBaseMapper().selectList(UserRolePO::getUserId, userId);
    }
}
