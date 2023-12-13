package top.kwseeker.authentication.biz.domain.security.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import top.kwseeker.authentication.biz.common.exception.GlobalErrorCodes;
import top.kwseeker.authentication.biz.common.pojo.CommonResult;
import top.kwseeker.authentication.biz.domain.security.core.util.SecurityFrameworkUtils;
import top.kwseeker.authentication.biz.infrastructure.util.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        // 打印 warn 的原因是，不定期合并 warn，看看有没恶意破坏
        log.warn("[commence][访问 URL({}) 时，用户({}) 权限不够]", request.getRequestURI(),
                SecurityFrameworkUtils.getLoginUserId(), e);
        // 返回 403
        ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodes.FORBIDDEN));
    }

}