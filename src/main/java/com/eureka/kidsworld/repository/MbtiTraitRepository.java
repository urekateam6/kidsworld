package com.eureka.kidsworld.repository;

import com.eureka.kidsworld.domain.entity.MbtiTrait;  // 엔티티 경로 수정
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiTraitRepository extends JpaRepository<MbtiTrait, Long> {
    MbtiTrait findByMbtiType(String mbtiType);
}
