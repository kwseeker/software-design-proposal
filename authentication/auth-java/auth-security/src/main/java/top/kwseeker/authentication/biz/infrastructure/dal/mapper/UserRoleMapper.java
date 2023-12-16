package top.kwseeker.authentication.biz.infrastructure.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.kwseeker.authentication.biz.infrastructure.dal.core.BaseMapperX;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserRolePO;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapperX<UserRolePO> {

    default List<UserRolePO> selectListByUserId(Long userId) {
        return selectList(UserRolePO::getUserId, userId);
    }
}
