package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.exception.UserAlreadyExistsException;
import com.mfreimueller.frooty.repositories.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * @param password The **already encrypted** password.
     */
    public void createUser(final String username, final String password) {
        Assert.notNull(username, "The username must not be null.");
        Assert.notNull(password, "The password must not be null.");

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        final User user = new User(username, password, Set.of());
        userRepository.save(user);
    }

    public void updateUser(User user) {
        Assert.notNull(user, "The user to update must not be null.");
        Assert.notNull(user.getId(), "The id of the user to update must not be null.");

        userRepository.save(user);
    }
}
