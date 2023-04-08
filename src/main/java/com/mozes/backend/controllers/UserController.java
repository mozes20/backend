package com.mozes.backend.controllers;

import com.mozes.backend.dto.UserDto;
import com.mozes.backend.dto.UserResponsDto;
import com.mozes.backend.dto.UserRoleDto;
import com.mozes.backend.models.User;
import com.mozes.backend.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
@RestControllerAdvice
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(409).body("A felhasználó már létezik!");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        // A token lejárt, ezt jelezzük hibakóddal
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException e) {
        // Az authToken érvénytelen, ezt jelezzük hibakóddal
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserDto user) throws ServiceException {
        userService.createUser(user, user.getUserRole());
        return ResponseEntity.ok("Data saved successfully");
    }

    @GetMapping()
    public ResponseEntity<List<UserResponsDto>> getUsers() throws ServiceException {
        List<UserResponsDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delUser(@PathVariable Long id) throws ServiceException {
        if(id == null){
            return  ResponseEntity.badRequest().body("Id is null");
        }
        userService.softDeleteUser(id);
        return ResponseEntity.ok("Data deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody User user) throws ServiceException {
        if(id == null){
            return  ResponseEntity.badRequest().body("Id is null");
        }
        userService.updateUser(user);
        return ResponseEntity.ok("Data updated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) throws ServiceException {
        if(id == null){
            return ResponseEntity.badRequest().body(new User());
        }
        return ResponseEntity.ok(userService.getById(id));
    }

}
