package top.kwseeker.authentication.biz.infrastructure.dal.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

import javax.annotation.Resource;

@SpringBootTest
//@MybatisPlusTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        Integer userId = 1;
        UserPO userPO = userMapper.selectById(userId);
        Assertions.assertThat(userPO).isNotNull();
    }
}