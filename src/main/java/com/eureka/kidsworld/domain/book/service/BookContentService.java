package com.eureka.kidsworld.domain.book.service;

import com.eureka.kidsworld.domain.book.dto.BookContentDto;
import com.eureka.kidsworld.domain.book.entity.BookContent;
import com.eureka.kidsworld.domain.book.repository.BookContentRepository;
import com.eureka.kidsworld.domain.user.entity.User;
import com.eureka.kidsworld.global.exception.BookContentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookContentService {
    private final BookContentRepository bookContentRepository;

    // 전체 도서 목록 가져오기
    @Transactional(readOnly = true)
    public List<BookContentDto> getAllBooks() {
        return bookContentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 도서 ID로 찾기
    @Transactional(readOnly = true)
    public Optional<BookContent> findByBookContentId(Long bookContentId) {
        return bookContentRepository.findById(bookContentId);
    }

    // 도서 추가
    public BookContentDto addBookContent(BookContent bookContent) {
        BookContent savedBook = bookContentRepository.save(bookContent);
        return convertToDto(savedBook);
    }

    public BookContentDto updateBookContent(Long bookId, BookContent updatedBookContent) {
        BookContent book = bookContentRepository.findById(bookId)
                .orElseThrow(() -> new BookContentNotFoundException("해당하는 도서가 없습니다."));

        book.update(updatedBookContent);

        return convertToDto(book);
    }

    public void deleteBookContent(Long bookId) {
        bookContentRepository.deleteById(bookId);
    }


    @Transactional(readOnly = true)
    public List<BookContentDto> searchBooksByTitle(String title) {
        return bookContentRepository.findByTitleContaining(title).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookContentDto> recommendBooksByAge(Integer age) {
        return bookContentRepository.findByRecommendedAgeLessThanEqual(age).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 선택된 MBTI 요소에 맞춰 추천 도서를 반환
     *
     * @param mbtiTraits 선택된 MBTI 요소 목록 (예: ["E", "N"])
     * @return 추천 도서 목록
     */
    // 선택된 MBTI 요소에 맞춰 추천 도서를 반환
    public List<BookContentDto> recommendBooksByMbti(List<String> mbtiTraits) {
        // 선택된 요소가 없는 경우 전체 목록 반환
        if (mbtiTraits == null || mbtiTraits.isEmpty()) {
            return getAllBooks();
        }

        // 모든 선택된 요소를 포함하는 도서만 필터링
        return bookContentRepository.findAll().stream()
                .filter(book -> book.getMbtiTraits().containsAll(mbtiTraits))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BookContentDto convertToDto(BookContent bookContent) {
        return new BookContentDto(
                bookContent.getBookId(),
                bookContent.getTitle(),
                bookContent.getSummary(),
                bookContent.getAuthor(),
                bookContent.getPublisher(),
                bookContent.getRecommendedAge(),
                bookContent.getMbtiTraits()
        );
    }

    @Transactional
    public BookContentDto getBookContentById(Long bookId) {
        BookContent book = bookContentRepository.findById(bookId)
                .orElseThrow(() -> new BookContentNotFoundException("해당하는 도서가 없습니다."));

        return convertToDto(book);

    }
}
