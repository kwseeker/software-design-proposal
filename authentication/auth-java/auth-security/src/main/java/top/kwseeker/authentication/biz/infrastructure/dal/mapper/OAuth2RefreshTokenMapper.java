package top.kwseeker.authentication.biz.infrastructure.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.kwseeker.authentication.biz.infrastructure.dal.core.BaseMapperX;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2RefreshTokenPO;

@Mapper
public interface OAuth2RefreshTokenMapper extends BaseMapperX<OAuth2RefreshTokenPO> {
}
