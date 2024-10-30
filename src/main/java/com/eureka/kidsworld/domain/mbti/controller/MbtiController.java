package com.eureka.kidsworld.domain.mbti.controller;

import com.eureka.kidsworld.domain.mbti.dto.MbtiQuestionDto;
import com.eureka.kidsworld.domain.mbti.dto.MbtiResultDto;
import com.eureka.kidsworld.domain.mbti.service.MbtiService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mbti")
public class MbtiController {

    private final MbtiService mbtiService;

    // 특정 질문을 보여주는 페이지
    @GetMapping("/question/{id}")
    public String getQuestionPage(@PathVariable Long id, Model model) {
        MbtiQuestionDto question = mbtiService.getMbtiQuestionById(id);
        model.addAttribute("question", question);
        return "mbtiQuestion";
    }

    // 사용자가 답변을 선택하면 세션에 저장하고 다음 질문으로 이동
    @PostMapping("/answer")
    public String handleAnswer(@RequestParam int questionId,
                               @RequestParam String answer,
                               HttpSession session) {
        Map<Integer, String> answers = (Map<Integer, String>) session.getAttribute("answers");
        if (answers == null) {
            answers = new HashMap<>();
        }
        answers.put(questionId, answer);
        session.setAttribute("answers", answers);

        if (questionId >= 12) {
            return "redirect:/mbti/result";
        }
        return "redirect:/mbti/question/" + (questionId + 1);
    }

    // 결과 페이지 렌더링
    @GetMapping("/result")
    public String getResultPage(HttpSession session, Model model) {
        Map<Integer, String> answers = (Map<Integer, String>) session.getAttribute("answers");
        String mbtiType = mbtiService.calculateMbtiType(answers);

        // Child ID를 얻는 방법 (User ID를 통해 얻거나 세션에서 가져올 수 있음)
        Long childId = (Long) session.getAttribute("child_id"); // 예시로 세션에서 가져오는 경우

        // MBTI 결과 저장
        mbtiService.saveMbtiResult(childId, mbtiType);

        MbtiResultDto resultDto = mbtiService.getMbtiResult(mbtiType);
        model.addAttribute("result", resultDto);
        session.removeAttribute("answers");
        return "mbtiResult";
    }


}
