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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class UserFeedbackController {
    private final UserFeedbackService userFeedbackService;
    private final UserService userService;
    private final BookContentService bookContentService;

    @PostMapping
    public ResponseEntity<UserFeedbackDto> addFeedback(@RequestBody UserFeedbackDto requestDto) {
        // todo: 커스텀 예외로 변경
        User user = userService.findByUserId(requestDto.getUserId()).orElseThrow(()->new RuntimeException("존재하지 않는 회원입니다."));
        BookContent book = bookContentService.findByBookContentId(requestDto.getBookId()).orElseThrow(()->new RuntimeException("존재하지 않는 도서압니다"));
        UserFeedbackDto feedbackDto = userFeedbackService.addFeedback(user, book, requestDto.getLiked());
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackDto);
    }

    @GetMapping
    public ResponseEntity<List<UserFeedbackDto>> getUserFeedback(@RequestParam Long userId, @RequestParam Long bookId) {
        User user = userService.findByUserId(userId).orElseThrow(()->new RuntimeException("존재하지 않는 회원입니다."));
        BookContent book = bookContentService.findByBookContentId(bookId).orElseThrow(()->new RuntimeException("존재하지 않는 도서압니다"));
        List<UserFeedbackDto> feedbacks = userFeedbackService.getUserFeedback(user, book);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/likes")
    public ResponseEntity<Long> getBookLikeCount(@RequestParam Long bookId) {
        BookContent book = bookContentService.findByBookContentId(bookId).orElseThrow(()->new RuntimeException("존재하지 않는 도서압니다"));
        Long likeCount = userFeedbackService.countLikesForBook(book);
        return ResponseEntity.ok(likeCount);
    }
}
