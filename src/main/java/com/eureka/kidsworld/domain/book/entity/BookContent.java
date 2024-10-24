package com.eureka.kidsworld.domain.book.entity;

import com.eureka.kidsworld.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookContent extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;

    @Column(length = 1000)
    private String summary;

    private String author;

    private String publisher;

    private Integer recommendedAge;

    @ElementCollection
    @CollectionTable(name = "book_mbti_traits", joinColumns = @JoinColumn(name = "book_id"))
    @MapKeyColumn(name = "mbti_trait")
    @Column(name = "trait_value")
    private Map<String, Float> mbtiTraits;

    @Builder
    public BookContent(Long bookId, String title, String summary, String author, String publisher, Integer recommendedAge, Map<String, Float> mbtiTraits) {
        this.bookId = bookId;
        this.title = title;
        this.summary = summary;
        this.author = author;
        this.publisher = publisher;
        this.recommendedAge = recommendedAge;
        this.mbtiTraits = mbtiTraits;
    }

    public void update(BookContent updatedBookContent) {
        this.title = updatedBookContent.getTitle();
        this.summary = updatedBookContent.getSummary();
        this.author = updatedBookContent.getAuthor();
        this.publisher = updatedBookContent.getPublisher();
        this.recommendedAge = updatedBookContent.getRecommendedAge();
        this.mbtiTraits = updatedBookContent.getMbtiTraits();
    }
}
