package net.annakat.restapp.service;

import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.UserRepository;
import net.annakat.restapp.repository.impl.HibernateUserRepositoryImpl;

import java.util.List;

public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {
        userRepository = new HibernateUserRepositoryImpl();
    }

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
