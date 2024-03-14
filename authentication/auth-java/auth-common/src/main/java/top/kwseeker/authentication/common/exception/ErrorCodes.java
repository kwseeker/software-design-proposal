package top.kwseeker.authentication.common.exception;

public interface ErrorCodes {

    // ========== AUTH 模块 1002000000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1002000000, "登录失败，账号密码不正确");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1002000001, "登录失败，账号被禁用");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1002000004, "验证码不正确，原因：{}");

    // ========== OAuth2 客户端 1002020000 =========
    ErrorCode OAUTH2_CLIENT_NOT_EXISTS = new ErrorCode(1002020000, "OAuth2 客户端不存在");
    ErrorCode OAUTH2_CLIENT_EXISTS = new ErrorCode(1002020001, "OAuth2 客户端编号已存在");
    ErrorCode OAUTH2_CLIENT_DISABLE = new ErrorCode(1002020002, "OAuth2 客户端已禁用");
}
