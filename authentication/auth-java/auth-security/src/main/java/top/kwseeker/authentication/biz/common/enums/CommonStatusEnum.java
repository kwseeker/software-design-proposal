package top.kwseeker.authentication.biz.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kwseeker.authentication.biz.common.core.IntArrayValuable;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CommonStatusEnum implements IntArrayValuable {

    ENABLE(0, "开启"),
    DISABLE(1, "关闭");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CommonStatusEnum::getStatus).toArray();

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
