package top.kwseeker.authentication.biz.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorCode {

    private final Integer code;
    private final String msg;
}