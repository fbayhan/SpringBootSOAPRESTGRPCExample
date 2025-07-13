package com.fatihbayhan.libraryrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatihbayhan.libraryrest.dto.UserDTO;
import com.fatihbayhan.libraryrest.dto.UserSearchResponseDTO;
import com.fatihbayhan.libraryrest.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userRequest) {
        try {
            UserDTO createdUser = userService.createUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kullanıcı oluşturulurken bir hata oluştu: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kullanıcılar listelenirken bir hata oluştu: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUser(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID: " + id + " olan kullanıcı bulunamadı");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kullanıcı bilgileri alınırken bir hata oluştu: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userRequest) {
        try {
            userRequest.setId(id);
            UserDTO updatedUser = userService.updateUser(userRequest);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID: " + id + " olan kullanıcı bulunamadı");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kullanıcı güncellenirken bir hata oluştu: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            boolean success = userService.deleteUser(id);
            if (success) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID: " + id + " olan kullanıcı bulunamadı");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kullanıcı silinirken bir hata oluştu: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestParam(defaultValue = "") String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            UserSearchResponseDTO response = userService.searchUsers(searchTerm, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kullanıcılar aranırken bir hata oluştu: " + e.getMessage());
        }
    }

}
