//package ru.hpclab.hl.module1.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import ru.hpclab.hl.module1.model.User_old;
//import ru.hpclab.hl.module1.service.UserService;
//
//import java.util.List;
//
//@RestController
//public class UserController_old {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserController_old(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/users")
//    public List<User_old> getUsers() {
//        return userService_old.getAllUsers();
//    }
//
//    @GetMapping("/users/{id}")
//    public User_old getUserById(@PathVariable String id) {
//        return userService_old.getUserById(id);
//    }
//
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable String id) {
//        userService.deleteUser(id);
//    }
//
//    @PostMapping(value = "/users/")
//    public User_old saveUser(@RequestBody User_old client) {
//        return userService.saveUser(client);
//    }
//
//    @PutMapping(value = "/users/{id}")
//    public User_old updateUser(@PathVariable(required = false) String id, @RequestBody User_old user) {
//        return userService.updateUser(id, user);
//    }
//
//}
