package com.mfreimueller.frooty.security;

import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsManager implements UserDetailsManager {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(UserDetails user) {
        User u = new User(user.getUsername(), user.getPassword());
        userRepository.save(u);
    }

    @Override
    public void updateUser(UserDetails user) {
        User u = userRepository.findByUsername(user.getUsername());

        // TODO: encrypt password?
        // TODO: error handling
        if (u != null) {
            u.setPassword(user.getPassword());
            userRepository.save(u);
        }
    }

    @Override
    public void deleteUser(String username) {
        User u = userRepository.findByUsername(username);

        // TODO: error handling
        if (u != null) {
            userRepository.delete(u);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // TODO: I don't save a user here???
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username);

        if (u == null) {
            throw  new UsernameNotFoundException("No user named " + username + " found.");
        }

        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(u.getPassword())
                .build();
    }
}
