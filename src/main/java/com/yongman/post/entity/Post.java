package com.yongman.post.entity;

import com.yongman.common.entity.BaseEntity;
import com.yongman.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String content;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Builder
    public Post(String content, Double latitude, Double longitude, PostType postType, User user) {
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postType = postType;
        this.user = user;
    }
}