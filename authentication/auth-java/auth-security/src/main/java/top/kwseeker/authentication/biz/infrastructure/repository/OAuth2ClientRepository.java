package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.kwseeker.authentication.biz.infrastructure.dal.mapper.OAuth2ClientMapper;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2ClientPO;

@Repository
public class OAuth2ClientRepository extends ServiceImpl<OAuth2ClientMapper, OAuth2ClientPO> implements IOAuth2ClientRepository {

    @Override
    public OAuth2ClientPO selectByClientId(String clientId) {
        return getBaseMapper().selectOne(OAuth2ClientPO::getClientId, clientId);
    }
}
