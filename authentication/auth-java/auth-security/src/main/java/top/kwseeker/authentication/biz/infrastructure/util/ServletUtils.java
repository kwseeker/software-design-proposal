package top.kwseeker.authentication.biz.infrastructure.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    public static String getRemoteId(HttpServletRequest request) {
        String ip = ServletUtils.getClientIP(request);
        String ua = request.getHeader("user-agent");
        if (StrUtil.isNotBlank(ip)) {
            return ip + ua;
        }
        return request.getRemoteAddr() + ua;
    }

    public static String getClientIP(HttpServletRequest request) {
        return ServletUtil.getClientIP(request);
    }
}
