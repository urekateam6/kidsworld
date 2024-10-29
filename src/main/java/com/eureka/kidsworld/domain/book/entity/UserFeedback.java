package com.eureka.kidsworld.domain.book.entity;

import com.eureka.kidsworld.domain.user.entity.User;
import com.eureka.kidsworld.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFeedback extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookContent bookContent;

    private Boolean liked;

    @Builder
    public UserFeedback(User user, BookContent bookContent, Boolean liked) {
        this.user = user;
        this.bookContent = bookContent;
        this.liked = liked;
    }
}
