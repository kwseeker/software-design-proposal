package top.kwseeker.authentication.biz.infrastructure.dal.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import top.kwseeker.authentication.biz.infrastructure.dal.po.base.BasePO;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 默认字段填充处理器
 * <a href="https://baomidou.com/pages/4c6bcf/"/>
 */
@Component
public class DefaultDBFieldHandler implements MetaObjectHandler {

    /**
     * MetaObject metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BasePO) {
            BasePO basePO = (BasePO) metaObject.getOriginalObject();

            LocalDateTime current = LocalDateTime.now();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(basePO.getCreateTime())) {
                basePO.setCreateTime(current);
            }
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(basePO.getUpdateTime())) {
                basePO.setUpdateTime(current);
            }

            //Long userId = WebFrameworkUtils.getLoginUserId();
            //// 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            //if (Objects.nonNull(userId) && Objects.isNull(basePO.getCreator())) {
            //    basePO.setCreator(userId.toString());
            //}
            //// 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            //if (Objects.nonNull(userId) && Objects.isNull(basePO.getUpdater())) {
            //    basePO.setUpdater(userId.toString());
            //}
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }

        //// 当前登录用户不为空，更新人为空，则当前登录用户为更新人
        //Object modifier = getFieldValByName("updater", metaObject);
        //Long userId = WebFrameworkUtils.getLoginUserId();
        //if (Objects.nonNull(userId) && Objects.isNull(modifier)) {
        //    setFieldValByName("updater", userId.toString(), metaObject);
        //}
    }
}
