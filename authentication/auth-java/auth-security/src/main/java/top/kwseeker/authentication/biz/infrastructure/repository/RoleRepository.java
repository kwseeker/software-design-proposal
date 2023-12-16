package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.kwseeker.authentication.biz.infrastructure.dal.mapper.RoleMapper;
import top.kwseeker.authentication.biz.infrastructure.dal.po.RolePO;

@Repository
public class RoleRepository extends ServiceImpl<RoleMapper, RolePO> implements IRoleRepository {
}
