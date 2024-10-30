package com.eureka.kidsworld.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "mbti_trait")
public class MbtiTrait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long traitId;

    @Column(nullable = false)
    private String mbtiType;

    @Column(nullable = false)
    private String strength;

    @Column(nullable = false)
    private String weakness;

    @Column(nullable = false)
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @Builder
    public MbtiTrait(String mbtiType, String strength, String weakness, String description, String imagePath) {
        this.mbtiType = mbtiType;
        this.strength = strength;
        this.weakness = weakness;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
