package com.fatihbayhan.LibraryManagementSystem.endpoint;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fatihbayhan.LibraryManagementSystem.service.BookService;
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

import jakarta.xml.bind.JAXBElement;

@Endpoint
public class BookEndpoint {
    private static final String NAMESPACE_URI = "http://fatihbayhan.com/librarymanagement";

    @Autowired
    private BookService bookService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateBookRequest")
    @ResponsePayload
    public JAXBElement<BookResponse> createBook(@RequestPayload JAXBElement<CreateBookRequest> request) {
        BookResponse response = bookService.createBook(request.getValue());
        return createResponseJaxbElement(response, BookResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetBookRequest")
    @ResponsePayload
    public JAXBElement<BookResponse> getBook(@RequestPayload JAXBElement<GetBookRequest> request) {
        BookResponse response = bookService.getBook(request.getValue());
        return createResponseJaxbElement(response, BookResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllBooksRequest")
    @ResponsePayload
    public JAXBElement<GetAllBooksResponse> getAllBooks(@RequestPayload JAXBElement<GetAllBooksRequest> request) {
        GetAllBooksResponse response = bookService.getAllBooks(request.getValue());
        return createResponseJaxbElement(response, GetAllBooksResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateBookRequest")
    @ResponsePayload
    public JAXBElement<BookResponse> updateBook(@RequestPayload JAXBElement<UpdateBookRequest> request) {
        BookResponse response = bookService.updateBook(request.getValue());
        return createResponseJaxbElement(response, BookResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteBookRequest")
    @ResponsePayload
    public JAXBElement<DeleteBookResponse> deleteBook(@RequestPayload JAXBElement<DeleteBookRequest> request) {
        DeleteBookResponse response = bookService.deleteBook(request.getValue());
        return createResponseJaxbElement(response, DeleteBookResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchBooksRequest")
    @ResponsePayload
    public JAXBElement<SearchBooksResponse> searchBooks(@RequestPayload JAXBElement<SearchBooksRequest> request) {
        SearchBooksResponse response = bookService.searchBooks(request.getValue());
        return createResponseJaxbElement(response, SearchBooksResponse.class);
    }

    private <T> JAXBElement<T> createResponseJaxbElement(T object, Class<T> clazz) {
        return new JAXBElement<>(new QName(NAMESPACE_URI, clazz.getSimpleName()), clazz, object);
    }
}
