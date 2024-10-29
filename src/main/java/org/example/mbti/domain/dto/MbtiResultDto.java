package org.example.mbti.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MbtiResultDto {
    private String mbtiType;  // MBTI 유형 (예: "ENTP")
    private String message;   // 결과 메시지
    private String strength;   // 강점
    private String weakness;   // 약점
    private String description; // 설명
    private String imagePath;   // 이미지 경로

    public static MbtiResultDto create(String mbtiType, String strength, String weakness, String description, String imagePath) {
        String message = "당신의 MBTI는 " + mbtiType + "입니다!";
        return new MbtiResultDto(mbtiType, message, strength, weakness, description, imagePath);
    }
}
