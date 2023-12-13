package top.kwseeker.authentication.biz.domain.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.kwseeker.authentication.biz.domain.auth.model.UserCreateReq;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserPO convert(UserCreateReq bean);
}
