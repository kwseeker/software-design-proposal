package top.kwseeker.authentication.biz.domain.auth.model.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDO {

    private Long id;
    private String username;
    private String password;
}
