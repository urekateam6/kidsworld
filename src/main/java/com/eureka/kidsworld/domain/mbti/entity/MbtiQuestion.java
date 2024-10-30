package com.eureka.kidsworld.domain.mbti.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "mbti_question")
public class MbtiQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String questionText;

    @Column(nullable = false, length = 300)
    private String optionA;

    @Column(nullable = false, length = 300)
    private String optionB;

    @Column(nullable = false, length = 1)
    private String mbtiTypeA;

    @Column(nullable = false, length = 1)
    private String mbtiTypeB;

    @Column(name = "image_file", length = 255)
    private String imageFile;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public MbtiQuestion(String questionText, String optionA, String optionB,
                        String mbtiTypeA, String mbtiTypeB, String imageFile) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.mbtiTypeA = mbtiTypeA;
        this.mbtiTypeB = mbtiTypeB;
        this.imageFile = imageFile;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    // 편의 메서드: 이미지 경로 반환 시 /images/ 경로 자동 추가
    public String getFullImagePath() {
        return "/images/" + this.imageFile;
    }

    @Override
    public String toString() {
        return "MbtiQuestion{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", mbtiTypeA='" + mbtiTypeA + '\'' +
                ", mbtiTypeB='" + mbtiTypeB + '\'' +
                ", imageFile='" + imageFile + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
