//package ru.hpclab.hl.module1.service;
//
//import ru.hpclab.hl.module1.model.User_old;
//import ru.hpclab.hl.module1.repository.UserRepository;
//
//import java.util.List;
//import java.util.UUID;
//
//public class UserService_old {
//
//    private final UserRepository userRepository;
//
//    public UserService_old(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public List<User_old> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User_old getUserById(String id) {
//        return userRepository.findById(UUID.fromString(id));
//    }
//
//    public User_old saveUser(User_old user) {
//        return userRepository.save(user);
//    }
//
//    public void deleteUser(String id) {
//        userRepository.delete(UUID.fromString(id));
//    }
//
//    public User_old updateUser(String id, User_old user) {
//        user.setIdentifier(UUID.fromString(id));
//        return userRepository.put(user);
//    }
//}
