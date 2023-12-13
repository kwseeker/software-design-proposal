package top.kwseeker.authentication.biz.domain.auth.service;

import top.kwseeker.authentication.biz.domain.auth.model.LoginReq;
import top.kwseeker.authentication.biz.domain.auth.model.LoginResp;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

public interface IAuthService {

    /**
     * 用户名密码认证
     *
     * @param req 用户登录信息（用户名密码、验证码）
     * @return 用户信息
     */
    LoginResp login(LoginReq req);

    UserPO authenticate(String username, String password);
}
