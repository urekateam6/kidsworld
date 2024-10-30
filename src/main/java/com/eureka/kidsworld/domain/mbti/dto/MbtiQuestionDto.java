package com.eureka.kidsworld.domain.mbti.dto;

import com.eureka.kidsworld.domain.mbti.entity.MbtiQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MbtiQuestionDto {
    private Long id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String mbtiTypeA;
    private String mbtiTypeB;
    private String imageFile;

    public static MbtiQuestionDto from(MbtiQuestion entity) {
        return new MbtiQuestionDto(
                entity.getId(),
                entity.getQuestionText(),
                entity.getOptionA(),
                entity.getOptionB(),
                entity.getMbtiTypeA(),
                entity.getMbtiTypeB(),
                entity.getImageFile()
        );
    }

    public String getFullImagePath() {
        return "/images/" + this.imageFile;
    }
}
