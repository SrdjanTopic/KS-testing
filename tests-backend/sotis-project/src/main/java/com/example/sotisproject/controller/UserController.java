package com.example.sotisproject.controller;

import java.security.InvalidParameterException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sotisproject.model.User;
import com.example.sotisproject.service.UserService;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping()
    public User user(Principal user) {
        if(user!=null)
            return userService.findByUsername(user.getName());
        return null;
    }

    @GetMapping("/credentials/{id}")
    public User checkUser(@PathVariable(value = "id") Long userId, Principal loggedInUser) {
        User user =  this.userService.findById(userId);
        if(!loggedInUser.getName().equals(user.getUsername())) {
            throw new InvalidParameterException();
        }
        return user;
    }

}
