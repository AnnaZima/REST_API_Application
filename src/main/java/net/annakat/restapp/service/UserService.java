package net.annakat.restapp.service;

import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.UserRepository;
import net.annakat.restapp.repository.impl.UserRepositoryImpl;

import java.util.List;

public class UserService {
    UserRepository userRepository = new UserRepositoryImpl();

    public User getUser(Integer id) {
        return userRepository.get(id);
    }

    public User createUser(User user) {
        return userRepository.create(user);
    }

    public User updateUser(User user) {
        return  userRepository.update(user);
    }

    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }

    public List<User> getAll () {
        return userRepository.getAll();
    }

}
