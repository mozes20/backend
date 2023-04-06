package com.mozes.backend.controllers;

import com.mozes.backend.dto.UserDto;
import com.mozes.backend.dto.UserRoleDto;
import com.mozes.backend.models.User;
import com.mozes.backend.services.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserDto user) {
        try {
            userService.createUser(user,user.getUserRole());
            return ResponseEntity.ok("Data saved successfully");
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(){
        try {
            return ResponseEntity.ok(userService.getUsers());
        }catch (ServiceException e){
            List<User> empty = new ArrayList<>();
            return ResponseEntity.badRequest().body(empty);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delUser(@PathVariable Long id){
        try {
            if(id != null){
                userService.softDeleteUser(id);
                return ResponseEntity.ok("Data deleted");
            }
        return  ResponseEntity.badRequest().body("Id is null");
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody User user){
        try {
            if(id != null){
                userService.updateUser(user);
                return ResponseEntity.ok("Data deleted");
            }
            return  ResponseEntity.badRequest().body("Id is null");
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id){
        try {
            if(id != null){
                return ResponseEntity.ok(userService.getById(id));
            }
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(new User());
        }
        return ResponseEntity.badRequest().body(new User());
    }
}
