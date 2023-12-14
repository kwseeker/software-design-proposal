package top.kwseeker.authentication.biz.domain.auth.service;

import cn.hutool.core.util.ObjectUtil;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.kwseeker.authentication.biz.common.enums.CommonStatusEnum;
import top.kwseeker.authentication.biz.common.exception.ErrorCodes;
import top.kwseeker.authentication.biz.common.exception.ServiceExceptionUtil;
import top.kwseeker.authentication.biz.domain.auth.common.constants.OAuth2ClientConstants;
import top.kwseeker.authentication.biz.domain.auth.convert.AuthConvert;
import top.kwseeker.authentication.biz.domain.auth.model.LoginReq;
import top.kwseeker.authentication.biz.domain.auth.model.LoginResp;
import top.kwseeker.authentication.biz.infrastructure.dal.po.OAuth2TokenPO;
import top.kwseeker.authentication.biz.infrastructure.dal.po.UserPO;

import javax.annotation.Resource;

@Service
public class AuthService implements IAuthService {

    @Value("${auth.captcha.enable:true}")
    private Boolean captchaEnable;

    @Resource
    private CaptchaService captchaService;
    @Resource
    private IUserService userService;
    @Resource
    private IOAuth2TokenService oauth2TokenService;

    @Override
    public LoginResp login(LoginReq req) {
        //校验验证码
        validateCaptcha(req);
        //用户名密码验证
        UserPO userPO = authenticate(req.getUsername(), req.getPassword());
        //创建Token
        OAuth2TokenPO tokenPO = oauth2TokenService.createAccessToken(userPO, OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        return AuthConvert.INSTANCE.convert(tokenPO);
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

    /**
     * 用户密码认证，比较传参和数据库中的用户名密码是否相等
     */
    @Override
    public UserPO authenticate(String username, String password) {
        UserPO userPO = userService.getUserByUsername(username);
        if (userPO == null) {
            throw ServiceExceptionUtil.exception(ErrorCodes.AUTH_LOGIN_BAD_CREDENTIALS);
        }
        //用户是否有效
        if (ObjectUtil.equal(userPO.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            throw ServiceExceptionUtil.exception(ErrorCodes.AUTH_LOGIN_USER_DISABLED);
        }
        //密码是否正确
        if (!userService.isPasswordMatch(password, userPO.getPassword())) {
            throw ServiceExceptionUtil.exception(ErrorCodes.AUTH_LOGIN_BAD_CREDENTIALS);
        }
        return userPO;
    }
}
