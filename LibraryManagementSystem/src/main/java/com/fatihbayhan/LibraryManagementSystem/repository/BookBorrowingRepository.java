package com.fatihbayhan.LibraryManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatihbayhan.LibraryManagementSystem.model.BookBorrowing;

public interface BookBorrowingRepository extends JpaRepository<BookBorrowing, Long> {
    List<BookBorrowing> findAllByOrderByBorrowDateDesc();
}
