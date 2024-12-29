package org.bagirov;

import org.bagirov.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    private UserService userService;

    @BeforeEach
    void initializeService() {
        userService = new UserServiceImpl();
    }

    @Test
    void shouldCreateUserSuccessfully() {
        User newUser = new User("john_doe", "john@example.com");

        User savedUser = userService.createUser(newUser);

        assertNotNull(savedUser.getId(), "User ID should not be null");

        assertEquals("john_doe", savedUser.getUsername(), "Usernames should match");
        assertEquals("john@example.com", savedUser.getEmail(), "Emails should match");
    }

    @Test
    void shouldFindUserById() {
        User newUser = new User("alice_smith", "alice@example.com");
        User savedUser = userService.createUser(newUser);

        Optional<User> foundUser = userService.findUserById(savedUser.getId());

        assertTrue(foundUser.isPresent(), "User should be found by ID");
        assertEquals(savedUser, foundUser.get(), "Returned user should be the same as saved user");
    }

    @Test
    void shouldUpdateUserDetails() {
        User user = new User("bob", "bob@example.com");
        User savedUser = userService.createUser(user);

        savedUser.setUsername("bob_updated");
        User updatedUser = userService.updateUser(savedUser);

        assertEquals("bob_updated", updatedUser.getUsername(), "Username should be updated");
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        User user = new User("charlie", "charlie@example.com");
        User savedUser = userService.createUser(user);

        userService.deleteUser(savedUser.getId());

        Optional<User> deletedUser = userService.findUserById(savedUser.getId());
        assertFalse(deletedUser.isPresent(), "User should be deleted and not found");
    }


}
