package com.eureka.kidsworld.domain.book.controller;

import com.eureka.kidsworld.domain.book.dto.BookContentDto;
import com.eureka.kidsworld.domain.book.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    // MBTI 성향을 기반으로 도서 추천
    @PostMapping("/recommend")
    public String recommendBooksByMbti(@RequestParam Map<String, Float> userMbtiPreferences, Model model) {
        List<BookContentDto> recommendedBooks = recommendationService.recommendBooksByMbti(userMbtiPreferences);
        model.addAttribute("recommendedBooks", recommendedBooks);
        return "books/recommend"; // books/recommend.html
    }
}
