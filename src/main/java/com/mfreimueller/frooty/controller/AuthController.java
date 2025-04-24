package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.exception.InvalidPasswordException;
import com.mfreimueller.frooty.exception.UserAlreadyExistsException;
import com.mfreimueller.frooty.payload.request.AuthRequest;
import com.mfreimueller.frooty.payload.request.SignupRequest;
import com.mfreimueller.frooty.payload.response.AuthResponse;
import com.mfreimueller.frooty.payload.response.MessageResponse;
import com.mfreimueller.frooty.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        try {
            authService.registerUser(
                    signupRequest.getUsername(),
                    signupRequest.getPassword()
            );
        } catch (UserAlreadyExistsException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already taken."));
        } catch (InvalidPasswordException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("The Password does not conform to the guidelines."));
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
