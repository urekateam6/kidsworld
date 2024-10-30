package org.example.mbti.service;

import lombok.RequiredArgsConstructor;
import org.example.mbti.domain.dto.MbtiQuestionDto;
import org.example.mbti.domain.entity.MbtiQuestion;
import org.example.mbti.repository.MbtiQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MbtiServiceV1 {

    private final MbtiQuestionRepository mbtiQuestionRepository;
//    private final MbtiAnswerRepository mbtiAnswerRepository;
//    private final MbtiResultRepository mbtiResultRepository;

    /** 1. MBTI 문제를 조회하는 메서드 */
    public List<MbtiQuestionDto> getMbtiQuestion() {
        List<MbtiQuestion> mbtiQuestionList = mbtiQuestionRepository.findAll();

        return mbtiQuestionList.stream()
                .map(MbtiQuestionDto::from)
                .toList();
    }

    /** 2. MBTI  응답을 받아서 저장 + 결과값  */


}
