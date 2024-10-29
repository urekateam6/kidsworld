package org.example.mbti.repository;

import org.example.mbti.domain.entity.MbtiTrait;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiTraitRepository extends JpaRepository<MbtiTrait, Long> {
    MbtiTrait findByMbtiType(String mbtiType); // 이 부분은 변경할 필요 없음
}
