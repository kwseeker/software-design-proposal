package top.kwseeker.authentication.biz.infrastructure.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.kwseeker.authentication.biz.infrastructure.dal.core.BaseMapperX;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

@Mapper
public interface UserMapper extends BaseMapperX<UserPO> {

    default UserPO selectByUsername(String username) {
        return selectOne(UserPO::getUsername, username);
    }
}
