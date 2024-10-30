package com.eureka.kidsworld.domain.mbti.repository;

import com.eureka.kidsworld.domain.mbti.entity.MbtiTrait;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiTraitRepository extends JpaRepository<MbtiTrait, Long> {
    MbtiTrait findByMbtiType(String mbtiType);
}
