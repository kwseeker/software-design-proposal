package top.kwseeker.authentication.biz.domain.auth.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private IUserService userService;

    @Test
    void getUserByUsername() {
        String username = "admin";
        UserPO userPO = userService.getUserByUsername(username);
        Assertions.assertEquals(userPO.getNickname(), "admin");
    }

    @Test
    void isPasswordMatch() {
        String rawPassword = "admin123";
        //每次生成的都不一样
        String encoded = passwordEncoder.encode(rawPassword);
        System.out.println("encoded: " + encoded);
        boolean passwordMatch = userService.isPasswordMatch(rawPassword, encoded);
        Assertions.assertTrue(passwordMatch);
    }
}