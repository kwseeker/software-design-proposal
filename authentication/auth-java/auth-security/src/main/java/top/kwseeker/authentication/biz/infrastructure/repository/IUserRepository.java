package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

public interface IUserRepository extends IService<UserPO> {

    void insert(UserPO user);

    UserPO getUserByUsername(String username);
}
