package top.kwseeker.authentication.biz.domain.security.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import top.kwseeker.authentication.biz.common.exception.ServiceException;
import top.kwseeker.authentication.biz.domain.auth.service.IOAuth2TokenService;
import top.kwseeker.authentication.biz.domain.security.config.AuthSecurityProperties;
import top.kwseeker.authentication.biz.domain.security.core.LoginUser;
import top.kwseeker.authentication.biz.domain.security.core.util.SecurityFrameworkUtils;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2TokenPO;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token校验
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private AuthSecurityProperties authSecurityProperties;

    @Resource
    private IOAuth2TokenService oauth2TokenService;

    public TokenAuthenticationFilter(AuthSecurityProperties authSecurityProperties) {
        this.authSecurityProperties = authSecurityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 微服务中，可能当前服务是后台服务，请求都来自网关或其他微服务，而且前面的服务可能已经验证过token了，
        // 这时直接通过自定义请求头“login-user”将用户信息透传过来，不再需要校验Token
        LoginUser loginUser = buildLoginUserByHeader(request);

        // 说明Token还没有校验
        // 那就校验 Token, 读取登录时颁发Token时存储的对应的用户信息
        if (loginUser == null) {
            String token = SecurityFrameworkUtils.obtainAuthorization(request, authSecurityProperties.getTokenHeader());
            if (StrUtil.isNotBlank(token)) {
                //Integer loginUserType = WebFrameworkUtils.getLoginUserType(request);
                loginUser = buildLoginUserByToken(token);
            }
        }

        //再往request attributes 中存一下方便出了安全过滤器链后仍然可以读取
        if (loginUser != null) {
            log.debug("loginUser: {}", loginUser);
            SecurityFrameworkUtils.setLoginUser(loginUser, request);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 从请求头取已经认证过Token的用户信息
     *
     * @return 已登录的用户信息
     */
    private LoginUser buildLoginUserByHeader(HttpServletRequest request) {
        String loginUserStr = request.getHeader(SecurityFrameworkUtils.LOGIN_USER_HEADER);
        return StrUtil.isNotEmpty(loginUserStr) ? JSONUtil.toBean(loginUserStr, LoginUser.class) : null;
    }

    //private LoginUser buildLoginUserByToken(String token, Integer userType) {
    private LoginUser buildLoginUserByToken(String token) {
        try {
            // 校验访问令牌
            //OAuth2AccessTokenCheckRespDTO accessToken = oauth2TokenApi.checkAccessToken(token).getCheckedData();
            OAuth2TokenPO tokenPO = oauth2TokenService.checkAccessToken(token);
            if (tokenPO.getAccessToken() == null) {
                return null;
            }

            // 用户类型不匹配，无权限
            //if (ObjectUtil.notEqual(accessToken.getUserType(), userType)) {
            //    throw new AccessDeniedException("错误的用户类型");
            //}
            // 构建登录用户
            return new LoginUser()
                    .setId(tokenPO.getUserId()).setUserType(tokenPO.getUserType())
                    .setTenantId(tokenPO.getTenantId()).setScopes(tokenPO.getScopes());
        } catch (ServiceException serviceException) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
    }
}
