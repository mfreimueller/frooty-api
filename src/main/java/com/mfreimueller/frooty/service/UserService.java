package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.CreateUserDto;
import com.mfreimueller.frooty.dto.UpdateUserDto;
import com.mfreimueller.frooty.dto.UserDto;
import com.mfreimueller.frooty.repositories.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserById(final Integer userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("No authenticated user found.");
        }

        final String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username)
                );
    }

    public UserDto getCurrentUserDto() {
        return conversionService.convert(getCurrentUser(), UserDto.class);
    }

    public void createUser(CreateUserDto createUserDto) {
        Assert.notNull(createUserDto, "createUserDto must not be null.");

        if (userRepository.existsByUsername(createUserDto.username())) {
            throw new IllegalArgumentException();
        }

        final User user = new User(createUserDto.username(), passwordEncoder.encode(createUserDto.password()), createUserDto.email());
        userRepository.save(user);
    }

    public void updateUser(UpdateUserDto updateUserDto) {
        Assert.notNull(updateUserDto, "updateUserDto must not be null.");

        User currentUser = getCurrentUser();

        boolean updateUsername = !Objects.equals(updateUserDto.username(), currentUser.getUsername());
        boolean updateEmail = !Objects.equals(updateUserDto.email(), currentUser.getEmail());
        boolean updatePassword = updateUserDto.password() != null && !updateUserDto.password().isEmpty();

        if (updateUsername) {
            if (userRepository.existsByUsername(updateUserDto.username())) {
                throw new IllegalArgumentException();
            }

            currentUser.setUsername(updateUserDto.username());
        }

        if (updateEmail) {
            if (userRepository.existsByEmail(updateUserDto.email())) {
                throw new IllegalArgumentException();
            }

            currentUser.setEmail(updateUserDto.email());
        }

        if (updatePassword) {
            if (updateUserDto.password().length() < 8) {
                throw new IllegalArgumentException();
            }

            currentUser.setPassword(passwordEncoder.encode(updateUserDto.password()));
        }

        if (updateUsername || updateEmail || updatePassword) {
            userRepository.save(currentUser);
        }
    }

    public void deleteCurrentUser() {
        final User currentUser = getCurrentUser();
        userRepository.delete(currentUser);
    }
}
