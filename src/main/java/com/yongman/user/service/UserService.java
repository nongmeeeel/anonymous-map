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

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
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
     * - kakaoId로 기존 회원 조회 → 있으면 반환
     * - 없으면 새 MEMBER 생성
     */
    @Transactional
    public User loginWithKakao(String kakaoId, String kakaoNickname, String nickname) {
        return userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .email(kakaoId)
                                .nickname(nickname)
                                .kakaoId(kakaoId)
                                .kakaoNickname(kakaoNickname)
                                .memberType(MemberType.MEMBER)
                                .build()
                ));
    }

    /**
     * 닉네임 변경 (MEMBER만 가능)
     * userId로 직접 조회
     */
    @Transactional
    public User updateNickname(Long userId, String newNickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.updateNickname(newNickname);
        return user;
    }
}