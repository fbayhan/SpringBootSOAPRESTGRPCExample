package com.fatihbayhan.LibraryManagementSystem.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatihbayhan.LibraryManagementSystem.model.Book;
import com.fatihbayhan.LibraryManagementSystem.model.BookBorrowing;
import com.fatihbayhan.LibraryManagementSystem.model.User;
import com.fatihbayhan.LibraryManagementSystem.model.Writer;
import com.fatihbayhan.LibraryManagementSystem.repository.BookBorrowingRepository;
import com.fatihbayhan.LibraryManagementSystem.repository.BookRepository;
import com.fatihbayhan.LibraryManagementSystem.repository.UserRepository;
import com.fatihbayhan.LibraryManagementSystem.service.BookBorrowingService;
import com.fatihbayhan.librarymanagement.BookBorrowingResponse;
import com.fatihbayhan.librarymanagement.BookBorrowingsResponse;
import com.fatihbayhan.librarymanagement.BookResponse;
import com.fatihbayhan.librarymanagement.BorrowBookRequest;
import com.fatihbayhan.librarymanagement.CategoryResponse;
import com.fatihbayhan.librarymanagement.GetAllBookBorrowingsRequest;
import com.fatihbayhan.librarymanagement.GetAllBookBorrowingsResponse;
import com.fatihbayhan.librarymanagement.ReturnBookRequest;
import com.fatihbayhan.librarymanagement.ReturnBookResponse;
import com.fatihbayhan.librarymanagement.UserResponse;
import com.fatihbayhan.librarymanagement.WriterResponse;

@Service
public class BookBorrowingServiceImpl implements BookBorrowingService {

    @Autowired
    private BookBorrowingRepository bookBorrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BookBorrowingResponse borrowBook(BorrowBookRequest request) {
        Book book = bookRepository.findById(Long.parseLong(request.getBookId()))
                .orElseThrow(() -> new RuntimeException("Book not found"));

        User user = userRepository.findById(Long.parseLong(request.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        BookBorrowing bookBorrowing = new BookBorrowing();
        bookBorrowing.setBook(book);
        bookBorrowing.setUser(user);
        bookBorrowing.setBorrowDate(new Date());
        bookBorrowing.setMustReturnDate(Date.from(LocalDate.now().plusDays(15).atStartOfDay().toInstant(java.time.ZoneOffset.UTC)));
        bookBorrowing.setIsReturned(false);

        BookBorrowing savedBorrowing = bookBorrowingRepository.save(bookBorrowing);

        BookBorrowingResponse response = new BookBorrowingResponse();
        response.setBookBorrowingId(savedBorrowing.getId().toString());
        response.setBookId(book.getId().toString());
        response.setUserId(user.getId().toString());

        try {
            XMLGregorianCalendar borrowDate = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(savedBorrowing.getBorrowDate().toInstant().toString());
            XMLGregorianCalendar mustReturnDate = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(savedBorrowing.getMustReturnDate().toInstant().toString());

            response.setBorrowDate(borrowDate);
            response.setMustReturnDate(mustReturnDate);
        } catch (Exception e) {
            throw new RuntimeException("Error converting dates", e);
        }

        response.setIsReturned(savedBorrowing.getIsReturned());
        return response;
    }

    @Override
    public ReturnBookResponse returnBook(ReturnBookRequest request) {
        BookBorrowing bookBorrowing = bookBorrowingRepository.findById(Long.parseLong(request.getBookBorrowingId()))
                .orElseThrow(() -> new RuntimeException("Book borrowing not found"));

        bookBorrowing.setReturnDate(new Date());
        bookBorrowing.setIsReturned(true);

        BookBorrowing savedBorrowing = bookBorrowingRepository.save(bookBorrowing);

        ReturnBookResponse response = new ReturnBookResponse();
        response.setBookBorrowingId(savedBorrowing.getId().toString());

        try {
            XMLGregorianCalendar returnDate = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(savedBorrowing.getReturnDate().toInstant().toString());
            response.setReturnDate(returnDate);
        } catch (Exception e) {
            throw new RuntimeException("Error converting date", e);
        }

        response.setIsReturned(savedBorrowing.getIsReturned());
        return response;
    }

    @Override
    public GetAllBookBorrowingsResponse getAllBookBorrowings(GetAllBookBorrowingsRequest request) {
        List<BookBorrowing> bookBorrowings = bookBorrowingRepository.findAll();
        GetAllBookBorrowingsResponse response = new GetAllBookBorrowingsResponse();

        for (BookBorrowing borrowing : bookBorrowings) {
            BookBorrowingsResponse bookBorrowingResponse = new BookBorrowingsResponse();
            bookBorrowingResponse.setBookBorrowingId(borrowing.getId().toString());

            Book book = borrowing.getBook();
            if (book != null) {
                BookResponse bookResponse = new BookResponse();
                bookResponse.setBookId(book.getId().toString());
                bookResponse.setTitle(book.getTitle());
                bookResponse.setIsbn(book.getIsbn());
                bookResponse.setPublisher(book.getPublisher());
                bookResponse.setLanguage(book.getLanguage());
                bookResponse.setPages(book.getPages());

                Writer writer = book.getWriter();
                if (writer != null) {
                    WriterResponse writerResponse = new WriterResponse();
                    writerResponse.setWriterId(writer.getId().toString());
                    writerResponse.setFullName(writer.getFullName());
                    try {
                        if (writer.getBirthDate() != null) {
                           writerResponse.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(writer.getBirthDate().toInstant().toString()));
                        }
                         if (writer.getDeathDate() != null) {
                           writerResponse.setDeathDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(writer.getDeathDate().toInstant().toString()));
                        }
                    } catch (Exception e) {
                        System.err.println("Error converting writer dates: " + e.getMessage());
                    }

                    writerResponse.setNationality(writer.getNationality());
                    writerResponse.setBiography(writer.getBiography());
                    bookResponse.setWriter(writerResponse);
                }

                if (book.getCategories() != null) {
                    book.getCategories().forEach(category -> {
                        CategoryResponse categoryResponse = new CategoryResponse();
                        categoryResponse.setCategoryId(category.getId().toString());
                        categoryResponse.setCategoryName(category.getCategoryName());
                        bookResponse.getCategories().add(categoryResponse);
                    });
                }

                bookBorrowingResponse.setBook(bookResponse);
            }

            User user = borrowing.getUser();
            if (user != null) {
                UserResponse userResponse = new UserResponse();
                userResponse.setUserId(user.getId().toString());
                userResponse.setIdentityNumber(user.getIdentityNumber());
                userResponse.setFullName(user.getFullName());
                userResponse.setEmail(user.getEmail());
                bookBorrowingResponse.setUser(userResponse);
            }

            try {
                 if (borrowing.getBorrowDate() != null) {
                    XMLGregorianCalendar borrowDate = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(borrowing.getBorrowDate().toInstant().toString());
                    bookBorrowingResponse.setBorrowDate(borrowDate);
                 }
                 if (borrowing.getMustReturnDate() != null) {
                    XMLGregorianCalendar mustReturnDate = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(borrowing.getMustReturnDate().toInstant().toString());
                    bookBorrowingResponse.setMustReturnDate(mustReturnDate);
                 }
            } catch (Exception e) {
                throw new RuntimeException("Error converting borrowing/return dates", e);
            }

            bookBorrowingResponse.setIsReturned(borrowing.getIsReturned());
            response.getBookBorrowingResponse().add(bookBorrowingResponse);
        }

        return response;
    }
}
