package com.eureka.kidsworld.domain.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserFeedbackDto {
    private Long feedbackId;
    private Long userId;
    private Long bookId;
    private Boolean isLike;
}
