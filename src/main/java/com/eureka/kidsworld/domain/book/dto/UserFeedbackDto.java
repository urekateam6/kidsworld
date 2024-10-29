package com.eureka.kidsworld.domain.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedbackDto {
    private Long feedbackId;
    private Long userId;
    private Long bookId;
    private Boolean liked;
}
