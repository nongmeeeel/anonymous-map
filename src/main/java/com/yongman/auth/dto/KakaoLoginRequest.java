package com.yongman.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginRequest {
    private String kakaoId;        // 카카오 사용자 고유 ID
    private String kakaoNickname;  // 카카오에서 받은 닉네임 (참고용)
    private String nickname;       // 사용자가 직접 설정한 닉네임
}
