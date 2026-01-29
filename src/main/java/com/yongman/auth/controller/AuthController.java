package com.yongman.auth.controller;

import com.yongman.auth.dto.KakaoLoginRequest;
import com.yongman.common.response.ApiResponse;
import com.yongman.user.dto.UserResponse;
import com.yongman.user.entity.User;
import com.yongman.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 카카오 로그인
     * - 프론트에서 카카오 SDK로 로그인 후 받은 정보를 전달
     * - kakaoId로 기존 회원 조회 또는 신규 생성
     */
    @PostMapping("/kakao")
    public ApiResponse<UserResponse> kakaoLogin(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestBody KakaoLoginRequest request) {

        User user = userService.loginWithKakao(
                deviceId,
                request.getKakaoId(),
                request.getKakaoNickname(),
                request.getNickname()
        );

        return ApiResponse.success(UserResponse.from(user));
    }
}
