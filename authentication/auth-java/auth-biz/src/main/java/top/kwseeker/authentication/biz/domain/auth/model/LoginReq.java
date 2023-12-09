package top.kwseeker.authentication.biz.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

    @NotEmpty(message = "账号不能为空")
    private String username;

    @NotEmpty(message = "账号不能为空")
    private String password;

    /**
     * 由前端生成和后端生成规则一样
     */
    @NotEmpty(message = "验证码二次校验码不能为空")
    private String captchaVerification;
}
