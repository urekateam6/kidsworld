package com.eureka.kidsworld.domain.mbti.controller;

import com.eureka.kidsworld.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    // 메인 페이지로 이동
    @GetMapping
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        String nickname = userService.findNicknameByUsername(username);

        model.addAttribute("nickname", nickname);
        return "main"; // main.html로 이동
    }

    // MBTI 검사 페이지로 리디렉션
    @GetMapping("/mbti")
    public String mbtiPage() {
        return "redirect:/mbti/question/1"; // MBTI 질문의 첫 번째로 리디렉션
    }

    @GetMapping("/book") // /main/book으로 매핑
    public String bookListPage() {
        return "redirect:/books"; // /books로 리디렉션
    }
}
