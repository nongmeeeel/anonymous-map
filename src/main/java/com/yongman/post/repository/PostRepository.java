package com.yongman.post.repository;

import com.yongman.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByRegDtBetween(LocalDateTime start, LocalDateTime end);
}
