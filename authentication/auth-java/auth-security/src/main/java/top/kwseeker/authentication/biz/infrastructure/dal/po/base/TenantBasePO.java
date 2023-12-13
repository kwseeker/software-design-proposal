package top.kwseeker.authentication.biz.infrastructure.dal.po.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TenantBasePO extends BasePO {

    //租户ID
    private Long tenantId;
}

