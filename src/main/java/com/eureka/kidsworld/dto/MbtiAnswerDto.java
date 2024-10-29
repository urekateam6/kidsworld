package com.eureka.kidsworld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class MbtiAnswerDto {
    private Long userId;
    private Map<Long, String> answers;

    public MbtiAnswerDto(Long userId) {
        this.userId = userId;
        this.answers = Map.of();
    }
}
