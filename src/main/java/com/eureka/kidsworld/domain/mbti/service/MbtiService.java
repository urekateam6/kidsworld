package com.eureka.kidsworld.domain.mbti.service;

import com.eureka.kidsworld.domain.mbti.dto.MbtiQuestionDto;
import com.eureka.kidsworld.domain.mbti.dto.MbtiResultDto;
import com.eureka.kidsworld.domain.mbti.entity.MbtiQuestion;
import com.eureka.kidsworld.domain.mbti.entity.MbtiResult;
import com.eureka.kidsworld.domain.mbti.entity.MbtiTrait;
import com.eureka.kidsworld.domain.mbti.repository.MbtiQuestionRepository;
import com.eureka.kidsworld.domain.mbti.repository.MbtiResultRepository;
import com.eureka.kidsworld.domain.mbti.repository.MbtiTraitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MbtiService {

    private final MbtiQuestionRepository mbtiQuestionRepository;
    private final MbtiTraitRepository mbtiTraitRepository;
    private final MbtiResultRepository mbtiResultRepository; // 결과 리포지토리 추가


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
    public void saveMbtiResult(Long childId, String mbtiType) {
        MbtiResult result = MbtiResult.builder()
                .childId(childId) // User ID를 childId로 설정
                .mbtiResult(mbtiType)
                .build();

        mbtiResultRepository.save(result); // mbtiResultRepository를 통해 DB에 저장
    }

}
