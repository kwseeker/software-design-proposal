package top.kwseeker.authentication.common.exception;

public interface GlobalErrorCodes {

    ErrorCode SUCCESS = new ErrorCode(0, "成功");

    ErrorCode BAD_REQUEST = new ErrorCode(400, "请求参数不正确");
    ErrorCode UNAUTHORIZED = new ErrorCode(401, "账号未登录");
    ErrorCode FORBIDDEN = new ErrorCode(403, "没有该操作权限");
}
