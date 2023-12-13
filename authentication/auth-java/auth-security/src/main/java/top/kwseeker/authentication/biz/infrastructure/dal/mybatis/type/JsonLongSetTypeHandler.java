package top.kwseeker.authentication.biz.infrastructure.dal.mybatis.type;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Set;

public class JsonLongSetTypeHandler extends AbstractJsonTypeHandler<Object> {

    private static final TypeReference<Set<Long>> TYPE_REFERENCE = new TypeReference<Set<Long>>() {
    };

    @Override
    protected Object parse(String json) {
        return JSONUtil.toBean(json, TYPE_REFERENCE.getType(), false);
    }

    @Override
    protected String toJson(Object obj) {
        return JSONUtil.toJsonStr(obj);
    }
}
