package com.eureka.kidsworld.service;

import lombok.RequiredArgsConstructor;
import com.eureka.kidsworld.dto.MbtiQuestionDto;  // DTO 경로 수정
import com.eureka.kidsworld.dto.MbtiResultDto;    // DTO 경로 수정
import com.eureka.kidsworld.domain.entity.MbtiQuestion;  // 엔티티 경로 수정
import com.eureka.kidsworld.domain.entity.MbtiTrait;     // 엔티티 경로 수정
import com.eureka.kidsworld.repository.MbtiQuestionRepository;  // 리포지토리 경로 수정
import com.eureka.kidsworld.repository.MbtiTraitRepository;     // 리포지토리 경로 수정
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MbtiService {

    private final MbtiQuestionRepository mbtiQuestionRepository;
    private final MbtiTraitRepository mbtiTraitRepository;

    public MbtiQuestionDto getMbtiQuestionById(Long id) {
        MbtiQuestion question = mbtiQuestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다. ID: " + id));
        return MbtiQuestionDto.from(question);
    }

    public String calculateMbtiType(Map<Integer, String> answers) {
        if (answers == null) {
            answers = new HashMap<>();
        }

        int eCount = 0, iCount = 0, sCount = 0, nCount = 0;
        int tCount = 0, fCount = 0, jCount = 0, pCount = 0;

        for (String answer : answers.values()) {
            switch (answer) {
                case "E": eCount++; break;
                case "I": iCount++; break;
                case "S": sCount++; break;
                case "N": nCount++; break;
                case "T": tCount++; break;
                case "F": fCount++; break;
                case "J": jCount++; break;
                case "P": pCount++; break;
            }
        }

        String eOrI = (eCount >= iCount) ? "E" : "I";
        String sOrN = (sCount >= nCount) ? "S" : "N";
        String tOrF = (tCount >= fCount) ? "T" : "F";
        String jOrP = (jCount >= pCount) ? "J" : "P";

        return eOrI + sOrN + tOrF + jOrP;
    }

    public MbtiResultDto getMbtiResult(String mbtiType) {
        MbtiTrait trait = mbtiTraitRepository.findByMbtiType(mbtiType);
        if (trait == null) {
            throw new IllegalArgumentException("해당 MBTI 타입이 존재하지 않습니다.");
        }

        return new MbtiResultDto(
                trait.getMbtiType(),
                "당신의 MBTI는 " + trait.getMbtiType() + "입니다!",
                trait.getStrength(),
                trait.getWeakness(),
                trait.getDescription(),
                trait.getImagePath()
        );
    }
}
