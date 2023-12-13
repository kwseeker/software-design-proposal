package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.kwseeker.authentication.biz.infrastructure.dal.mapper.UserMapper;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

@Repository
public class UserRepository extends ServiceImpl<UserMapper, UserPO> implements IUserRepository {

    @Override
    public void insert(UserPO user) {
        getBaseMapper().insert(user);
    }

    @Override
    public UserPO getUserByUsername(String username) {
        return getBaseMapper().selectByUsername(username);
    }
}
