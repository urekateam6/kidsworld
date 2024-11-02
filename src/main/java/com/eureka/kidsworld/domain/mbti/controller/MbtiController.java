package com.eureka.kidsworld.domain.mbti.controller;

import com.eureka.kidsworld.domain.mbti.dto.MbtiHistoryDto;
import com.eureka.kidsworld.domain.mbti.dto.MbtiQuestionDto;
import com.eureka.kidsworld.domain.mbti.dto.MbtiResultDto;
import com.eureka.kidsworld.domain.mbti.service.MbtiService;
import com.eureka.kidsworld.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mbti")

public class MbtiController {

    private final MbtiService mbtiService;
    private final UserService userService;
    // 특정 질문을 보여주는 페이지
    @GetMapping("/question/{id}")
    public String getQuestionPage(@PathVariable Long id, Model model) {
        MbtiQuestionDto question = mbtiService.getMbtiQuestionById(id);
        model.addAttribute("question", question);
        return "mbtiQuestion";
    }

    @PostMapping("/answer")
    public String handleAnswer(@RequestParam int questionId,
                               @RequestParam String answer,
                               @AuthenticationPrincipal UserDetails userDetails,
                               HttpSession session) {
        // userDetails에서 username을 가져온 후 UserService를 통해 userId 조회
        String username = userDetails.getUsername();
        Long userId = userService.findUserIdByUsername(username);

        // userId가 null인지 확인하여 예외 처리
        if (userId == null) {
            throw new IllegalArgumentException("User ID가 세션에 없습니다. 로그인을 확인해 주세요.");
        }

        // 응답을 DB에 저장
        mbtiService.saveAnswer(userId, (long) questionId, answer);

        // 세션에 응답을 저장하여 세션에서도 관리
        Map<Integer, String> answers = (Map<Integer, String>) session.getAttribute("answers");
        if (answers == null) {
            answers = new HashMap<>();
        }
        answers.put(questionId, answer);
        session.setAttribute("answers", answers);

        // 다음 질문으로 이동 또는 결과 페이지로 리디렉션
        if (questionId >= 12) {
            return "redirect:/mbti/result";
        }
        return "redirect:/mbti/question/" + (questionId + 1);
    }



    @GetMapping("/result")
    public String getResultPage(@AuthenticationPrincipal UserDetails userDetails, HttpSession session, Model model) {
        // 세션에서 응답 목록 가져오기
        Map<Integer, String> answers = (Map<Integer, String>) session.getAttribute("answers");

        // MBTI 유형 계산
        String mbtiType = mbtiService.calculateMbtiType(answers);

        // username을 통해 userId를 조회
        String username = userDetails.getUsername();
        Long userId = userService.findUserIdByUsername(username);

        // userId가 null인지 확인하여 예외 처리
        if (userId == null) {
            throw new IllegalArgumentException("User ID를 찾을 수 없습니다. 로그인 상태를 확인해 주세요.");
        }

        // MBTI 결과 저장
        mbtiService.saveMbtiResult(userId, mbtiType);

        // 결과 DTO 생성 및 모델에 추가
        MbtiResultDto resultDto = mbtiService.getMbtiResult(mbtiType);
        model.addAttribute("result", resultDto);

        // 세션에서 answers 속성 제거
        session.removeAttribute("answers");

        return "mbtiResult";
    }

    @PostMapping("/delete")
    public String deleteMbtiResult(@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        String username = userDetails.getUsername();
        Long userId = userService.findUserIdByUsername(username);

        if (userId == null) {
            throw new IllegalArgumentException("User ID를 찾을 수 없습니다. 로그인 상태를 확인해 주세요.");
        }

        mbtiService.deleteChildMbtiAnswers(userId); // MBTI 결과 논리 삭제
        redirectAttributes.addFlashAttribute("message", "MBTI 결과가 삭제되었습니다!");
        return "redirect:/main";
    }

    @GetMapping("/history")
    public String getUserHistory(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        Long userId = userService.findUserIdByUsername(username);

        if (userId == null) {
            throw new IllegalArgumentException("User ID를 찾을 수 없습니다. 로그인 상태를 확인해 주세요.");
        }

        List<MbtiHistoryDto> history = mbtiService.getUserHistory(userId);
        model.addAttribute("history", history);
        return "mbtiHistory"; // 히스토리 보기 페이지로 이동
    }@PostMapping("/reset")

    public String resetMbtiResult(@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        String username = userDetails.getUsername();
        Long userId = userService.findUserIdByUsername(username);

        if (userId == null) {
            throw new IllegalArgumentException("User ID를 찾을 수 없습니다. 로그인 상태를 확인해 주세요.");
        }

        mbtiService.resetMbtiAnswers(userId); // 이전 응답들을 논리적으로 삭제
        redirectAttributes.addFlashAttribute("message", "다시 해보기가 실행되었습니다.");
        return "redirect:/mbti/question/1"; // 메인 페이지로 리다이렉트
    }




}
