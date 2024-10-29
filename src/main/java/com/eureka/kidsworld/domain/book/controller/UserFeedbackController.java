package com.eureka.kidsworld.domain.book.controller;

import com.eureka.kidsworld.domain.book.dto.UserFeedbackDto;
import com.eureka.kidsworld.domain.book.entity.BookContent;
import com.eureka.kidsworld.domain.book.service.BookContentService;
import com.eureka.kidsworld.domain.book.service.UserFeedbackService;
import com.eureka.kidsworld.domain.user.entity.User;
import com.eureka.kidsworld.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class UserFeedbackController {
    private final UserFeedbackService userFeedbackService;
    private final UserService userService;
    private final BookContentService bookContentService;

    // 피드백 목록 페이지
    @GetMapping
    public String getFeedbackList(Model model) {
        List<UserFeedbackDto> feedbackList = userFeedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbackList);
        return "feedback/list"; // feedback/list.html 페이지를 렌더링
    }

    // 피드백 추가 폼 페이지
    @GetMapping("/new")
    public String showAddFeedbackForm(Model model) {
        model.addAttribute("feedback", new UserFeedbackDto());
        return "feedback/add"; // feedback/add.html 페이지를 렌더링
    }

    // 피드백 추가 처리
    @PostMapping
    public String addFeedback(@ModelAttribute("feedback") UserFeedbackDto feedbackDto) {

        userFeedbackService.addFeedback(feedbackDto);
        return "redirect:/feedback";
    }

    // 피드백 상세 페이지
    @GetMapping("/{id}")
    public String getFeedbackDetail(@PathVariable Long id, Model model) {
        UserFeedbackDto feedbackDto = userFeedbackService.getFeedbackById(id);
        model.addAttribute("feedback", feedbackDto);
        return "feedback/detail"; // feedback/detail.html 페이지를 렌더링
    }
}
