package top.kwseeker.authentication.biz.domain.auth.convert;

import org.junit.jupiter.api.Test;
import top.kwseeker.authentication.biz.domain.auth.model.LoginResp;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2TokenPO;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuthConvertTest {

    @Test
    public void testConvert() {
        OAuth2TokenPO tokenPO = new OAuth2TokenPO()
                .setUserId(1L)
                .setRefreshToken("aaa")
                .setAccessToken("bbb")
                .setExpiresTime(LocalDateTime.now());
        LoginResp loginResp = AuthConvert.INSTANCE.convert(tokenPO);
        assertEquals(loginResp.getAccessToken(), tokenPO.getAccessToken());
    }
}