package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.kwseeker.authentication.biz.infrastructure.dal.mapper.OAuth2TokenMapper;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2TokenPO;

@Repository
public class OAuth2TokenRepository extends ServiceImpl<OAuth2TokenMapper, OAuth2TokenPO>
        implements IOAuth2TokenRepository {
}
