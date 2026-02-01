package com.yongman.comment.repository;

import com.yongman.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);

    /**
     * 특정 시간 이후에 댓글이 달린 게시글 ID 목록 조회
     */
    @Query("SELECT DISTINCT c.post.id FROM Comment c WHERE c.regDt >= :since")
    Set<Long> findPostIdsWithCommentsAfter(@Param("since") LocalDateTime since);
}
