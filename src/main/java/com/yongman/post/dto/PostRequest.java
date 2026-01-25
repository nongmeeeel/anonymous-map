package com.yongman.post.dto;

import com.yongman.post.entity.PostType;
import lombok.Getter;

@Getter
public class PostRequest {

    private String content;
    private Double latitude;
    private Double longitude;
    private PostType postType;
}
