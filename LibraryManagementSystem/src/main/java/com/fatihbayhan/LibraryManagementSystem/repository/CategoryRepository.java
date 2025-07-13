package com.fatihbayhan.LibraryManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatihbayhan.LibraryManagementSystem.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Category> searchCategories(@Param("searchTerm") String searchTerm, Pageable pageable);
}
