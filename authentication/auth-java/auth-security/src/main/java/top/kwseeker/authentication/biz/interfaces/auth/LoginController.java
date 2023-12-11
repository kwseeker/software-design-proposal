package top.kwseeker.authentication.biz.interfaces.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.kwseeker.authentication.biz.common.pojo.CommonResult;
import top.kwseeker.authentication.biz.domain.auth.model.LoginReq;
import top.kwseeker.authentication.biz.domain.auth.model.LoginResp;
import top.kwseeker.authentication.biz.domain.auth.service.IAuthService;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class LoginController {

    @Resource
    private IAuthService authService;

    @PostMapping("/login")
    public CommonResult<LoginResp> login(@RequestBody @Valid LoginReq data) {
        LoginResp resp = authService.login(data);
        return CommonResult.success(resp);
    }

}
