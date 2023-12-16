package top.kwseeker.authentication.biz.domain.auth.service;

import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2ClientPO;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2RefreshTokenPO;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2TokenPO;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

import java.util.List;

public interface IOAuth2TokenService {

    OAuth2TokenPO createAccessToken(UserPO userPO, String clientId, List<String> scopes);

    OAuth2RefreshTokenPO createOAuth2RefreshToken(UserPO userPO, OAuth2ClientPO clientPO);

    OAuth2TokenPO createOAuth2Token(OAuth2RefreshTokenPO refreshTokenPO, OAuth2ClientPO clientPO);

    OAuth2TokenPO checkAccessToken(String accessToken);

    OAuth2TokenPO getAccessToken(String accessToken);
}
