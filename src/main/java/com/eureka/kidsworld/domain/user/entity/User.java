package com.eureka.kidsworld.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String username;
    private String password;
    private String nickname;
    private String childMbti;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void setChildMbti(String childMbti) {
        this.childMbti = childMbti;
    }
}
