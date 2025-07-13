package com.fatihbayhan.libraryrest.service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.fatihbayhan.libraryrest.client.gen.CreateWriterRequest;
import com.fatihbayhan.libraryrest.client.gen.DeleteWriterRequest;
import com.fatihbayhan.libraryrest.client.gen.DeleteWriterResponse;
import com.fatihbayhan.libraryrest.client.gen.GetAllWritersRequest;
import com.fatihbayhan.libraryrest.client.gen.GetAllWritersResponse;
import com.fatihbayhan.libraryrest.client.gen.GetWriterRequest;
import com.fatihbayhan.libraryrest.client.gen.UpdateWriterRequest;
import com.fatihbayhan.libraryrest.client.gen.WriterResponse;
import com.fatihbayhan.libraryrest.dto.WriterDTO;

import jakarta.xml.bind.JAXBElement;

@Service
public class WriterService {
    
    @Autowired
    private WebServiceTemplate webServiceTemplate;
    
    public WriterDTO createWriter(WriterDTO writerDTO) {
        CreateWriterRequest request = new CreateWriterRequest();
        request.setFullName(writerDTO.getFullName());
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(writerDTO.getBirthDate());
            XMLGregorianCalendar birthDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            request.setBirthDate(birthDate);
            
            if (writerDTO.getDeathDate() != null) {
                calendar.setTime(writerDTO.getDeathDate());
                XMLGregorianCalendar deathDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                request.setDeathDate(deathDate);
            }
        } catch (Exception e) {
            throw new RuntimeException("Tarih dönüşümü hatası", e);
        }
        request.setNationality(writerDTO.getNationality());
        request.setBiography(writerDTO.getBiography());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        WriterResponse writerResponse = ((JAXBElement<WriterResponse>) response).getValue();
        
        WriterDTO responseDTO = new WriterDTO();
        responseDTO.setId(Long.parseLong(writerResponse.getWriterId()));
        responseDTO.setFullName(writerResponse.getFullName());
        if (writerResponse.getBirthDate() != null) {
            responseDTO.setBirthDate(writerResponse.getBirthDate().toGregorianCalendar().getTime());
        }
        if (writerResponse.getDeathDate() != null) {
            responseDTO.setDeathDate(writerResponse.getDeathDate().toGregorianCalendar().getTime());
        }
        responseDTO.setNationality(writerResponse.getNationality());
        responseDTO.setBiography(writerResponse.getBiography());
        return responseDTO;
    }

    public WriterDTO getWriter(Long writerId) {
        GetWriterRequest request = new GetWriterRequest();
        request.setWriterId(writerId.toString());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        WriterResponse writerResponse = ((JAXBElement<WriterResponse>) response).getValue();
        
        WriterDTO responseDTO = new WriterDTO();
        responseDTO.setId(Long.parseLong(writerResponse.getWriterId()));
        responseDTO.setFullName(writerResponse.getFullName());
        responseDTO.setBirthDate(writerResponse.getBirthDate().toGregorianCalendar().getTime());
        if (writerResponse.getDeathDate() != null) {
            responseDTO.setDeathDate(writerResponse.getDeathDate().toGregorianCalendar().getTime());
        }
        responseDTO.setNationality(writerResponse.getNationality());
        responseDTO.setBiography(writerResponse.getBiography());
        return responseDTO;
    }

    public WriterDTO updateWriter(WriterDTO writerDTO) {
        UpdateWriterRequest request = new UpdateWriterRequest();
        request.setWriterId(writerDTO.getId().toString());
        request.setFullName(writerDTO.getFullName());
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            if (writerDTO.getBirthDate() != null) {
                calendar.setTime(writerDTO.getBirthDate());
                XMLGregorianCalendar birthDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                request.setBirthDate(birthDate);
            }
            if (writerDTO.getDeathDate() != null) {
                calendar.setTime(writerDTO.getDeathDate());
                XMLGregorianCalendar deathDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                request.setDeathDate(deathDate);
            }
        } catch (Exception e) {
            throw new RuntimeException("Tarih dönüşümü hatası", e);
        }
        request.setNationality(writerDTO.getNationality());
        request.setBiography(writerDTO.getBiography());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        WriterResponse writerResponse = ((JAXBElement<WriterResponse>) response).getValue();
        
        WriterDTO responseDTO = new WriterDTO();
        responseDTO.setId(Long.parseLong(writerResponse.getWriterId()));
        responseDTO.setFullName(writerResponse.getFullName());
        if (writerResponse.getBirthDate() != null) {
            responseDTO.setBirthDate(writerResponse.getBirthDate().toGregorianCalendar().getTime());
        }
        if (writerResponse.getDeathDate() != null) {
            responseDTO.setDeathDate(writerResponse.getDeathDate().toGregorianCalendar().getTime());
        }
        responseDTO.setNationality(writerResponse.getNationality());
        responseDTO.setBiography(writerResponse.getBiography());
        return responseDTO;
    }

    public boolean deleteWriter(Long writerId) {
        DeleteWriterRequest request = new DeleteWriterRequest();
        request.setWriterId(writerId.toString());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        DeleteWriterResponse deleteResponse = ((JAXBElement<DeleteWriterResponse>) response).getValue();
        
        return deleteResponse.isSuccess();
    }

    public List<WriterDTO> getAllWriters() {
        GetAllWritersRequest request = new GetAllWritersRequest();
        Object response = webServiceTemplate.marshalSendAndReceive(request);
        GetAllWritersResponse writersResponse = ((JAXBElement<GetAllWritersResponse>) response).getValue();
        
        return writersResponse.getWriters().stream()
                .map(writer -> {
                    WriterDTO dto = new WriterDTO();
                    dto.setId(Long.parseLong(writer.getWriterId()));
                    dto.setFullName(writer.getFullName());
                    if (writer.getBirthDate() != null) {
                        dto.setBirthDate(writer.getBirthDate().toGregorianCalendar().getTime());
                    }
                    if (writer.getDeathDate() != null) {
                        dto.setDeathDate(writer.getDeathDate().toGregorianCalendar().getTime());
                    }
                    dto.setNationality(writer.getNationality());
                    dto.setBiography(writer.getBiography());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
