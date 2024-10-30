package com.eureka.kidsworld.ai.controller;

import com.eureka.kidsworld.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/ai")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AiController {
    private final AiService aiService;

    @GetMapping
    public ResponseEntity<?> getRecommendedMbtiByBook(@RequestParam(name = "title") String title,
                                                       @RequestParam(name = "author") String author) {
        String mbti = aiService.getRecommendedMbtiByBook(title, author);
        // INTJ -> 이렇게 딱 4글자로 오게 설정해둠

        return ResponseEntity.ok().body("success");
    }
}
