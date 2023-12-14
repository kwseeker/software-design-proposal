package top.kwseeker.authentication.biz.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2ClientPO;

public interface IOAuth2ClientRepository extends IService<OAuth2ClientPO> {

    OAuth2ClientPO selectByClientId(String clientId);
}
