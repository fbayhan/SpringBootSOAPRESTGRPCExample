package com.fatihbayhan.LibraryManagementSystem.service;

import com.fatihbayhan.librarymanagement.BookResponse;
import com.fatihbayhan.librarymanagement.CreateBookRequest;
import com.fatihbayhan.librarymanagement.DeleteBookRequest;
import com.fatihbayhan.librarymanagement.DeleteBookResponse;
import com.fatihbayhan.librarymanagement.GetAllBooksRequest;
import com.fatihbayhan.librarymanagement.GetAllBooksResponse;
import com.fatihbayhan.librarymanagement.GetBookRequest;
import com.fatihbayhan.librarymanagement.SearchBooksRequest;
import com.fatihbayhan.librarymanagement.SearchBooksResponse;
import com.fatihbayhan.librarymanagement.UpdateBookRequest;

public interface BookService {
    BookResponse createBook(CreateBookRequest request);
    BookResponse getBook(GetBookRequest request);
    GetAllBooksResponse getAllBooks(GetAllBooksRequest request);
    BookResponse updateBook(UpdateBookRequest request);
    DeleteBookResponse deleteBook(DeleteBookRequest request);
    SearchBooksResponse searchBooks(SearchBooksRequest request);
}
