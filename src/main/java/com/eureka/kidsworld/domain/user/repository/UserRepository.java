package com.eureka.kidsworld.domain.user.repository;

import com.eureka.kidsworld.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);
}
