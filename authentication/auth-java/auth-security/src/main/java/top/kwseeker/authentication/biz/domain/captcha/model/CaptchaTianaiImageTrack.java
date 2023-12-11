package top.kwseeker.authentication.biz.domain.captcha.model;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CaptchaTianaiImageTrack extends ImageCaptchaTrack {

    private String id;
}

