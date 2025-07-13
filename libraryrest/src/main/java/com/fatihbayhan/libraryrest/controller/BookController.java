package com.fatihbayhan.libraryrest.controller;

import java.util.ArrayList;
import java.util.List;

import com.fatihbayhan.libraryrest.dto.UserDTO;
import com.fatihbayhan.libraryrest.service.GrpcClientService;
import com.fatihbayhan.libraryrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatihbayhan.libraryrest.dto.BookDTO;
import com.fatihbayhan.libraryrest.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private GrpcClientService grpcClientService;

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookRequest) {
        BookDTO createdBook = bookService.createBook(bookRequest);
        return ResponseEntity.ok(createdBook);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        BookDTO book = bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookRequest) {
        bookRequest.setId(id);
        BookDTO updatedBook = bookService.updateBook(bookRequest);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean success = bookService.deleteBook(id);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String searchTerm) {
        List<BookDTO> books = bookService.searchBooks(searchTerm);
        return ResponseEntity.ok(books);
    }


}
