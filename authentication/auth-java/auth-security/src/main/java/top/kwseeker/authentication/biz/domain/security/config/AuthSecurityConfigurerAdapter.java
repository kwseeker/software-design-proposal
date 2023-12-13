package top.kwseeker.authentication.biz.domain.security.config;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import top.kwseeker.authentication.biz.domain.security.core.filter.TokenAuthenticationFilter;
import top.kwseeker.authentication.biz.domain.security.core.handler.AccessDeniedHandlerImpl;
import top.kwseeker.authentication.biz.domain.security.core.handler.AuthenticationEntryPointImpl;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.Map;
import java.util.Set;

@Configuration
//@EnableConfigurationProperties(AuthSecurityProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AuthSecurityConfigurerAdapter  {

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private AuthSecurityProperties authSecurityProperties;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(authSecurityProperties);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(authSecurityProperties.getPasswordEncoderLength());
    }

    /**
     * 这里安全认证代码是从starter抽离的
     * AuthorizeRequestsCustomizer本是用于各个项目自己特殊的权限控制配置
     */
    @Bean("specialAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer specialAuthorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {
            @Override
            public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
                // Swagger 接口文档
                registry.antMatchers("/v3/api-docs/**").permitAll() // 元数据
                        .antMatchers("/swagger-ui.html").permitAll(); // Swagger UI
                // Druid 监控
                registry.antMatchers("/druid/**").anonymous();
                // Spring Boot Actuator 的安全配置
                registry.antMatchers("/actuator").anonymous()
                        .antMatchers("/actuator/**").anonymous();
            }
        };
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 开启跨域
                .cors().and()
                // 基于Token，不使用Cookie、Session机制，没有CSRF风险
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 关闭 X-Content-Type-Options 防护
                .headers().frameOptions().disable().and()
                // 异常处理
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler()).and()
                .rememberMe();
        // 登录、登录暂时不使用 Spring Security 的拓展点，主要考虑一方面拓展多用户、多种登录方式相对复杂，一方面用户的学习成本较高

        // 获得 @PermitAll 带来的 URL 列表，免登录
        Multimap<HttpMethod, String> permitAllUrls = getPermitAllUrlsFromAnnotations();
        // 设置每个请求的权限
        httpSecurity
                // ①：全局共享规则
                .authorizeRequests()
                    // 1.1 静态资源，可匿名访问
                    .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                    // 1.2 设置了 @PermitAll 无需认证，TODO 为何这么麻烦 Spring Security 本身没有处理 @PermitAll注解么
                    .antMatchers(HttpMethod.GET, permitAllUrls.get(HttpMethod.GET).toArray(new String[0])).permitAll()
                    .antMatchers(HttpMethod.POST, permitAllUrls.get(HttpMethod.POST).toArray(new String[0])).permitAll()
                    .antMatchers(HttpMethod.PUT, permitAllUrls.get(HttpMethod.PUT).toArray(new String[0])).permitAll()
                    .antMatchers(HttpMethod.DELETE, permitAllUrls.get(HttpMethod.DELETE).toArray(new String[0])).permitAll()
                    // 1.3 配置文件中 auth.security.permit-all-urls 指定的url无需权限校验
                    .antMatchers(authSecurityProperties.getPermitAllUrls().toArray(new String[0])).permitAll()
                    // 1.4 设置 App API (都是内部接口，比如 FeignClient 接口) 无需权限校验
                    //.antMatchers(buildAppApi("/**")).permitAll()
                    .and()
                // ②：每个项目的自定义规则（这个本来是starter中的代码被各个服务引用，通过authorizeRequestsCustomizers加载各自的特殊规则）
                //.authorizeRequests(registry -> // 下面，循环设置自定义规则
                        // authorizeRequestsCustomizers.forEach(customizer -> customizer.customize(registry))
                //)
                .authorizeRequests(specialAuthorizeRequestsCustomizer())
                // ③：兜底规则，必须认证
                .authorizeRequests()
                    .anyRequest().authenticated()
        ;

        // 在 UsernamePasswordAuthenticationFilter 前面添加 Token Filter
        httpSecurity.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    /**
     * 获取 RequestMappingHandlerMapping 中所有免权限验证的 URL
     * @return HttpMethod -> url 的 Map
     */
    private Multimap<HttpMethod, String> getPermitAllUrlsFromAnnotations() {
        Multimap<HttpMethod, String> result = HashMultimap.create();
        // 获得接口对应的 HandlerMethod 集合
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // 获得有 @PermitAll 注解的接口
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            if (!handlerMethod.hasMethodAnnotation(PermitAll.class)) {
                continue;
            }
            if (entry.getKey().getPatternsCondition() == null) {
                continue;
            }
            Set<String> urls = entry.getKey().getPatternsCondition().getPatterns();
            // 根据请求方法，添加到 result 结果
            entry.getKey().getMethodsCondition().getMethods().forEach(requestMethod -> {
                switch (requestMethod) {
                    case GET:
                        result.putAll(HttpMethod.GET, urls);
                        break;
                    case POST:
                        result.putAll(HttpMethod.POST, urls);
                        break;
                    case PUT:
                        result.putAll(HttpMethod.PUT, urls);
                        break;
                    case DELETE:
                        result.putAll(HttpMethod.DELETE, urls);
                        break;
                }
            });
        }
        return result;
    }

    //private String buildAppApi(String url) {
    //    return webProperties.getAppApi().getPrefix() + url;
    //}
}
