package top.kwseeker.authentication.biz.domain.auth.service;

import top.kwseeker.authentication.biz.domain.auth.model.UserCreateReq;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

import javax.validation.Valid;

public interface IUserService {

    Long createUser(@Valid UserCreateReq req);

    UserPO getUserByUsername(String username);

    boolean isPasswordMatch(String actualPassword, String expectPassword);
}
