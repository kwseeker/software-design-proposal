package top.kwseeker.authentication.biz.domain.auth.service;

import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.kwseeker.authentication.biz.common.exception.ErrorCodes;
import top.kwseeker.authentication.biz.common.exception.ServiceExceptionUtil;
import top.kwseeker.authentication.biz.domain.auth.model.LoginReq;
import top.kwseeker.authentication.biz.domain.auth.model.LoginResp;
import top.kwseeker.authentication.biz.domain.auth.model.dataobject.UserDO;

import javax.annotation.Resource;

@Service
public class AuthService implements IAuthService {

    @Value("${captcha.enable:true}")
    private Boolean captchaEnable;

    @Resource
    private CaptchaService captchaService;

    @Override
    public LoginResp login(LoginReq req) {
        //校验验证码
        validateCaptcha(req);
        //用户名密码验证
        UserDO userDO = authenticate(req.getUsername(), req.getPassword());
        //创建Token
        return new LoginResp();
    }

    private void validateCaptcha(LoginReq req) {
        //为了方便测试可禁止校验
        if (!captchaEnable) {
            return;
        }
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(req.getCaptchaVerification());
        ResponseModel responseModel = captchaService.verification(captchaVO);
        if (!responseModel.isSuccess()) {
            throw ServiceExceptionUtil.exception(ErrorCodes.AUTH_LOGIN_CAPTCHA_CODE_ERROR);
        }
    }

    @Override
    public UserDO authenticate(String username, String password) {
        //password应该是摘要算法处理后的加密字符串
        //且是查数据库进行校验
        //这里先简化
        if (!"kwseeker".equalsIgnoreCase(username) && !"admin".equalsIgnoreCase(username)) {
            throw ServiceExceptionUtil.exception(ErrorCodes.AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!"123456".equals(password)) {
            throw ServiceExceptionUtil.exception(ErrorCodes.AUTH_LOGIN_BAD_CREDENTIALS);
        }
        return new UserDO("admin".equalsIgnoreCase(username)? 100L:101L, username, password);
    }
}
