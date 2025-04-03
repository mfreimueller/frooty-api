package com.mfreimueller.frooty.security;

import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomUserDetailsManager implements UserDetailsManager {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(UserDetails user) {
        User u = new User(user.getUsername(), user.getPassword(), Set.of());
        userRepository.save(u);
    }

    @Override
    public void updateUser(UserDetails user) {
        User u = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No user named " + user.getUsername() + " found."));

        u.setPassword(user.getPassword());
        userRepository.save(u);
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user named " + username + " found."));

        userRepository.delete(user);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // TODO: I don't save a user here???
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user named " + username + " found."));

        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(u.getPassword())
                .build();
    }
}
