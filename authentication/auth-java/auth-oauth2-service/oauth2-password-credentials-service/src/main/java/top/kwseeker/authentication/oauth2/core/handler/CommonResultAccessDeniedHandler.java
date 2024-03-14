package top.kwseeker.authentication.oauth2.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import top.kwseeker.authentication.common.exception.GlobalErrorCodes;
import top.kwseeker.authentication.common.util.ServletUtils;
import top.kwseeker.authentication.common.vo.CommonResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CommonResultAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        log.warn("request access URL({}) denied", request.getRequestURI(), e);
         //返回 403
        ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodes.FORBIDDEN));
    }
}