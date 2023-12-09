package top.kwseeker.authentication.biz.interfaces.captcha;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;
import top.kwseeker.authentication.biz.domain.captcha.model.CaptchaTianaiImageTrack;

import javax.annotation.Resource;

/**
 * 这个还需要手动添加模板
 */
@Deprecated
@RestController
@RequestMapping("/captcha/tianai")
public class CaptchaTianaiController {

    @Resource
    private ImageCaptchaApplication captchaService2;

    @GetMapping("/get")
    public  ApiResponse<CaptchaResponse<ImageCaptchaVO>> get(String captchaType) {
        if (StrUtil.isBlank(captchaType)) {
            captchaType = CaptchaTypeConstant.JIGSAW;
        }

        CaptchaResponse<ImageCaptchaVO> captcha = captchaService2.generateCaptcha(captchaType);
        return ApiResponse.ofSuccess(captcha);
    }

    @PostMapping("/check")
    public ApiResponse<?> check(@RequestBody CaptchaTianaiImageTrack track) {
        return captchaService2.matching(track.getId(), track);
    }
}
