package top.kwseeker.authentication.biz.interfaces.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kwseeker.authentication.biz.common.pojo.CommonResult;
import top.kwseeker.authentication.biz.domain.auth.model.UserCreateReq;
import top.kwseeker.authentication.biz.domain.auth.service.IUserService;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/create")
    //@PreAuthorize("hasAuthority('system:user:create')")
    //@PreAuthorize("@ss.hasPermission('system:user:create')")
    public CommonResult<Long> createUser(@Valid @RequestBody UserCreateReq reqVO) {
        Long id = userService.createUser(reqVO);
        return CommonResult.success(id);
    }
}
