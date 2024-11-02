package com.eureka.kidsworld.domain.user.service;

import com.eureka.kidsworld.domain.user.entity.Role;
import com.eureka.kidsworld.domain.user.entity.User;
import com.eureka.kidsworld.domain.user.repository.UserRepository;
import com.eureka.kidsworld.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // 수정: UserRepository 중복 제거
    private final PasswordEncoder passwordEncoder;

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
        return userRepository.save(user);
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public Long findUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getUserId)
                .orElse(null);
    }

    public void updateChildMbti(Long userId, String mbtiResult) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setChildMbti(mbtiResult); // user 엔티티에 setChildMbti 메서드가 필요함
            userRepository.save(user);
        });
    }

    public void clearChildMbti(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setChildMbti(null); // child_mbti를 null로 설정
            userRepository.save(user); // 변경사항 저장
        });

    }
    public String findNicknameByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getNickname)
                .orElse("사용자");
    }

}
