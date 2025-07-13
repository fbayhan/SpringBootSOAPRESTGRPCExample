package com.library.grpc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.grpc.Book;
import com.library.grpc.BookId;
import com.library.grpc.BookTransaction;
import com.library.grpc.BookTransactionList;
import com.library.grpc.Empty;
import com.library.grpc.LibraryServiceGrpc;
import com.library.grpc.People;
import com.library.grpc.PeopleList;
import com.library.grpc.TopRequest;
import com.library.grpc.dto.BookDTO;
import com.library.grpc.dto.BookTransactionDTO;
import com.library.grpc.dto.UserDTO;
import com.library.grpc.model.BookBorrowing;
import com.library.grpc.repository.BookBorrowingRepository;

import io.grpc.stub.StreamObserver;


@Service
public class LibraryService extends LibraryServiceGrpc.LibraryServiceImplBase {

    private final BookBorrowingRepository bookBorrowingRepository;

    @Autowired
    public LibraryService(BookBorrowingRepository bookBorrowingRepository) {
        this.bookBorrowingRepository = bookBorrowingRepository;
    }

    @Override
    public void whoBooked(BookId request, StreamObserver<PeopleList> responseObserver) {
        People person1 = People.newBuilder()
                .setFullName("Alice")
                .setEmail("alice@example.com")
                .build();

        People person2 = People.newBuilder()
                .setFullName("Bob")
                .setEmail("bob@example.com")
                .build();

        PeopleList response = PeopleList.newBuilder()
                .addPeople(person1)
                .addPeople(person2)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void getCurrentlyBorrowedBooks(Empty request, StreamObserver<BookTransactionList> responseObserver) {
        List<BookBorrowing> bookBorrowings = bookBorrowingRepository.findByIsReturnedFalseOrderByMustReturnDateAsc();
        BookTransactionList.Builder responseBuilder = BookTransactionList.newBuilder();
        bookBorrowings.forEach(borrowing -> responseBuilder.addBookTransaction(mapToBookTransaction(borrowing)));
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getTopReaders(TopRequest request, StreamObserver<PeopleList> responseObserver) {
        List<Object[]> topReaders = bookBorrowingRepository.findTopReaders(request.getLimit());
        PeopleList.Builder responseBuilder = PeopleList.newBuilder();

        for (Object[] reader : topReaders) {
            Long userId = ((Number) reader[0]).longValue();
            String identity = reader[1].toString();
            String fullName = reader[2].toString();
            String email = reader[3].toString();

            int borrowCount = ((Number) reader[4]).intValue();
            int readCount = ((Number) reader[5]).intValue();

            People.Builder peopleBuilder = People.newBuilder()
                    .setId(userId)
                    .setIdentityNumber(identity)
                    .setFullName(fullName)
                    .setEmail(email)
                    .setBorrowCount(borrowCount)
                    .setReadCount(readCount);
                    ;

            responseBuilder.addPeople(peopleBuilder.build());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    private BookTransaction mapToBookTransaction(BookBorrowing borrowing) {
        Book.Builder bookBuilder = Book.newBuilder()
                .setId(borrowing.getBook().getId())
                .setTitle(borrowing.getBook().getTitle())
                .setIsbn(borrowing.getBook().getIsbn())
                .setPublisher(borrowing.getBook().getPublisher())
                .setLanguage(borrowing.getBook().getLanguage())
                .setPages(borrowing.getBook().getPages());

        People.Builder userBuilder = People.newBuilder()
                .setId(borrowing.getUser().getId())
                .setIdentityNumber(borrowing.getUser().getIdentityNumber())
                .setFullName(borrowing.getUser().getFullName())
                .setEmail(borrowing.getUser().getEmail());

        return BookTransaction.newBuilder()
                .setBookId(borrowing.getBook().getId())
                .setTransactionId(borrowing.getId())
                .setUserId(borrowing.getUser().getId())
                .setBorrowDate(borrowing.getBorrowDate().toString())
                .setMustReturnDate(borrowing.getMustReturnDate().toString())
                .setIsReturned(borrowing.getIsReturned())
                .setBook(bookBuilder)
                .setUser(userBuilder)
                .build();
    }

    public BookTransactionDTO mapBookBorrowingsToBookTransactionDTOs2(BookBorrowing bookBorrowing) {
        BookTransactionDTO bookTransactionDTO = new BookTransactionDTO();

        // Temel bilgileri ayarla
        bookTransactionDTO.setBookId(bookBorrowing.getBook().getId());
        bookTransactionDTO.setTransactionId(bookBorrowing.getId());
        bookTransactionDTO.setUserId(bookBorrowing.getUser().getId());
        bookTransactionDTO.setBorrowDate(bookBorrowing.getBorrowDate());
        bookTransactionDTO.setMustReturnDate(bookBorrowing.getMustReturnDate());
        bookTransactionDTO.setIsReturned(bookBorrowing.getIsReturned());

        // Book bilgilerini ayarla
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookBorrowing.getBook().getId());
        bookDTO.setTitle(bookBorrowing.getBook().getTitle());
        bookDTO.setIsbn(bookBorrowing.getBook().getIsbn());
        bookDTO.setPublisher(bookBorrowing.getBook().getPublisher());
        bookDTO.setLanguage(bookBorrowing.getBook().getLanguage());
        bookDTO.setPages(bookBorrowing.getBook().getPages());
        bookTransactionDTO.setBook(bookDTO);

        // User bilgilerini ayarla
        UserDTO userDTO = new UserDTO();
        userDTO.setId(bookBorrowing.getUser().getId());
        userDTO.setIdentityNumber(bookBorrowing.getUser().getIdentityNumber());
        userDTO.setFullName(bookBorrowing.getUser().getFullName());
        userDTO.setEmail(bookBorrowing.getUser().getEmail());
        bookTransactionDTO.setUser(userDTO);

        return bookTransactionDTO;
    }
}
