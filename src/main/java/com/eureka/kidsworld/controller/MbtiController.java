package com.eureka.kidsworld.controller;

import lombok.RequiredArgsConstructor;
import com.eureka.kidsworld.service.MbtiService;  // 서비스 경로 수정
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
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
        MbtiResultDto resultDto = mbtiService.getMbtiResult(mbtiType);
        model.addAttribute("result", resultDto);
        session.removeAttribute("answers");
        return "mbtiResult";
    }
}
