package com.eureka.kidsworld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MbtiResultDto {
    private String mbtiType;
    private String message;
    private String strength;
    private String weakness;
    private String description;
    private String imagePath;

    public static MbtiResultDto create(String mbtiType, String strength, String weakness, String description, String imagePath) {
        String message = "당신의 MBTI는 " + mbtiType + "입니다!";
        return new MbtiResultDto(mbtiType, message, strength, weakness, description, imagePath);
    }
}
