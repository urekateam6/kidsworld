package com.eureka.kidsworld.domain.user.service;

import com.eureka.kidsworld.domain.user.entity.Role;
import com.eureka.kidsworld.domain.user.entity.User;
import com.eureka.kidsworld.domain.user.repository.UserRepository;
import com.eureka.kidsworld.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository UserRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public Optional<User> findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public User saveUser(UserDto userDto) {
        // 일반 사용자에게 기본 권한 설정
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .role(Role.ROLE_USER)
                .build();
        log.info("Role to save: {}", user.getRole());

        return userRepository.save(user);
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    // 추가된 메소드
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    public void save(User user) {
        userRepository.save(user); // 사용자의 정보를 저장
    }

}
