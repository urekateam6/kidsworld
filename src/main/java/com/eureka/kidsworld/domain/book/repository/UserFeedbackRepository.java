package com.eureka.kidsworld.domain.book.repository;

import com.eureka.kidsworld.domain.book.entity.BookContent;
import com.eureka.kidsworld.domain.book.entity.UserFeedback;
import com.eureka.kidsworld.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long> {
    // 특정 사용자와 도서에 대한 피드백 조회
    List<UserFeedback> findByUserAndBook(User user, BookContent book);

    // 특정 도서에 대한 좋아요 개수 조회
    Long countByBookAndIsLike(BookContent book, Boolean isLike);
}
