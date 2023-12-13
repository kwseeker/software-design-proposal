package top.kwseeker.authentication.biz.domain.auth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import top.kwseeker.authentication.biz.domain.auth.model.bo.UserBaseBO;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreateReq extends UserBaseBO {

    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

}
