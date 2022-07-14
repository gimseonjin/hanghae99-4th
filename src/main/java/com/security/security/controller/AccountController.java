package com.security.security.controller;

import com.security.security.model.User;
import com.security.security.model.dto.request.RegisterRequestDto;
import com.security.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto registerRequestDto){
        User user = userService.save(registerRequestDto.toUser());
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
}
