package ru.hpclab.hl.module1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.User;
import ru.hpclab.hl.module1.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Добавление нового пользователя
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    // Получение пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = userService.getUser(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // Получение всех пользователей
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Удаление пользователя по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Новый эндпоинт для очистки всех данных
    @Operation(summary = "Clear all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All users deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearAllUsers() {
        userService.clearAllUsers();
        return ResponseEntity.noContent().build();
    }
}
