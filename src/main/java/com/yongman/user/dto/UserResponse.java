package com.yongman.user.dto;

import com.yongman.user.entity.MemberType;
import com.yongman.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private MemberType memberType;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .memberType(user.getMemberType())
                .build();
    }
}