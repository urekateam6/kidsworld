package com.eureka.kidsworld.domain.book.repository;


import com.eureka.kidsworld.domain.book.entity.Recommendation;
import com.eureka.kidsworld.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    // 특정 사용자의 추천 목록 조회
    List<Recommendation> findByUser(User user);
}
