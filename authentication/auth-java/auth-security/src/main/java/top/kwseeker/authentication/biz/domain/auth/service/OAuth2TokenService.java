package top.kwseeker.authentication.biz.domain.auth.service;

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;
import top.kwseeker.authentication.biz.common.enums.UserTypeEnum;
import top.kwseeker.authentication.biz.common.exception.GlobalErrorCodes;
import top.kwseeker.authentication.biz.common.exception.ServiceException;
import top.kwseeker.authentication.biz.common.exception.ServiceExceptionUtil;
import top.kwseeker.authentication.biz.common.util.DateUtil;
import top.kwseeker.authentication.biz.infrastructure.dal.po.*;
import top.kwseeker.authentication.biz.infrastructure.repository.IOAuth2RefreshTokenRepository;
import top.kwseeker.authentication.biz.infrastructure.repository.IOAuth2TokenRepository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OAuth2TokenService implements IOAuth2TokenService {

    @Resource
    private IOAuth2ClientService oauth2ClientService;
    @Resource
    private IOAuth2RefreshTokenRepository oauth2RefreshTokenRepository;
    @Resource
    private IOAuth2TokenRepository oauth2TokenRepository;

    /**
     * 创建令牌，包括访问令牌和刷新令牌
     */
    @Override
    public OAuth2TokenPO createAccessToken(UserPO userPO, String clientId, List<String> scopes) {
        OAuth2ClientPO clientPO = oauth2ClientService.validOAuthClientFromCache(clientId);
        //创建刷新令牌
        OAuth2RefreshTokenPO refreshToken = createOAuth2RefreshToken(userPO, clientPO);
        //创建访问令牌
        return createOAuth2Token(refreshToken, clientPO);
    }

    @Override
    public OAuth2RefreshTokenPO createOAuth2RefreshToken(UserPO userPO, OAuth2ClientPO clientPO) {
        OAuth2RefreshTokenPO refreshToken = new OAuth2RefreshTokenPO()
                .setRefreshToken(generateRefreshToken())
                .setUserId(userPO.getId())
                .setUserType(UserTypeEnum.ADMIN.getValue())
                .setClientId(clientPO.getClientId())
                //刷新令牌有效时间根据客户端配置定
                .setExpiresTime(LocalDateTime.now().plusSeconds(clientPO.getRefreshTokenValiditySeconds()));
        oauth2RefreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public OAuth2TokenPO createOAuth2Token(OAuth2RefreshTokenPO refreshTokenPO, OAuth2ClientPO clientPO) {
        OAuth2TokenPO tokenPO = new OAuth2TokenPO()
                .setAccessToken(generateAccessToken())
                .setUserId(refreshTokenPO.getUserId())
                .setUserType(refreshTokenPO.getUserType())
                .setClientId(clientPO.getClientId())
                .setScopes(refreshTokenPO.getScopes())
                .setRefreshToken(refreshTokenPO.getRefreshToken())
                .setExpiresTime(LocalDateTime.now().plusSeconds(clientPO.getAccessTokenValiditySeconds()));
        //tokenPO.setTenantId(TenantContextHolder.getTenantId()); // 手动设置租户编号，避免缓存到 Redis 的时候，无对应的租户编号
        oauth2TokenRepository.save(tokenPO);
        // 记录到 Redis 中 TODO
        //oauth2AccessTokenRedisDAO.set(tokenPO);
        return tokenPO;
    }

    @Override
    public OAuth2TokenPO getAccessToken(String accessToken) {
        // TODO 优先从Redis
        // 再尝试从MySQL查
        OAuth2TokenPO tokenPO = oauth2TokenRepository.get(accessToken);
        if (tokenPO != null && !DateUtil.isExpired(tokenPO.getExpiresTime())) {
            // TODO 缓存到Redis
        }
        return tokenPO;
    }

    @Override
    public OAuth2TokenPO checkAccessToken(String accessToken) {
        OAuth2TokenPO tokenPO = getAccessToken(accessToken);
        // 检查token有效性
        if (tokenPO == null) {
            throw ServiceExceptionUtil.exception(GlobalErrorCodes.UNAUTHORIZED.getCode(), "访问令牌不存在");
        }
        if (DateUtil.isExpired(tokenPO.getExpiresTime())) {
            throw ServiceExceptionUtil.exception(GlobalErrorCodes.UNAUTHORIZED.getCode(), "访问令牌已过期");
        }
        return tokenPO;
    }

    private String generateRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }

    private String generateAccessToken() {
        return IdUtil.fastSimpleUUID();
    }
}
