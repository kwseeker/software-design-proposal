package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.kwseeker.authentication.biz.infrastructure.dal.mapper.OAuth2RefreshTokenMapper;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2RefreshTokenPO;

@Repository
public class OAuth2RefreshTokenRepository extends ServiceImpl<OAuth2RefreshTokenMapper, OAuth2RefreshTokenPO>
        implements IOAuth2RefreshTokenRepository {

}
