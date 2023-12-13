package top.kwseeker.authentication.biz.domain.security.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.kwseeker.authentication.biz.common.exception.GlobalErrorCodes;
import top.kwseeker.authentication.biz.common.pojo.CommonResult;
import top.kwseeker.authentication.biz.infrastructure.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.debug("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), e);
        // 返回 401, 这个401
        ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodes.UNAUTHORIZED));
    }
}