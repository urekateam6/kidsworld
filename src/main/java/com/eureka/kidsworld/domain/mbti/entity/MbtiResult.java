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
@Table(name = "mbti_results")
public class MbtiResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @Column(name = "user_id", nullable = false) // user_id로 수정
    private Long userId; // User ID를 저장

    @Column(name = "mbti_result", nullable = false, length = 4)
    private String mbtiResult; // MBTI 결과

    @Column(name = "calculated_at", nullable = false)
    private LocalDateTime calculatedAt;

    @PrePersist
    public void setCalculatedAt() {
        this.calculatedAt = LocalDateTime.now();
    }
}
