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
    private String displayNickname;  // GUEST는 "익명의유저", MEMBER는 실제 닉네임
    private MemberType memberType;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .displayNickname(user.getDisplayNickname())
                .memberType(user.getMemberType())
                .build();
    }
}