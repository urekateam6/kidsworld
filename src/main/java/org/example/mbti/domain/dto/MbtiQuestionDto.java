package org.example.mbti.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.mbti.domain.entity.MbtiQuestion;

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

    // 편의 메서드: 이미지 경로 반환
    public String getFullImagePath() {
        return "/images/" + this.imageFile;
    }
}
