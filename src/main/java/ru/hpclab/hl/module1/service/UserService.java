package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.User;
import ru.hpclab.hl.module1.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User addUser(User user) { return repository.save(user); }

    public User getUser(String id) {
        return repository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<User> getAllUsers() { return repository.findAll(); }

    public void deleteUser(String id) {
        repository.deleteById(UUID.fromString(id));
    }

    public void clearAllUsers(){ repository.deleteAll(); }
}
