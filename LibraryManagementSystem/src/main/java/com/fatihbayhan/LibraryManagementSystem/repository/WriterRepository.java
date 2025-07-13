package com.fatihbayhan.LibraryManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fatihbayhan.LibraryManagementSystem.model.Writer;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    Page<Writer> findByFullNameContaining(String searchTerm, Pageable pageable);
}
