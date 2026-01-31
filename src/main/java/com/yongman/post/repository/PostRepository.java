package com.yongman.post.repository;

import com.yongman.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByRegDtBetween(LocalDateTime start, LocalDateTime end);

    // 날짜 필터 (이 날짜 이후)
    List<Post> findByRegDtAfter(LocalDateTime dateFrom);

    // 지도 뷰포트 영역 내 게시글 조회
    List<Post> findByLatitudeBetweenAndLongitudeBetween(
            Double minLat, Double maxLat,
            Double minLng, Double maxLng);

    // 지도 뷰포트 + 날짜 필터
    List<Post> findByLatitudeBetweenAndLongitudeBetweenAndRegDtAfter(
            Double minLat, Double maxLat,
            Double minLng, Double maxLng,
            LocalDateTime dateFrom);
}
