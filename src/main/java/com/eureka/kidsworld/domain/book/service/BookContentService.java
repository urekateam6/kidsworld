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

    @Transactional(readOnly = true)
    public Optional<BookContent> findByBookContentId(Long bookContentId) {
        return bookContentRepository.findById(bookContentId);
    }

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
}
