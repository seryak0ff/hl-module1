package ru.hpclab.hl.module1.apimodule.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.apimodule.model.User;
import ru.hpclab.hl.module1.apimodule.repository.UserRepository;
import ru.hpclab.hl.module1.apimodule.service.statistics.ObservabilityService;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;

    private final ObservabilityService observabilityService;

    public UserService(ObservabilityService observabilityService, UserRepository repository) {
        this.observabilityService = observabilityService;
        this.repository = repository;
    }

    public User addUser(User user) {
        this.observabilityService.start(getClass().getSimpleName() + ":addUser");
        User temp = repository.save(user);
        this.observabilityService.stop(getClass().getSimpleName() + ":addUser");
        return  temp;
    }

    public User getUser(String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":getUser");
        User temp = repository.findById(UUID.fromString(id)).orElse(null);
        this.observabilityService.stop(getClass().getSimpleName() + ":getUser");
        return temp;

    }

    public List<User> getAllUsers() {
        this.observabilityService.start(getClass().getSimpleName() + ":getAllUsers");
        List<User> temp = repository.findAll();
        this.observabilityService.stop(getClass().getSimpleName() + ":getAllUsers");
        return temp;
    }

    public void deleteUser(String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":deleteUser");
        repository.deleteById(UUID.fromString(id));
        this.observabilityService.stop(getClass().getSimpleName() + ":deleteUser");
    }

    public void clearAllUsers(){
        this.observabilityService.start(getClass().getSimpleName() + ":clearAllUsers");
        repository.deleteAll();
        this.observabilityService.stop(getClass().getSimpleName() + ":clearAllUsers");
    }
}
