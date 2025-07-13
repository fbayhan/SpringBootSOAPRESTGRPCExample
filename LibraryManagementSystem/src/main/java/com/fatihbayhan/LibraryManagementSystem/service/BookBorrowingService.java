package com.fatihbayhan.LibraryManagementSystem.service;

import com.fatihbayhan.librarymanagement.BookBorrowingResponse;
import com.fatihbayhan.librarymanagement.BorrowBookRequest;
import com.fatihbayhan.librarymanagement.ReturnBookRequest;
import com.fatihbayhan.librarymanagement.ReturnBookResponse;
import com.fatihbayhan.librarymanagement.GetAllBookBorrowingsRequest;
import com.fatihbayhan.librarymanagement.GetAllBookBorrowingsResponse;

public interface BookBorrowingService {
    BookBorrowingResponse borrowBook(BorrowBookRequest request);
    ReturnBookResponse returnBook(ReturnBookRequest request);
    GetAllBookBorrowingsResponse getAllBookBorrowings(GetAllBookBorrowingsRequest request);
}
