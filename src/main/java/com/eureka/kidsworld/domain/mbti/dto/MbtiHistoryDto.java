package com.eureka.kidsworld.domain.mbti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MbtiHistoryDto {
    private String question; // 질문 내용
    private String answer;   // 사용자의 답변
}
