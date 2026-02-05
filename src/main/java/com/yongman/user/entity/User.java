package com.yongman.user.entity;

import com.yongman.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    public static final String ANONYMOUS_NICKNAME = "익명의유저";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String nickname;

    @Column(unique = true)
    private String kakaoId;

    private String kakaoNickname;  // 카카오에서 받은 닉네임 (참고용)

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    // USER, ADMIN, BOT - 나중에 제거 가능
    @Column(length = 20)
    private String role = "USER";

    @Builder
    public User(String email, String nickname, String kakaoId, String kakaoNickname, MemberType memberType, String role) {
        this.email = email;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.kakaoNickname = kakaoNickname;
        this.memberType = memberType;
        this.role = role != null ? role : "USER";
    }

    // 카카오 로그인으로 MEMBER 전환
    public void linkKakaoAccount(String kakaoId, String kakaoNickname, String nickname) {
        this.kakaoId = kakaoId;
        this.kakaoNickname = kakaoNickname;
        this.nickname = nickname;
        this.memberType = MemberType.MEMBER;
    }

    // 닉네임 변경 (MEMBER만 가능)
    public void updateNickname(String nickname) {
        if (this.memberType != MemberType.MEMBER) {
            throw new IllegalStateException("GUEST는 닉네임을 변경할 수 없습니다. 로그인 후 이용해주세요.");
        }
        this.nickname = nickname;
    }

    // 표시용 닉네임 (GUEST는 항상 "익명의유저")
    public String getDisplayNickname() {
        return this.memberType == MemberType.GUEST ? ANONYMOUS_NICKNAME : this.nickname;
    }
}