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
     * 카카오 ID로 기존 회원 확인
     * - 기존 회원이면 UserResponse 반환
     * - 신규 회원이면 null 반환 (data가 null)
     */
    @GetMapping("/kakao/check")
    public ApiResponse<UserResponse> checkKakaoUser(@RequestParam String kakaoId) {
        return userService.findByKakaoId(kakaoId)
                .map(user -> ApiResponse.success(UserResponse.from(user)))
                .orElse(ApiResponse.success(null));
    }

    /**
     * 카카오 로그인
     * - 프론트에서 카카오 SDK로 로그인 후 받은 정보를 전달
     * - kakaoId로 기존 회원 조회 또는 신규 생성
     */
    @PostMapping("/kakao")
    public ApiResponse<UserResponse> kakaoLogin(@RequestBody KakaoLoginRequest request) {
        User user = userService.loginWithKakao(
                request.getKakaoId(),
                request.getKakaoNickname(),
                request.getNickname()
        );

        return ApiResponse.success(UserResponse.from(user));
    }
}
