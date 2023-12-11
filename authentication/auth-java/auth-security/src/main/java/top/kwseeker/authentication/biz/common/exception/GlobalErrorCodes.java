package top.kwseeker.authentication.biz.common.exception;

public interface GlobalErrorCodes {

    ErrorCode SUCCESS = new ErrorCode(0, "成功");

    ErrorCode BAD_REQUEST = new ErrorCode(400, "请求参数不正确");
}
