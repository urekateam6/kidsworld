package com.eureka.kidsworld.domain.mbti.repository;

import com.eureka.kidsworld.domain.mbti.entity.MbtiAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MbtiAnswerRepository extends JpaRepository<MbtiAnswer, Long> {

    // 특정 사용자 ID로 MBTI 답변 조회 (논리 삭제 포함)
    @Query("SELECT a FROM MbtiAnswer a WHERE a.userId = :userId")
    List<MbtiAnswer> findByUserId(@Param("userId") Long userId);

    // 논리 삭제되지 않은 답변만 조회 (deletedAt이 null인 경우)
    List<MbtiAnswer> findByUserIdAndDeletedAtIsNull(Long userId);
    // deleted_at이 한 달 이상 지난 데이터 삭제
    void deleteByDeletedAtBefore(LocalDateTime dateTime);
}
