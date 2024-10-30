package com.eureka.kidsworld.domain.mbti.repository;

import com.eureka.kidsworld.domain.mbti.entity.MbtiResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiResultRepository extends JpaRepository<MbtiResult, Long> {
    // 추가적인 메서드가 필요하면 정의 가능
}
