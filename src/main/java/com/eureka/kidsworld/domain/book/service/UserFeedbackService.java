package com.eureka.kidsworld.domain.book.service;

import com.eureka.kidsworld.domain.book.dto.UserFeedbackDto;
import com.eureka.kidsworld.domain.book.entity.BookContent;
import com.eureka.kidsworld.domain.book.entity.UserFeedback;
import com.eureka.kidsworld.domain.book.repository.UserFeedbackRepository;
import com.eureka.kidsworld.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFeedbackService {
    private final UserFeedbackRepository userFeedbackRepository;

    public UserFeedbackDto addFeedback(User user, BookContent book, Boolean liked) {
        UserFeedback feedback=UserFeedback.builder()
                .user(user)
                .book(book)
                .liked(liked)
                .build();
        return convertToDto(userFeedbackRepository.save(feedback));
    }

    public List<UserFeedbackDto> getUserFeedback(User user, BookContent book) {
        return userFeedbackRepository.findByUserAndBook(user, book).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Long countLikesForBook(BookContent book) {
        return userFeedbackRepository.countByBookAndLiked(book, true);
    }

    private UserFeedbackDto convertToDto(UserFeedback feedback) {
        return new UserFeedbackDto(
                feedback.getFeedbackId(),
                feedback.getUser().getUserId(),
                feedback.getBook().getBookId(),
                feedback.getLiked()
        );
    }
}
