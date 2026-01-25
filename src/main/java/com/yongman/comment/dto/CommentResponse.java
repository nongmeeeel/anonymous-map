package com.yongman.comment.dto;

import com.yongman.comment.entity.Comment;
import com.yongman.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {

    private Long id;
    private String content;
    private UserResponse user;
    private LocalDateTime regDt;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(UserResponse.from(comment.getUser()))
                .regDt(comment.getRegDt())
                .build();
    }
}
