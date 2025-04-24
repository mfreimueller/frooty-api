package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.exception.InvalidPasswordException;
import com.mfreimueller.frooty.security.CustomUserDetailsManager;
import com.mfreimueller.frooty.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsManager userDetailsManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    public String authenticateUser(final String username, final String password) {
        Assert.notNull(username, "username must not be null");
        Assert.notNull(password, "password must not be null");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails user = userDetailsManager.loadUserByUsername(username);
        return jwtUtil.generateToken(user.getUsername());
    }

    public void registerUser(final String username, final String password) {
        Assert.notNull(username, "username must not be null");
        Assert.notNull(password, "password must not be null");

        if (password.length() < 8) {
            throw new InvalidPasswordException();
        }

        userService.createUser(username, passwordEncoder.encode(password));
    }
}
