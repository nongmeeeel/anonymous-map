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

    @PostMapping
    public ApiResponse<UserResponse> getOrCreateUser(
            @RequestHeader("X-Device-Id") String deviceId) {
        User user = userService.getOrCreateUser(deviceId);
        return ApiResponse.success(UserResponse.from(user));
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getMe(@RequestHeader("X-Device-Id") String deviceId) {
        User user = userService.findByEmail(deviceId);
        return ApiResponse.success(UserResponse.from(user));
    }

    @PutMapping("/nickname")
    public ApiResponse<UserResponse> updateNickname(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestParam String nickname) {
        User user = userService.updateNickname(deviceId, nickname);
        return ApiResponse.success(UserResponse.from(user));
    }
}