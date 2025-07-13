package com.fatihbayhan.LibraryManagementSystem.endpoint;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fatihbayhan.LibraryManagementSystem.service.WriterService;
import com.fatihbayhan.librarymanagement.CreateWriterRequest;
import com.fatihbayhan.librarymanagement.DeleteWriterRequest;
import com.fatihbayhan.librarymanagement.DeleteWriterResponse;
import com.fatihbayhan.librarymanagement.GetAllWritersRequest;
import com.fatihbayhan.librarymanagement.GetAllWritersResponse;
import com.fatihbayhan.librarymanagement.GetWriterRequest;
import com.fatihbayhan.librarymanagement.SearchWritersRequest;
import com.fatihbayhan.librarymanagement.SearchWritersResponse;
import com.fatihbayhan.librarymanagement.UpdateWriterRequest;
import com.fatihbayhan.librarymanagement.WriterResponse;

import jakarta.xml.bind.JAXBElement;

@Endpoint
public class WriterEndpoint {
    private static final String NAMESPACE_URI = "http://fatihbayhan.com/librarymanagement";

    @Autowired
    private WriterService writerService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateWriterRequest")
    @ResponsePayload
    public JAXBElement<WriterResponse> createWriter(@RequestPayload JAXBElement<CreateWriterRequest> request) {
        WriterResponse response = writerService.createWriter(request.getValue());
        return createResponseJaxbElement(response, WriterResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetWriterRequest")
    @ResponsePayload
    public JAXBElement<WriterResponse> getWriter(@RequestPayload JAXBElement<GetWriterRequest> request) {
        WriterResponse response = writerService.getWriter(request.getValue());
        return createResponseJaxbElement(response, WriterResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllWritersRequest")
    @ResponsePayload
    public JAXBElement<GetAllWritersResponse> getAllWriters(@RequestPayload JAXBElement<GetAllWritersRequest> request) {
        GetAllWritersResponse response = writerService.getAllWriters(request.getValue());
        return createResponseJaxbElement(response, GetAllWritersResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateWriterRequest")
    @ResponsePayload
    public JAXBElement<WriterResponse> updateWriter(@RequestPayload JAXBElement<UpdateWriterRequest> request) {
        WriterResponse response = writerService.updateWriter(request.getValue());
        return createResponseJaxbElement(response, WriterResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteWriterRequest")
    @ResponsePayload
    public JAXBElement<DeleteWriterResponse> deleteWriter(@RequestPayload JAXBElement<DeleteWriterRequest> request) {
        DeleteWriterResponse response = writerService.deleteWriter(request.getValue());
        return createResponseJaxbElement(response, DeleteWriterResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchWritersRequest")
    @ResponsePayload
    public JAXBElement<SearchWritersResponse> searchWriters(@RequestPayload JAXBElement<SearchWritersRequest> request) {
        SearchWritersResponse response = writerService.searchWriters(request.getValue());
        return createResponseJaxbElement(response, SearchWritersResponse.class);
    }

    private <T> JAXBElement<T> createResponseJaxbElement(T object, Class<T> clazz) {
        return new JAXBElement<>(new QName(NAMESPACE_URI, clazz.getSimpleName()), clazz, object);
    }
}
