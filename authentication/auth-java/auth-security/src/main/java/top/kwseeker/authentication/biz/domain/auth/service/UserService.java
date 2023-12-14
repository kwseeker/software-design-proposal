package top.kwseeker.authentication.biz.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kwseeker.authentication.biz.common.enums.CommonStatusEnum;
import top.kwseeker.authentication.biz.domain.auth.convert.UserConvert;
import top.kwseeker.authentication.biz.domain.auth.model.UserCreateReq;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;
import top.kwseeker.authentication.biz.infrastructure.repository.IUserRepository;

import javax.annotation.Resource;

@Service
public class UserService implements IUserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private IUserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateReq req) {
        // 校验用户名、手机号、Email是否唯一、部门是否有效等
        // 不是此项目的重点，先忽略

        // 插入用户
        UserPO user = UserConvert.INSTANCE.convert(req);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus());
        user.setPassword(encodePassword(req.getPassword()));
        userRepository.insert(user);
        return user.getId();
    }

    @Override
    public UserPO getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
