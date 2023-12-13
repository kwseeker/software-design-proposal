package top.kwseeker.authentication.biz.infrastructure.dal.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.kwseeker.authentication.biz.common.enums.CommonStatusEnum;
import top.kwseeker.authentication.biz.infrastructure.dal.common.enums.DataScopeEnum;
import top.kwseeker.authentication.biz.infrastructure.dal.common.enums.RoleTypeEnum;
import top.kwseeker.authentication.biz.infrastructure.dal.mybatis.type.JsonLongSetTypeHandler;
import top.kwseeker.authentication.biz.infrastructure.dal.po.base.TenantBasePO;

import java.util.Set;

@TableName(value = "system_role", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePO extends TenantBasePO {

    /**
     * 角色ID
     */
    @TableId
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色标识
     * 枚举
     */
    private String code;
    /**
     * 角色排序
     */
    private Integer sort;
    /**
     * 角色状态
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 角色类型
     * 枚举 {@link RoleTypeEnum}
     */
    private Integer type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 数据范围
     * 枚举
     */
    private Integer dataScope;
    /**
     * 数据范围(指定部门数组)
     * 适用于 {@link #dataScope} 的值为 {@link DataScopeEnum#DEPT_CUSTOM} 时
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> dataScopeDeptIds;
}
