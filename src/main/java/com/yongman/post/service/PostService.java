package com.yongman.post.service;

import com.yongman.post.dto.PostRequest;
import com.yongman.post.entity.Post;
import com.yongman.post.repository.PostRepository;
import com.yongman.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostRequest request, User user) {
        Post post = Post.builder()
                .content(request.getContent())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .postType(request.getPostType())
                .user(user)
                .build();

        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return postRepository.findByRegDtBetween(start, end);
    }
}
