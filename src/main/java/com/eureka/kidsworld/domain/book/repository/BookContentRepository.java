package com.eureka.kidsworld.domain.book.repository;

import com.eureka.kidsworld.domain.book.entity.BookContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookContentRepository extends JpaRepository<BookContent, Long> {
    // 제목을 기준으로 책 검색
    List<BookContent> findByTitleContaining(String title);

    // 권장 연령 기준으로 책 검색
    List<BookContent> findByRecommendedAgeLessThanEqual(Integer age);

    // 특정 MBTI 요소를 포함하는 도서 검색
    @Query("SELECT b FROM BookContent b JOIN b.mbtiTraits m WHERE m = :mbtiTrait")
    List<BookContent> findByMbtiTrait(@Param("mbtiTrait") String mbtiTrait);
}
