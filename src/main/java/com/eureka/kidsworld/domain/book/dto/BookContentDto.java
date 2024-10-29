package com.eureka.kidsworld.domain.book.dto;

import com.eureka.kidsworld.domain.book.entity.BookContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookContentDto {
    private Long bookId;
    private String title;
    private String summary;
    private String author;
    private String publisher;
    private Integer recommendedAge;
    private Set<String> mbtiTraits;

    public BookContent toEntity() {
        return BookContent.builder()
                .bookId(bookId)
                .title(title)
                .summary(summary)
                .author(author)
                .publisher(publisher)
                .recommendedAge(recommendedAge)
                .mbtiTraits(mbtiTraits)
                .build();
    }

    // MBTI Traits를 정해진 순서로 문자열로 반환
    public String getMbtiTraitsAsString() {
        StringBuilder mbtiString = new StringBuilder();

        if (mbtiTraits.contains("I")) {
            mbtiString.append("I");
        } else if (mbtiTraits.contains("E")) {
            mbtiString.append("E");
        }

        if (mbtiTraits.contains("N")) {
            mbtiString.append("N");
        } else if (mbtiTraits.contains("S")) {
            mbtiString.append("S");
        }

        if (mbtiTraits.contains("F")) {
            mbtiString.append("F");
        } else if (mbtiTraits.contains("T")) {
            mbtiString.append("T");
        }

        if (mbtiTraits.contains("P")) {
            mbtiString.append("P");
        } else if (mbtiTraits.contains("J")) {
            mbtiString.append("J");
        }

        return mbtiString.toString();
    }
}
