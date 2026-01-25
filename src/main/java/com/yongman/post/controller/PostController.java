package com.yongman.post.controller;

import com.yongman.common.response.ApiResponse;
import com.yongman.post.dto.PostRequest;
import com.yongman.post.dto.PostResponse;
import com.yongman.post.entity.Post;
import com.yongman.post.service.PostService;
import com.yongman.user.entity.User;
import com.yongman.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ApiResponse<PostResponse> createPost(
            @RequestHeader("X-Device-Id") String deviceId,
            @RequestBody PostRequest request) {

        User user = userService.findByEmail(deviceId);
        Post post = postService.createPost(request, user);
        return ApiResponse.success(PostResponse.from(post));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        Post post = postService.findById(id);
        return ApiResponse.success(PostResponse.from(post));
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getPosts(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<Post> posts;
        if (start != null && end != null) {
            posts = postService.findByDateRange(start, end);
        } else {
            posts = postService.findAll();
        }

        List<PostResponse> response = posts.stream()
                .map(PostResponse::from)
                .toList();

        return ApiResponse.success(response);
    }
}