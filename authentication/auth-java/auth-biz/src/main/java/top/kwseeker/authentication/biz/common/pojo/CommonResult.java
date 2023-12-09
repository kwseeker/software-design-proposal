package top.kwseeker.authentication.biz.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.Assert;
import top.kwseeker.authentication.biz.common.exception.ErrorCode;
import top.kwseeker.authentication.biz.common.exception.GlobalErrorCodes;
import top.kwseeker.authentication.biz.common.exception.ServiceException;

import java.io.Serializable;
import java.util.Objects;

@Data
public class CommonResult<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMsg());
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!GlobalErrorCodes.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = GlobalErrorCodes.SUCCESS.getCode();
        result.data = data;
        result.msg = "";
        return result;
    }

    public static boolean isSuccess(Integer code) {
        return Objects.equals(code, GlobalErrorCodes.SUCCESS.getCode());
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isSuccess() {
        return isSuccess(code);
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isError() {
        return !isSuccess();
    }

    public void checkError() throws ServiceException {
        if (isSuccess()) {
            return;
        }
        // 业务异常
        throw new ServiceException(code, msg);
    }

    @JsonIgnore // 避免 jackson 序列化
    public T getCheckedData() {
        checkError();
        return data;
    }

    public static <T> CommonResult<T> error(ServiceException serviceException) {
        return error(serviceException.getCode(), serviceException.getMessage());
    }
}
