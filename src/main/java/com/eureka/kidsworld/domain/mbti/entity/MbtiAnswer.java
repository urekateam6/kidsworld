package com.eureka.kidsworld.domain.mbti.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mbti_answer")
public class MbtiAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Column(name = "answer", nullable = false, length = 1)
    private String answer;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
