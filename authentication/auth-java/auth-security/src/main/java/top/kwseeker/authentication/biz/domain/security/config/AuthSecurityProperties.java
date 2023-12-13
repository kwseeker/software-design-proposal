package top.kwseeker.authentication.biz.domain.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;

/**
 * 自定义安全配置
 */
@Data
@Validated
@ConfigurationProperties(prefix = "auth.security")
public class AuthSecurityProperties {

    @NotEmpty(message = "Token Header 不能为空")
    private String tokenHeader = "Authorization";

    /**
     * 免登录的 URL 列表
     */
    private List<String> permitAllUrls = Collections.emptyList();

    /**
     * PasswordEncoder
     */
    private Integer passwordEncoderLength = 4;
}
