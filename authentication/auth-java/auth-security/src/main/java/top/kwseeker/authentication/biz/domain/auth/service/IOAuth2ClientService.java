package top.kwseeker.authentication.biz.domain.auth.service;

import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2ClientPO;

public interface IOAuth2ClientService {

    /**
     * 通过ClientId获取并校验客户端配置（OAuth2ClientPO），校验通过就返回
     */
    OAuth2ClientPO validOAuthClientFromCache(String clientId);

    /**
     * 通过ClientId获取并校验客户端配置（OAuth2ClientPO），优先从缓存中获取
     */
    OAuth2ClientPO getOAuth2ClientFromCache(String clientId);
}
