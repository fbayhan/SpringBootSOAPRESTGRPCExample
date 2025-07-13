package com.fatihbayhan.libraryrest.controller;

import java.util.List;

import com.fatihbayhan.libraryrest.dto.UserDTO;
import com.fatihbayhan.libraryrest.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatihbayhan.libraryrest.dto.BookTransactionDTO;
import com.fatihbayhan.libraryrest.service.BookTransactionService;

@RestController
@RequestMapping("/api/transactions")
public class BookTransactionController {

    @Autowired
    private BookTransactionService bookTransactionService;

    @Autowired
    GrpcClientService grpcClientService;

    @GetMapping
    public ResponseEntity<List<BookTransactionDTO>> getAllBookBorrowings() {
        List<BookTransactionDTO> transactions = bookTransactionService.getAllBookBorrowings();
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long id) {
        bookTransactionService.returnBook(id);

        return ResponseEntity.ok().build();

    }


    @PostMapping("/borrow/{bookId}/{userId}")
    public ResponseEntity<Boolean> borrowBook(@PathVariable Long bookId, @PathVariable Long userId) {
        bookTransactionService.borrowBook(bookId, userId);

        return ResponseEntity.ok().build();
    }


    public List<UserDTO> mostBookReaderUsers() {

        return null;
    }


    @GetMapping("/currentlyborrowed")
    public List<BookTransactionDTO> getCurrentlyBorrowedBooks() {
        List<BookTransactionDTO> getCurrentlyBorrowedBooks=grpcClientService.getCurrentlyBorrowedBooks();
        return getCurrentlyBorrowedBooks;
    }


    @GetMapping("/topreaders")
    public List<UserDTO> topReaders() {
        List<UserDTO> topReaders=grpcClientService.topReaders();
        return topReaders;
    }

}
