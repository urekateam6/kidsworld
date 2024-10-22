package com.eureka.kidsworld.auth.service;

import com.eureka.kidsworld.domain.user.entity.User;
import com.eureka.kidsworld.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        // User 엔티티를 스프링 시큐리티가 이해할 수 있는 UserDetails로 변환
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),      // 사용자 이름
                user.getPassword(),      // 비밀번호
                user.getRole().getAuthorities() // 권한 정보 설정 (ROLE, 권한들)
        );
    }
}
