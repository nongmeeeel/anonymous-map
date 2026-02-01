package com.yongman.comment.controller;

import com.yongman.comment.dto.CommentRequest;
import com.yongman.comment.dto.CommentResponse;
import com.yongman.comment.entity.Comment;
import com.yongman.comment.service.CommentService;
import com.yongman.common.response.ApiResponse;
import com.yongman.post.entity.Post;
import com.yongman.post.service.PostService;
import com.yongman.user.entity.User;
import com.yongman.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ApiResponse<CommentResponse> createComment(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @RequestBody CommentRequest request) {

        // 로그인 유저면 User 조회, 비로그인이면 null
        User user = userId != null ? userService.findById(userId) : null;
        Post post = postService.findById(request.getPostId());
        Comment comment = commentService.createComment(request, post, user);
        return ApiResponse.success(CommentResponse.from(comment));
    }

    @GetMapping
    public ApiResponse<List<CommentResponse>> getComments(@RequestParam Long postId) {
        List<Comment> comments = commentService.findByPostId(postId);

        List<CommentResponse> response = comments.stream()
                .map(CommentResponse::from)
                .toList();

        return ApiResponse.success(response);
    }
}