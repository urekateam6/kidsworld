package com.eureka.kidsworld.domain.book.controller;

import com.eureka.kidsworld.domain.book.dto.BookContentDto;
import com.eureka.kidsworld.domain.book.entity.BookContent;
import com.eureka.kidsworld.domain.book.service.BookContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookContentController {

    private final BookContentService bookContentService;

    // 책 목록 페이지
    @GetMapping
    public String getBooks(Model model) {
        List<BookContentDto> bookList = bookContentService.getAllBooks();
        model.addAttribute("books", bookList);
        return "book/list";
    }

    // 책 추가 폼 페이지
    @GetMapping("/new")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new BookContentDto());
        return "book/add"; // books/add.html 페이지를 렌더링
    }

    // 책 추가 처리
    @PostMapping
    public String addBook(@ModelAttribute("book") BookContentDto requestDto) {
        bookContentService.addBookContent(requestDto.toEntity());
        return "redirect:/book";
    }

    // 책 상세 페이지
    @GetMapping("/{id}")
    public String getBookDetail(@PathVariable Long id, Model model) {
        BookContentDto bookDto = bookContentService.getBookContentById(id);
        model.addAttribute("book", bookDto);
        return "book/detail"; // books/detail.html 페이지를 렌더링
    }
}
