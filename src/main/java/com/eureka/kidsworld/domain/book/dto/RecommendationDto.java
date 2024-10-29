package com.eureka.kidsworld.domain.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendationDto {
    private Long recommendationId;
    private Long userId;
    private Long bookId;
    private Float score;
}
