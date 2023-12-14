package top.kwseeker.authentication.biz.infrastructure.dal.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.kwseeker.authentication.biz.infrastructure.dal.po.base.TenantBasePO;

/**
 * 角色和菜单关联
 *
 * @author ruoyi
 */
@TableName("system_role_menu")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuPO extends TenantBasePO {

    /**
     * 自增主键
     */
    @TableId
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;

}
