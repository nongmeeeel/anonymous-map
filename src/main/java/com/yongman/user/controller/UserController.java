package com.yongman.user.controller;

import com.yongman.common.response.ApiResponse;
import com.yongman.user.dto.UserResponse;
import com.yongman.user.entity.User;
import com.yongman.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 내 정보 조회 (로그인한 MEMBER만 가능)
     */
    @GetMapping("/me")
    public ApiResponse<UserResponse> getMe(@RequestHeader("X-User-Id") Long userId) {
        User user = userService.findById(userId);
        return ApiResponse.success(UserResponse.from(user));
    }

    @PutMapping("/nickname")
    public ApiResponse<UserResponse> updateNickname(
            @RequestParam Long userId,
            @RequestParam String nickname) {
        User user = userService.updateNickname(userId, nickname);
        return ApiResponse.success(UserResponse.from(user));
    }
}