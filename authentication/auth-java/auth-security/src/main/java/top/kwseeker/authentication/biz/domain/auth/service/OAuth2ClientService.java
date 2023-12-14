package top.kwseeker.authentication.biz.domain.auth.service;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import top.kwseeker.authentication.biz.common.enums.CommonStatusEnum;
import top.kwseeker.authentication.biz.common.exception.ErrorCodes;
import top.kwseeker.authentication.biz.common.exception.ServiceExceptionUtil;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2ClientPO;
import top.kwseeker.authentication.biz.infrastructure.repository.IOAuth2ClientRepository;

import javax.annotation.Resource;

@Service
public class OAuth2ClientService implements IOAuth2ClientService {

    @Resource
    private IOAuth2ClientRepository oauth2ClientRepository;

    @Override
    public OAuth2ClientPO validOAuthClientFromCache(String clientId) {
        OAuth2ClientPO client = getOAuth2ClientFromCache(clientId);

        //后面是一些校验客户端配置是否有效的逻辑
        if (ObjectUtil.notEqual(client.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw ServiceExceptionUtil.exception(ErrorCodes.OAUTH2_CLIENT_DISABLE);
        }
        // ...

        return client;
    }

    //TODO 1 添加缓存
    //TODO 2 OAuth2Client只有少量客户端类型，感觉更适合用 Nacos 配置
    @Override
    public OAuth2ClientPO getOAuth2ClientFromCache(String clientId) {
        return oauth2ClientRepository.selectByClientId(clientId);
    }
}
