package ru.hpclab.hl.module1.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import ru.hpclab.hl.module1.model.User_old;
import ru.hpclab.hl.module1.model.User;
import ru.hpclab.hl.module1.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserServiceTest.UserServiceTestConfiguration.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateAndGet(){
        //create
        User user = new User(UUID.randomUUID(),"login", "university", LocalDate.parse("2022-02-06"));

        User savedUser = userService.saveUser(user);

        Assertions.assertEquals(user.getLogin(), savedUser.getLogin());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);

        //getAll
        List<User> userList = userService.getAllUsers();

        Assertions.assertEquals("login1", userList.get(0).getLogin());
        Assertions.assertEquals("login2", userList.get(1).getLogin());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();

    }

    @Configuration
    static class UserServiceTestConfiguration {

        @Bean
        UserRepository userRepository() {
            UserRepository userRepository = mock(UserRepository.class);
            when(userRepository.save(any())).thenReturn(new User(UUID.randomUUID(), "login", "university", LocalDate.parse("2022-02-06")));
            when(userRepository.findAll())
                    .thenReturn(Arrays.asList(new User(UUID.randomUUID(), "login1", "university1", LocalDate.parse("2025-03-01")),
                            new User(UUID.randomUUID(), "login2", "university1", LocalDate.parse("2025-03-02"))));
            return userRepository;
        }

        @Bean
        UserService UserService(UserRepository userRepository){
            return new UserService(userRepository);
        }
    }

}
