package org.example.mbti.repository;

import org.example.mbti.domain.entity.MbtiQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiQuestionRepository extends JpaRepository<MbtiQuestion, Long> {

}
