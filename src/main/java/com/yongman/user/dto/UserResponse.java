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
        if (user == null) {
            return anonymous();
        }
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .displayNickname(user.getDisplayNickname())
                .memberType(user.getMemberType())
                .build();
    }

    // 익명 유저 (비로그인)
    public static UserResponse anonymous() {
        return UserResponse.builder()
                .id(null)
                .email(null)
                .nickname("익명의유저")
                .displayNickname("익명의유저")
                .memberType(null)
                .build();
    }
}