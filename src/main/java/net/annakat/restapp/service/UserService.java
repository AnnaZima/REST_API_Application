package net.annakat.restapp.service;

import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.UserRepository;
import net.annakat.restapp.repository.impl.UserRepositoryImpl;

public class UserService {
    UserRepository userRepository = new UserRepositoryImpl();

    public User getUser(Integer id) {
        return userRepository.get(id);
    }

    public User createUser(User user) {
        return userRepository.create(user);
    }
}
