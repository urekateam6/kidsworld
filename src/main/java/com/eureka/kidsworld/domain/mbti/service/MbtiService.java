package com.eureka.kidsworld.domain.mbti.service;

import com.eureka.kidsworld.domain.mbti.dto.MbtiHistoryDto;
import com.eureka.kidsworld.domain.mbti.dto.MbtiQuestionDto;
import com.eureka.kidsworld.domain.mbti.dto.MbtiResultDto;
import com.eureka.kidsworld.domain.mbti.entity.MbtiAnswer;
import com.eureka.kidsworld.domain.mbti.entity.MbtiQuestion;
import com.eureka.kidsworld.domain.mbti.entity.MbtiResult;
import com.eureka.kidsworld.domain.mbti.entity.MbtiTrait;
import com.eureka.kidsworld.domain.mbti.repository.MbtiAnswerRepository;
import com.eureka.kidsworld.domain.mbti.repository.MbtiQuestionRepository;
import com.eureka.kidsworld.domain.mbti.repository.MbtiResultRepository;
import com.eureka.kidsworld.domain.mbti.repository.MbtiTraitRepository;
import com.eureka.kidsworld.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MbtiService {

    private final MbtiQuestionRepository mbtiQuestionRepository;
    private final MbtiTraitRepository mbtiTraitRepository;
    private final MbtiResultRepository mbtiResultRepository;
    private final MbtiAnswerRepository mbtiAnswerRepository;
    private final UserService userService;

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
                case "E":
                    eCount++;
                    break;
                case "I":
                    iCount++;
                    break;
                case "S":
                    sCount++;
                    break;
                case "N":
                    nCount++;
                    break;
                case "T":
                    tCount++;
                    break;
                case "F":
                    fCount++;
                    break;
                case "J":
                    jCount++;
                    break;
                case "P":
                    pCount++;
                    break;
            }
        }

        String eOrI = (eCount >= iCount) ? "E" : "I";
        String sOrN = (sCount >= nCount) ? "S" : "N";
        String tOrF = (tCount >= fCount) ? "T" : "F";
        String jOrP = (jCount >= pCount) ? "J" : "P";

        return eOrI + sOrN + tOrF + jOrP;
    }

    public void saveAnswer(Long userId, Long questionId, String answer) {
        MbtiAnswer mbtiAnswer = MbtiAnswer.builder()
                .userId(userId)
                .questionId(questionId)
                .answer(answer)
                .build();
        mbtiAnswerRepository.save(mbtiAnswer);
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

    public void saveMbtiResult(Long userId, String mbtiType) {
        MbtiResult result = MbtiResult.builder()
                .userId(userId)
                .mbtiResult(mbtiType)
                .calculatedAt(LocalDateTime.now())
                .build();
        mbtiResultRepository.save(result);

        userService.updateChildMbti(userId, mbtiType);
    }

    public void deleteChildMbtiAnswers(Long userId) {
        List<MbtiAnswer> answers = mbtiAnswerRepository.findByUserIdAndDeletedAtIsNull(userId);
        LocalDateTime now = LocalDateTime.now();

        answers.forEach(answer -> answer.setDeletedAt(now));
        mbtiAnswerRepository.saveAll(answers);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldMbtiAnswers() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        mbtiAnswerRepository.deleteByDeletedAtBefore(oneMonthAgo);
    }

    public List<MbtiHistoryDto> getUserHistory(Long userId) {
        List<MbtiAnswer> answers = mbtiAnswerRepository.findByUserIdAndDeletedAtIsNull(userId);
        List<MbtiHistoryDto> historyDtos = new ArrayList<>();

        for (MbtiAnswer answer : answers) {
            MbtiQuestion question = mbtiQuestionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다. Question ID: " + answer.getQuestionId()));

            String selectedAnswer = "알 수 없는 선택입니다.";

            if (answer.getAnswer().equals(question.getMbtiTypeA())) {
                selectedAnswer = question.getOptionA();
            } else if (answer.getAnswer().equals(question.getMbtiTypeB())) {
                selectedAnswer = question.getOptionB();
            }

            MbtiHistoryDto historyDto = new MbtiHistoryDto(question.getQuestionText(), selectedAnswer);
            historyDtos.add(historyDto);
        }
        return historyDtos;
    }

    public void resetMbtiAnswers(Long userId) {
        List<MbtiAnswer> answers = mbtiAnswerRepository.findByUserIdAndDeletedAtIsNull(userId);
        LocalDateTime now = LocalDateTime.now();

        answers.forEach(answer -> answer.setDeletedAt(now));
        mbtiAnswerRepository.saveAll(answers);
    }
}
