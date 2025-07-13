package com.library.grpc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.grpc.model.BookBorrowing;

@Repository
public interface BookBorrowingRepository extends JpaRepository<BookBorrowing, Long> {
    List<BookBorrowing> findByIsReturnedFalseOrderByMustReturnDateAsc();

    @Query(value = """
    SELECT u.id, u.identity_number, u.full_name,  u.email,
           COUNT(b.id) AS totalBorrowCount,
           SUM(CASE WHEN b.is_returned = true THEN 1 ELSE 0 END) AS readCount
    FROM tbl_book_borrowing b
    JOIN users u ON b.user_id = u.id
    GROUP BY u.id, u.identity_number, u.full_name
    ORDER BY readCount DESC
    LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findTopReaders(@Param("limit") int limit);
}
