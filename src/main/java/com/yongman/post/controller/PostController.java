package com.yongman.post.controller;

import com.yongman.comment.repository.CommentRepository;
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
import java.util.Set;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private static final int HOT_THRESHOLD_HOURS = 1;

    private final PostService postService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @PostMapping
    public ApiResponse<PostResponse> createPost(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @RequestBody PostRequest request) {

        // 로그인 유저면 User 조회, 비로그인이면 null
        User user = userId != null ? userService.findById(userId) : null;
        Post post = postService.createPost(request, user);
        // 새로 생성된 글은 항상 hot
        return ApiResponse.success(PostResponse.from(post, true));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        Post post = postService.findById(id);
        return ApiResponse.success(PostResponse.from(post));
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getPosts(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false) Double swLat,
            @RequestParam(required = false) Double swLng,
            @RequestParam(required = false) Double neLat,
            @RequestParam(required = false) Double neLng) {

        List<Post> posts;
        if (swLat != null && swLng != null && neLat != null && neLng != null) {
            // 지도 뷰포트 영역 내 게시글 조회 (+ 날짜 필터)
            posts = postService.findByViewportAndDateFrom(swLat, swLng, neLat, neLng, dateFrom);
        } else if (start != null && end != null) {
            posts = postService.findByDateRange(start, end);
        } else if (dateFrom != null) {
            posts = postService.findByDateFrom(dateFrom);
        } else {
            posts = postService.findAll();
        }

        // Hot 게시글 판별 (1시간 이내 작성 또는 댓글)
        LocalDateTime hotThreshold = LocalDateTime.now().minusHours(HOT_THRESHOLD_HOURS);
        Set<Long> hotPostIds = commentRepository.findPostIdsWithCommentsAfter(hotThreshold);

        List<PostResponse> response = posts.stream()
                .map(post -> {
                    boolean isHot = post.getRegDt().isAfter(hotThreshold)
                            || hotPostIds.contains(post.getId());
                    return PostResponse.from(post, isHot);
                })
                .toList();

        return ApiResponse.success(response);
    }
}