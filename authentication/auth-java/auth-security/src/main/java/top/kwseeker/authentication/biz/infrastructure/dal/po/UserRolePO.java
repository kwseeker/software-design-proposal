package top.kwseeker.authentication.biz.infrastructure.dal.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.kwseeker.authentication.biz.infrastructure.dal.po.base.BasePO;

@TableName("system_user_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRolePO extends BasePO {

    /**
     * 自增主键
     */
    @TableId
    private Long id;
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 角色 ID
     */
    private Long roleId;

}
