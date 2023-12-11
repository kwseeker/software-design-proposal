//package top.kwseeker.authentication.biz.interfaces.captcha;
//
//import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
//import cloud.tianai.captcha.common.response.ApiResponse;
//import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
//import cloud.tianai.captcha.spring.vo.CaptchaResponse;
//import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
//import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//
//@SpringBootTest
//class CaptchaTianaiControllerTest {
//
//    @Resource
//    private ImageCaptchaApplication application;
//
//    @Test
//    public void testImageCaptchaApplication() {
//        CaptchaResponse<ImageCaptchaVO> res1 = application.generateCaptcha(CaptchaTypeConstant.SLIDER);
//        ImageCaptchaTrack sliderCaptchaTrack = new ImageCaptchaTrack();
//        ApiResponse<?> match = application.matching(res1.getId(), sliderCaptchaTrack);
//    }
//}