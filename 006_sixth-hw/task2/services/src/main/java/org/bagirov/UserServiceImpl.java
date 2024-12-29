package org.bagirov;

import org.bagirov.UserService;
import org.bagirov.model.User;

import java.util.*;

public class UserServiceImpl implements UserService {
    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        if (user.getId() == null) {
            user = new User(user.getUsername(), user.getEmail());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findUserById(UUID id) {

        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new IllegalArgumentException("User with this ID does not exist");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(UUID id) {
        users.remove(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
