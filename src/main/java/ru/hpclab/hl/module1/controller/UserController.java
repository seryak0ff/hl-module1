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
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ObservabilityService observabilityService;

    public UserController(ObservabilityService observabilityService, UserService userService) {
        this.observabilityService = observabilityService;
        this.userService = userService;
    }

    // Добавление нового пользователя
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        this.observabilityService.start(getClass().getSimpleName() + ":createUser - Controller");
        ResponseEntity<User> temp = ResponseEntity.ok(userService.addUser(user));
        this.observabilityService.stop(getClass().getSimpleName() + ":createUser - Controller");
        return temp;
    }

    // Получение пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":getUser - Controller");
        User user = userService.getUser(id);
        ResponseEntity<User> temp = user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
        this.observabilityService.stop(getClass().getSimpleName() + ":getUser - Controller");
        return temp;
    }

    // Получение всех пользователей
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        this.observabilityService.start(getClass().getSimpleName() + ":getAllUsers - Controller");
        ResponseEntity<List<User>> temp = ResponseEntity.ok(userService.getAllUsers());
        this.observabilityService.stop(getClass().getSimpleName() + ":getAllUsers - Controller");
        return temp;
    }

    // Удаление пользователя по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":deleteUser - Controller");
        userService.deleteUser(id);
        ResponseEntity<Void> temp = ResponseEntity.noContent().build();
        this.observabilityService.stop(getClass().getSimpleName() + ":deleteUser - Controller");
        return temp;
    }

    // Новый эндпоинт для очистки всех данных
    @Operation(summary = "Clear all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All users deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearAllUsers() {
        this.observabilityService.start(getClass().getSimpleName() + ":clearAllUsers - Controller");
        userService.clearAllUsers();
        ResponseEntity<Void> temp = ResponseEntity.noContent().build();
        this.observabilityService.stop(getClass().getSimpleName() + ":clearAllUsers - Controller");
        return temp;
    }
}
