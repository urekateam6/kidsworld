package com.eureka.kidsworld.repository;

import com.eureka.kidsworld.domain.entity.MbtiQuestion;  // 엔티티 경로 수정
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiQuestionRepository extends JpaRepository<MbtiQuestion, Long> {

}
