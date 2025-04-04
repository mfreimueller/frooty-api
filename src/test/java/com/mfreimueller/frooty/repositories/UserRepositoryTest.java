package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws Exception {
        User user1 = new User("Alice", "testpass1", Set.of());
        User user2 = new User("Bob", "testpass2", Set.of());

        assertNull(user1.getId());
        assertNull(user2.getId());

        userRepository.save(user1);
        userRepository.save(user2);

        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
    }

    @Test
    public void testFetchData() {
        Optional<User> user1 = userRepository.findByUsername("Alice");
        assertTrue(user1.isPresent());
        assertEquals("testpass1", user1.get().getPassword());

        Iterable<User> allUsers = userRepository.findAll();
        int count = 0;
        for (User u : allUsers) {
            count++;
        }

        assertEquals(2, count);
    }
}
