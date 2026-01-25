package com.yongman.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequest {

    private String content;
    private Long postId;
}
