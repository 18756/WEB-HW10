package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderById();
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User register(String login, String name, String password) {
        User user = new User();
        user.setLogin(login);
        user.setName(name);
        user = userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), user.getLogin(), password);
        return user;
    }
}
