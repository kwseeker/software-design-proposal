package top.kwseeker.authentication.biz.interfaces.captcha;

import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static top.kwseeker.authentication.biz.infrastructure.util.ServletUtils.getRemoteId;

@RestController
@RequestMapping("/captcha/xyv")
public class CaptchaXyvController {

    @Resource
    private CaptchaService captchaService;

    @PostMapping("/get")
    public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
        //browserInfo 要求是“客户端ip+userAgent”
        data.setBrowserInfo(getRemoteId(request));
        return captchaService.get(data);
    }

    /**
     * 用于给前端核对验证码
     */
    @PostMapping("/check")
    public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
        data.setBrowserInfo(getRemoteId(request));
        return captchaService.check(data);
    }
}
