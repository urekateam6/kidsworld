package com.eureka.kidsworld.domain.book.repository;

import com.eureka.kidsworld.domain.book.entity.BookContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookContentRepository extends JpaRepository<BookContent, Long> {
    // 제목을 기준으로 책 검색
    List<BookContent> findByTitleContaining(String title);

    // 권장 연령 기준으로 책 검색
    List<BookContent> findByRecommendedAgeLessThanEqual(Integer age);
}
