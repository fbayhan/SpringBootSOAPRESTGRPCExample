package com.fatihbayhan.libraryrest.service;

import com.example.grpc.ClientStreamingServiceGrpc;
import com.example.grpc.ServerStreamingServiceGrpc;
import com.example.grpc.UnaryServiceGrpc;
import com.fatihbayhan.libraryrest.client.gen.BookBorrowingsResponse;
import com.fatihbayhan.libraryrest.config.GrpcClientConfigProperties;
import com.fatihbayhan.libraryrest.dto.BookDTO;
import com.fatihbayhan.libraryrest.dto.BookTransactionDTO;
import com.fatihbayhan.libraryrest.dto.UserDTO;
import com.fatihbayhan.libraryrest.dto.WriterDTO;
import com.library.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

@Service
public class GrpcClientService {
    private final LibraryServiceGrpc.LibraryServiceBlockingStub stub;

    public GrpcClientService(GrpcClientConfigProperties grpcClientConfigProperties) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcClientConfigProperties.getHost(), grpcClientConfigProperties.getPort())
                .usePlaintext()
                .build();

        stub = LibraryServiceGrpc.newBlockingStub(channel);
    }

    public List<BookDTO> borrowedBooks() {
        //  PeopleList peopleList= LibraryServiceGrpc.getWhoBookedMethod()


                /*
                      UnaryRequest request = UnaryRequest.newBuilder().setMessage(message).build();
        UnaryResponse response;
        try {
            response = unaryBlockingStub.unaryCall(request);
            logger.info("Unary response: {}", response.getMessage());
        } catch (Exception e) {
            logger.error("Failed to call unary service: {}", e.getMessage());
            return "Unary call failed";
        }
        return response.getMessage();
                 */
        return null;
    }

//    public List<UserDTO>  whoBorrowedBooksMost(int limit) {
//        BookId bookId = BookId.newBuilder().setBookId("1L").build();
//        PeopleList response = stub.whoBooked(bookId);
//        return response.getPeopleList().stream()
//                .map(person -> new UserDTO(1L, person.getName(), person.getEmail(), "Test","Test"))
//                .collect(Collectors.toList());
//    }

    public List<BookTransactionDTO> getCurrentlyBorrowedBooks() {
        BookTransactionList response = stub.getCurrentlyBorrowedBooks(Empty.getDefaultInstance());
        return response.getBookTransactionList().stream()
                .map(bookTransaction -> {

                    return mapbookTransactionToBookTransactionDTO(bookTransaction);
                })
                .collect(Collectors.toList());
    }

    public List<UserDTO> topReaders() {
        TopRequest topRequest = TopRequest.newBuilder().setLimit(10).build();

        PeopleList response = stub.getTopReaders(topRequest );
        return response.getPeopleList().stream()
                .map(person -> {
                    UserDTO user = mapPersonToUserDTO(person);
                    return user;
                })
                .collect(Collectors.toList());
    }

    public UserDTO mapPersonToUserDTO(People person) {
        UserDTO user = new UserDTO();
        user.setId(person.getId());
        user.setFullName(person.getFullName());
        user.setEmail(person.getEmail());
        user.setIdentityNumber(person.getIdentityNumber());
        user.setBorrowCount(person.getBorrowCount());
        user.setReadCount(person.getReadCount());
        return user;
    }

    public BookTransactionDTO mapbookTransactionToBookTransactionDTO(BookTransaction bookTransaction) {
        BookTransactionDTO dto = new BookTransactionDTO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        dto.setBookId(bookTransaction.getBookId());
        dto.setTransactionId(bookTransaction.getTransactionId());
        dto.setUserId(bookTransaction.getUserId());
        dto.setIsReturned(bookTransaction.getIsReturned());
        
        try {
            dto.setBorrowDate(sdf.parse(bookTransaction.getBorrowDate()));
            dto.setMustReturnDate(sdf.parse(bookTransaction.getMustReturnDate()));
        } catch (Exception e) {
            dto.setBorrowDate(null);
            dto.setMustReturnDate(null);
        }


        BookDTO book = new BookDTO();
        Book protoBook = bookTransaction.getBook();
        book.setId(protoBook.getId());
        book.setTitle(protoBook.getTitle());
        book.setIsbn(protoBook.getIsbn());
        book.setPublisher(protoBook.getPublisher());
        book.setLanguage(protoBook.getLanguage());
        book.setPages(protoBook.getPages());
        book.setWriterId(protoBook.getWriterId());


        UserDTO user = new UserDTO();
        People protoUser = bookTransaction.getUser();
        user.setId(protoUser.getId());
        user.setIdentityNumber(protoUser.getIdentityNumber());
        user.setFullName(protoUser.getFullName());
        user.setEmail(protoUser.getEmail());

        dto.setBook(book);
        dto.setUser(user);

        return dto;
    }
}
