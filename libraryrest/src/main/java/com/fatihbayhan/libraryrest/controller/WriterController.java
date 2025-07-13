package com.fatihbayhan.libraryrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatihbayhan.libraryrest.dto.WriterDTO;
import com.fatihbayhan.libraryrest.service.WriterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/writers")
public class WriterController {

    @Autowired
    private WriterService writerService;

    @PostMapping
    public ResponseEntity<WriterDTO> createWriter(@Valid @RequestBody WriterDTO writerRequest) {
        WriterDTO createdWriter = writerService.createWriter(writerRequest);
        return ResponseEntity.ok(createdWriter);
    }

    @GetMapping
    public ResponseEntity<List<WriterDTO>> getAllWriters() {
        List<WriterDTO> writers = writerService.getAllWriters();
        return ResponseEntity.ok(writers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WriterDTO> getWriter(@PathVariable Long id) {
        WriterDTO writer = writerService.getWriter(id);
        return ResponseEntity.ok(writer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WriterDTO> updateWriter(@PathVariable Long id, @Valid @RequestBody WriterDTO writerRequest) {
        writerRequest.setId(id);
        WriterDTO updatedWriter = writerService.updateWriter(writerRequest);
        return ResponseEntity.ok(updatedWriter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWriter(@PathVariable Long id) {
        boolean success = writerService.deleteWriter(id);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
