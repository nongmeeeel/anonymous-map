package com.yongman.user.repository;

import com.yongman.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByKakaoId(String kakaoId);

    // 봇 유저 조회 (Admin 기능용) - 닉네임 오름차순 정렬
    List<User> findByRoleOrderByNicknameAsc(String role);
}