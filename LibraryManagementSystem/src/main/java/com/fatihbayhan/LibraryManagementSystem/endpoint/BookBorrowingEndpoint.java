package com.fatihbayhan.LibraryManagementSystem.endpoint;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fatihbayhan.LibraryManagementSystem.service.BookBorrowingService;
import com.fatihbayhan.librarymanagement.BookBorrowingResponse;
import com.fatihbayhan.librarymanagement.BorrowBookRequest;
import com.fatihbayhan.librarymanagement.ReturnBookRequest;
import com.fatihbayhan.librarymanagement.ReturnBookResponse;
import com.fatihbayhan.librarymanagement.GetAllBookBorrowingsRequest;
import com.fatihbayhan.librarymanagement.GetAllBookBorrowingsResponse;

import jakarta.xml.bind.JAXBElement;

@Endpoint
public class BookBorrowingEndpoint {
    private static final String NAMESPACE_URI = "http://fatihbayhan.com/librarymanagement";

    @Autowired
    private BookBorrowingService bookBorrowingService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "BorrowBookRequest")
    @ResponsePayload
    public JAXBElement<BookBorrowingResponse> borrowBook(@RequestPayload JAXBElement<BorrowBookRequest> request) {
        BookBorrowingResponse response = bookBorrowingService.borrowBook(request.getValue());
        return createResponseJaxbElement(response, BookBorrowingResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ReturnBookRequest")
    @ResponsePayload
    public JAXBElement<ReturnBookResponse> returnBook(@RequestPayload JAXBElement<ReturnBookRequest> request) {
        ReturnBookResponse response = bookBorrowingService.returnBook(request.getValue());
        return createResponseJaxbElement(response, ReturnBookResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllBookBorrowingsRequest")
    @ResponsePayload
    public JAXBElement<GetAllBookBorrowingsResponse> getAllBookBorrowings(@RequestPayload JAXBElement<GetAllBookBorrowingsRequest> request) {
        GetAllBookBorrowingsResponse response = bookBorrowingService.getAllBookBorrowings(request.getValue());
        return createResponseJaxbElement(response, GetAllBookBorrowingsResponse.class);
    }

    private <T> JAXBElement<T> createResponseJaxbElement(T object, Class<T> clazz) {
        return new JAXBElement<>(new QName(NAMESPACE_URI, clazz.getSimpleName()), clazz, object);
    }
}
