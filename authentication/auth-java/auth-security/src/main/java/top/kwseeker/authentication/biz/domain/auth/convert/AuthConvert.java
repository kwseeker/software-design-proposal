package top.kwseeker.authentication.biz.domain.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.kwseeker.authentication.biz.domain.auth.model.LoginResp;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2TokenPO;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    LoginResp convert(OAuth2TokenPO bean);
}
