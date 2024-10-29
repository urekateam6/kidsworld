package com.eureka.kidsworld.domain.book.service;

import com.eureka.kidsworld.domain.book.dto.UserFeedbackDto;
import com.eureka.kidsworld.domain.book.entity.BookContent;
import com.eureka.kidsworld.domain.book.entity.UserFeedback;
import com.eureka.kidsworld.domain.book.repository.BookContentRepository;
import com.eureka.kidsworld.domain.book.repository.UserFeedbackRepository;
import com.eureka.kidsworld.domain.user.entity.User;
import com.eureka.kidsworld.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFeedbackService {
    private final UserRepository userRepository;
    private final BookContentRepository bookContentRepository;
    private final UserFeedbackRepository userFeedbackRepository;

    public UserFeedbackDto addFeedback(UserFeedbackDto userFeedbackDto) {
        User user=userRepository.findByUserId(userFeedbackDto.getUserId()).orElseThrow(()->new RuntimeException("존재하지 않는 회원입니다."));
        BookContent bookContent=bookContentRepository.findById(userFeedbackDto.getFeedbackId()).orElseThrow(()->new RuntimeException("존재하지 않는 도서입니다."));
        UserFeedback feedback=UserFeedback.builder()
                .user(user)
                .bookContent(bookContent)
                .liked(userFeedbackDto.getLiked())
                .build();
        return convertToDto(userFeedbackRepository.save(feedback));
    }

    public List<UserFeedbackDto> getUserFeedback(User user, BookContent bookContent) {
        return userFeedbackRepository.findByUserAndBookContent(user, bookContent).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Long countLikesForBook(BookContent bookContent) {
        return userFeedbackRepository.countByBookContentAndLiked(bookContent, true);
    }

    private UserFeedbackDto convertToDto(UserFeedback feedback) {
        return new UserFeedbackDto(
                feedback.getFeedbackId(),
                feedback.getUser().getUserId(),
                feedback.getBookContent().getBookId(),
                feedback.getLiked()
        );
    }

    @Transactional(readOnly = true)
    public List<UserFeedbackDto> getAllFeedbacks() {
        return userFeedbackRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserFeedbackDto getFeedbackById(Long id) {
        return convertToDto(userFeedbackRepository.findById(id).orElseThrow(()->new RuntimeException("존재하지 않는 피드백")));
    }
}
