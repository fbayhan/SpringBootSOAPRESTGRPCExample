package com.fatihbayhan.LibraryManagementSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fatihbayhan.LibraryManagementSystem.model.Writer;
import com.fatihbayhan.LibraryManagementSystem.repository.WriterRepository;
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

@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    private WriterRepository writerRepository;

    @Override
    public WriterResponse createWriter(CreateWriterRequest request) {
        Writer writer = new Writer();
        writer.setFullName(request.getFullName());
        if (request.getBirthDate() != null) {
            writer.setBirthDate(request.getBirthDate().toGregorianCalendar().getTime());
        }
        if (request.getDeathDate() != null) {
            writer.setDeathDate(request.getDeathDate().toGregorianCalendar().getTime());
        }
        writer.setNationality(request.getNationality());
        writer.setBiography(request.getBiography());

        Writer savedWriter = writerRepository.save(writer);
        return convertToWriterResponse(savedWriter);
    }

    @Override
    public WriterResponse getWriter(GetWriterRequest request) {
        Writer writer = writerRepository.findById(Long.parseLong(request.getWriterId()))
                .orElseThrow(() -> new RuntimeException("Writer not found"));
        return convertToWriterResponse(writer);
    }

    @Override
    public GetAllWritersResponse getAllWriters(GetAllWritersRequest request) {
        List<Writer> writers = writerRepository.findAll();
        GetAllWritersResponse response = new GetAllWritersResponse();
        response.getWriters().addAll(writers.stream()
                .map(this::convertToWriterResponse)
                .collect(Collectors.toList()));
        return response;
    }

    @Override
    public WriterResponse updateWriter(UpdateWriterRequest request) {
        Writer writer = writerRepository.findById(Long.parseLong(request.getWriterId()))
                .orElseThrow(() -> new RuntimeException("Writer not found"));

        writer.setFullName(request.getFullName());
        writer.setBirthDate(request.getBirthDate().toGregorianCalendar().getTime());
        if (request.getDeathDate() != null) {
            writer.setDeathDate(request.getDeathDate().toGregorianCalendar().getTime());
        }
        writer.setNationality(request.getNationality());
        writer.setBiography(request.getBiography());

        Writer updatedWriter = writerRepository.save(writer);
        return convertToWriterResponse(updatedWriter);
    }

    @Override
    public DeleteWriterResponse deleteWriter(DeleteWriterRequest request) {
        writerRepository.deleteById(Long.parseLong(request.getWriterId()));
        DeleteWriterResponse response = new DeleteWriterResponse();
        response.setSuccess(true);
        return response;
    }

    @Override
    public SearchWritersResponse searchWriters(SearchWritersRequest request) {
        Page<Writer> writerPage = writerRepository.findByFullNameContaining(
                request.getSearchTerm(),
                PageRequest.of(request.getPage(), request.getSize()));

        SearchWritersResponse response = new SearchWritersResponse();
        response.getWriters().addAll(writerPage.getContent().stream()
                .map(this::convertToWriterResponse)
                .collect(Collectors.toList()));
        response.setTotalElements(writerPage.getTotalElements());
        response.setTotalPages(writerPage.getTotalPages());
        response.setCurrentPage(writerPage.getNumber());
        return response;
    }

    private WriterResponse convertToWriterResponse(Writer writer) {
        WriterResponse response = new WriterResponse();
        response.setWriterId(String.valueOf(writer.getId()));
        response.setFullName(writer.getFullName());
//        response.setBirthDate(javax.xml.datatype.DatatypeFactory.newInstance()
//                .newXMLGregorianCalendar(new java.text.SimpleDateFormat("yyyy-MM-dd")
//                        .format(writer.getBirthDate())));
//        if (writer.getDeathDate() != null) {
//            response.setDeathDate( writer.getDeathDate()));
//        }
        response.setNationality(writer.getNationality());
        response.setBiography(writer.getBiography());
        return response;
    }
}
