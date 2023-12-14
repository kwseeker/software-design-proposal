package top.kwseeker.authentication.biz.infrastructure.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.kwseeker.authentication.biz.infrastructure.dal.core.BaseMapperX;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2ClientPO;

@Mapper
public interface OAuth2ClientMapper extends BaseMapperX<OAuth2ClientPO> {
}
