package com.yongman.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yongman.post.entity.Post;
import com.yongman.post.entity.PostType;
import com.yongman.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private Double latitude;
    private Double longitude;
    private PostType postType;
    private UserResponse user;
    private LocalDateTime regDt;
    @JsonProperty("isHot")
    private boolean isHot;

    public static PostResponse from(Post post) {
        return from(post, false);
    }

    public static PostResponse from(Post post, boolean isHot) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .postType(post.getPostType())
                .user(UserResponse.from(post.getUser()))
                .regDt(post.getRegDt())
                .isHot(isHot)
                .build();
    }
}
