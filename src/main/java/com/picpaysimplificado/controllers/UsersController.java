package com.picpaysimplificado.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDto;
import com.picpaysimplificado.services.contracts.UserService;


@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")   
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){
        UserDto newUser = userService.create(dto);
        
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAllUsers();

        if(users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
