package top.kwseeker.authentication.biz.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResp {

    private Long userId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiresTime;
}