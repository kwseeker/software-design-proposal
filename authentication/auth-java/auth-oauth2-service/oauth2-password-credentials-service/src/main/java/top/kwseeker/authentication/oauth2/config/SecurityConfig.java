package top.kwseeker.authentication.oauth2.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import top.kwseeker.authentication.oauth2.core.handler.CommonResultAccessDeniedHandler;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int DEFAULT_BCRYPT_LENGTH = 4;

    @Value("${oauth2.authentication-server.test:false}")
    private Boolean test;

    @Resource
    @Qualifier("rpcUserDetailService")
    private UserDetailsService userDetailsService;
    @Resource
    @Qualifier("defaultUserDetailService")
    private UserDetailsService defaultUserDetailService;

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @SuppressWarnings("deprecation")
    @ConditionalOnProperty(name = "oauth2.authentication-server.test", havingValue = "true")
    public static PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @ConditionalOnProperty(name = "oauth2.authentication-server.test", havingValue = "false", matchIfMissing = true)
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(DEFAULT_BCRYPT_LENGTH);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CommonResultAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 开启跨域
                .cors().and()
                //// 基于Token，不使用Cookie、Session机制，没有CSRF风险
                //.csrf().disable()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //// 关闭 X-Content-Type-Options 防护
                //.headers().frameOptions().disable().and()
                // 异常处理
                .exceptionHandling()
                //.authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (test) {
            //存储到内存的用户仅用于测试
            //auth.inMemoryAuthentication()
            //        .passwordEncoder(passwordEncoder())
            //        .withUser("Arvin").password("123456").roles("USER");
            auth.userDetailsService(defaultUserDetailService)
                    .passwordEncoder(noOpPasswordEncoder());
        } else {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        }
    }
}


