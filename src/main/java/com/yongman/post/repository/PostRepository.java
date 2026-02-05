package com.yongman.post.repository;

import com.yongman.post.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 단건 조회 (User 포함)
    @Override
    @EntityGraph(attributePaths = {"user"})
    Optional<Post> findById(Long id);

    // 날짜 범위 조회
    @EntityGraph(attributePaths = {"user"})
    List<Post> findByRegDtBetween(LocalDateTime start, LocalDateTime end);

    // 날짜 이후 조회
    @EntityGraph(attributePaths = {"user"})
    List<Post> findByRegDtAfter(LocalDateTime dateFrom);

    // 뷰포트 조회
    @EntityGraph(attributePaths = {"user"})
    List<Post> findByLatitudeBetweenAndLongitudeBetween(
            Double minLat, Double maxLat,
            Double minLng, Double maxLng);

    // 뷰포트 + 날짜 필터
    @EntityGraph(attributePaths = {"user"})
    List<Post> findByLatitudeBetweenAndLongitudeBetweenAndRegDtAfter(
            Double minLat, Double maxLat,
            Double minLng, Double maxLng,
            LocalDateTime dateFrom);
}
