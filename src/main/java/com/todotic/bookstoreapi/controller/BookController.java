package com.todotic.bookstoreapi.controller;

import com.todotic.bookstoreapi.model.entity.Book;
import com.todotic.bookstoreapi.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/books")
@RestController
public class BookController {

    private BookService bookService;

    @GetMapping("/last")
    public List<Book> getLast() {
        return bookService.findLast6Books();
    }

    @GetMapping
    public Page<Book> paginate(@PageableDefault(sort = "title", size = 5) Pageable pageable) {
        return bookService.paginate(pageable);
    }

    @GetMapping("/{slug}")
    public Book get(@PathVariable String slug) {
        return bookService.findBySlug(slug);
    }

}
