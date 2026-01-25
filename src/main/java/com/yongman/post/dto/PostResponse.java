package com.yongman.post.dto;

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

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .postType(post.getPostType())
                .user(UserResponse.from(post.getUser()))
                .regDt(post.getRegDt())
                .build();
    }
}
