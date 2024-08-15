package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.LoginRecord;
import com.mindhub.todolist.dtos.RegisterRecord;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Login Client", description = "Login client")
    @PostMapping("/login")
    public ResponseEntity<?> loginClient(@RequestBody LoginRecord loginRecord) {
        return ResponseEntity.ok(userService.login(loginRecord));
    }

    @Operation(summary = "Register Client", description = "Register new client")
    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody RegisterRecord registerRecord) {
        return ResponseEntity.ok(userService.Register(registerRecord));
    }
}