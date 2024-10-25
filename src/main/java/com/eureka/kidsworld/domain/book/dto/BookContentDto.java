package com.eureka.kidsworld.domain.book.dto;

import com.eureka.kidsworld.domain.book.entity.BookContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

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
    private Map<String, Float> mbtiTraits;

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
}
