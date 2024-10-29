package org.example.mbti.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class MbtiAnswerDto {

    private Long userId;  // 사용자의 ID
    private Map<Long, String> answers;  // 질문 ID와 해당 답변 (예: {1: "E", 2: "I"})

    // 응답 데이터를 위한 생성자
    public MbtiAnswerDto(Long userId) {
        this.userId = userId;
        this.answers = Map.of();  // 초기화된 빈 맵
    }
}
