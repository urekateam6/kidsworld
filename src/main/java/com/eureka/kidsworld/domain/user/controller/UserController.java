package com.eureka.kidsworld.domain.user.controller;

import com.eureka.kidsworld.domain.user.service.UserService;
import com.eureka.kidsworld.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());  // 회원가입 폼에 전달할 빈 DTO 객체
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserDto userDto, Model model) {
        log.info("start register");
        try {
            userService.saveUser(userDto);  // UserDto를 User로 변환하여 저장
            log.info("register success");
            return "home";  // 회원가입 성공 후 로그인 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during registration");
            return "register";  // 오류 발생 시 다시 회원가입 페이지로
        }
    }
}
