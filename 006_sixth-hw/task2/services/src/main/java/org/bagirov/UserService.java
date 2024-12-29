package org.bagirov;

import org.bagirov.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(User user);
    Optional<User> findUserById(UUID id);
    User updateUser(User user);
    void deleteUser(UUID id);
    List<User> getAllUsers();

}
