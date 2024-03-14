package top.kwseeker.authentication.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class ServiceException extends RuntimeException {

    private Integer code;
    private String message;

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
