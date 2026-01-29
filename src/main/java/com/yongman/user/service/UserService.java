package com.yongman.user.service;

import com.yongman.user.entity.MemberType;
import com.yongman.user.entity.User;
import com.yongman.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 디바이스 ID로 사용자 조회 또는 생성
     * GUEST는 항상 "익명의유저" 닉네임 사용
     */
    @Transactional
    public User getOrCreateUser(String deviceId) {
        return userRepository.findByEmail(deviceId)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .email(deviceId)
                                .nickname(User.ANONYMOUS_NICKNAME)
                                .memberType(MemberType.GUEST)
                                .build()
                ));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Optional<User> findByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }

    /**
     * 카카오 로그인 처리
     * 1. kakaoId로 기존 회원 조회 -> 있으면 반환
     * 2. deviceId로 GUEST 조회 -> 있으면 카카오 연동
     * 3. 둘 다 없으면 새 MEMBER 생성
     *
     * @param kakaoNickname 카카오에서 받은 닉네임 (참고용)
     * @param nickname 사용자가 직접 설정한 닉네임
     */
    @Transactional
    public User loginWithKakao(String deviceId, String kakaoId, String kakaoNickname, String nickname) {
        // 1. 이미 카카오로 가입한 회원인지 확인
        Optional<User> existingMember = userRepository.findByKakaoId(kakaoId);
        if (existingMember.isPresent()) {
            return existingMember.get();
        }

        // 2. 해당 디바이스의 GUEST가 있으면 카카오 계정 연동
        Optional<User> guestUser = userRepository.findByEmail(deviceId);
        if (guestUser.isPresent()) {
            User user = guestUser.get();
            user.linkKakaoAccount(kakaoId, kakaoNickname, nickname);
            return user;
        }

        // 3. 새 MEMBER 생성
        return userRepository.save(
                User.builder()
                        .email(deviceId)
                        .nickname(nickname)
                        .kakaoId(kakaoId)
                        .kakaoNickname(kakaoNickname)
                        .memberType(MemberType.MEMBER)
                        .build()
        );
    }

    /**
     * 닉네임 변경 (MEMBER만 가능)
     */
    @Transactional
    public User updateNickname(String deviceId, String newNickname) {
        User user = findByEmail(deviceId);
        user.updateNickname(newNickname);
        return user;
    }
}