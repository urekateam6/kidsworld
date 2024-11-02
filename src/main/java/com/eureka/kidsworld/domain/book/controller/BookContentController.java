package com.eureka.kidsworld.domain.book.controller;

import com.eureka.kidsworld.domain.book.dto.BookContentDto;
import com.eureka.kidsworld.domain.book.entity.BookContent;
import com.eureka.kidsworld.domain.book.service.BookContentService;
import com.eureka.kidsworld.global.exception.BookContentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookContentController {

    private final BookContentService bookContentService;

    // 도서 목록 페이지
    @GetMapping
    public String getBooks(Model model) {
        model.addAttribute("books", bookContentService.getAllBooks());
        return "book/list";
    }

    // 책 추가 폼 페이지
    @GetMapping("/new")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new BookContentDto());
        return "book/add";
    }

    // 책 추가
    @PostMapping
    public String addBook(@ModelAttribute("book") BookContent bookContent, RedirectAttributes redirectAttributes) {
        bookContentService.addBookContent(bookContent);
        redirectAttributes.addFlashAttribute("successMessage", "도서가 추가되었습니다.");
        return "redirect:/books"; // list.html 페이지로 리다이렉트
    }

    // 도서 수정 페이지로 이동
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        BookContentDto bookContentDto = bookContentService.findByBookContentId(id);
        model.addAttribute("book", bookContentDto);
        return "book/edit";
    }

    // 도서 수정 요청 처리 후 detail.html로 리다이렉트
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute BookContentDto bookContentDto, RedirectAttributes redirectAttributes) {
        bookContentService.updateBookContent(id, bookContentDto);
        redirectAttributes.addFlashAttribute("successMessage", "도서가 수정되었습니다.");
        return "redirect:/books/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookContentService.deleteBookContent(id);
        redirectAttributes.addFlashAttribute("successMessage", "도서가 삭제되었습니다.");
        return "redirect:/books";
    }
    // 책 상세 페이지
    @GetMapping("/{id}")
    public String getBookDetail(@PathVariable Long id, Model model) {
        BookContentDto bookDto = bookContentService.getBookContentById(id);
        model.addAttribute("book", bookDto);
        return "book/detail";
    }

    // 선택된 MBTI 요소에 맞춰 도서 추천
    @PostMapping("/recommend")
    public String recommendBooksByMbti(@RequestParam(value = "mbtiTraits", required = false) List<String> mbtiTraits, Model model) {
        List<BookContentDto> recommendedBooks = bookContentService.recommendBooksByMbti(mbtiTraits);
        model.addAttribute("recommendedBooks", recommendedBooks);
        return "book/recommend";
    }
}
