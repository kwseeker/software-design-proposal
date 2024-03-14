package top.kwseeker.authentication.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import javax.annotation.Resource;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${oauth2.authentication-server.test:false}")
    private Boolean test;

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private ClientDetailsService clientDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServerConfigurer) throws Exception {
        oauthServerConfigurer.checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        if (test) {
            // inMemory() 仅用于测试
            // 如果是集群内部客户端应用一般也不是很多，在内存中配置也不是不可以
            clients.inMemory()
                    .withClient("client-gateway").secret("secret-123")  // 客户端应用账号、密码
                    .authorizedGrantTypes("password")   // 密码模式
                    .scopes("read_userinfo", "read_contacts");   // 可授权的 Scope
        } else {
            //TODO
            clients.withClientDetails(clientDetailsService);
        }
    }
}
