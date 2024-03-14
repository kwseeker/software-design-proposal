package top.kwseeker.authentication.common.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ServiceExceptionUtil {

    private static final ConcurrentMap<Integer, String> MESSAGES = new ConcurrentHashMap<>();

    public static void putAll(Map<Integer, String> messages) {
        ServiceExceptionUtil.MESSAGES.putAll(messages);
    }

    public static void put(Integer code, String message) {
        ServiceExceptionUtil.MESSAGES.put(code, message);
    }

    public static void delete(Integer code, String message) {
        ServiceExceptionUtil.MESSAGES.remove(code, message);
    }

    public static ServiceException exception(ErrorCode errorCode) {
        String messagePattern = MESSAGES.getOrDefault(errorCode.getCode(), errorCode.getMsg());
        return exception0(errorCode.getCode(), messagePattern);
    }

    public static ServiceException exception(ErrorCode errorCode, Object... params) {
        String messagePattern = MESSAGES.getOrDefault(errorCode.getCode(), errorCode.getMsg());
        return exception0(errorCode.getCode(), messagePattern, params);
    }

    public static ServiceException exception(ErrorCode errorCode, String message) {
        return exception0(errorCode.getCode(), message);
    }

    public static ServiceException exception(Integer code) {
        return exception0(code, MESSAGES.get(code));
    }

    public static ServiceException exception(Integer code, Object... params) {
        return exception0(code, MESSAGES.get(code), params);
    }

    public static ServiceException exception0(Integer code, String messagePattern, Object... params) {
        String message = doFormat(code, messagePattern, params);
        return new ServiceException(code, message);
    }

    public static ServiceException invalidParamException(String messagePattern, Object... params) {
        return exception0(GlobalErrorCodes.BAD_REQUEST.getCode(), messagePattern, params);
    }

    public static String doFormat(int code, String messagePattern, Object... params) {
        StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
        int i = 0;
        int j;
        int l;
        for (l = 0; l < params.length; l++) {
            j = messagePattern.indexOf("{}", i);
            if (j == -1) {
                log.error("[doFormat][参数过多：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
                if (i == 0) {
                    return messagePattern;
                } else {
                    sbuf.append(messagePattern.substring(i));
                    return sbuf.toString();
                }
            } else {
                sbuf.append(messagePattern, i, j);
                sbuf.append(params[l]);
                i = j + 2;
            }
        }
        if (messagePattern.indexOf("{}", i) != -1) {
            log.error("[doFormat][参数过少：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
        }
        sbuf.append(messagePattern.substring(i));
        return sbuf.toString();
    }
}
