package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.CreateUserDto;
import com.mfreimueller.frooty.payload.request.AuthRequest;
import com.mfreimueller.frooty.payload.response.AuthResponse;
import com.mfreimueller.frooty.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        final String token = authService.authenticateUser(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@RequestBody CreateUserDto createUserDto) {
        authService.registerUser(createUserDto);
    }


}
