package com.eureka.kidsworld.domain.book.controller;

import com.eureka.kidsworld.domain.book.dto.BookContentDto;
import com.eureka.kidsworld.domain.book.entity.BookContent;
import com.eureka.kidsworld.domain.book.service.BookContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookContentController {
    private final BookContentService bookContentService;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody BookContentDto requestDto) {
        BookContent bookContent = requestDto.toEntity();
        BookContentDto bookDto = bookContentService.addBookContent(bookContent);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId,
                                                     @RequestBody BookContentDto requestDto) {
        BookContent updatedBook = requestDto.toEntity();
        BookContentDto bookDto = bookContentService.updateBookContent(bookId, updatedBook);
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookContentService.deleteBookContent(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam String title) {
        List<BookContentDto> books = bookContentService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> recommendBooks(@RequestParam Integer age) {
        List<BookContentDto> books = bookContentService.recommendBooksByAge(age);
        return ResponseEntity.ok(books);
    }
}
