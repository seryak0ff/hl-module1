//package ru.hpclab.hl.module1.configuration;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
////import ru.hpclab.hl.module1.model.User_old;
//import ru.hpclab.hl.module1.model.User;
//import ru.hpclab.hl.module1.repository.UserRepository;
////import ru.hpclab.hl.module1.service.StatisticsService;
//import ru.hpclab.hl.module1.service.UserService;
//
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Configuration
//public class ServicesConfig {
//
//    @Bean
//    UserService userService(UserRepository userRepository) {
//        UserService userService = new UserService(userRepository);
//        for (int i = 0; i < 5; i++) {
//            userRepository.save(new User(UUID.randomUUID(), "new super login user", "university1", LocalDate.parse("2025-03-01")));
//        }
//        return userService;
//    }
//
//
//
//    @Bean
//    @ConditionalOnProperty(prefix = "statistics", name = "service", havingValue = "console2000")
//    StatisticsService statisticsService2000(UserService userService){
//        return new StatisticsService(2000, userService);
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "statistics", name = "service", havingValue = "console1000")
//    StatisticsService statisticsService1000(UserService userService){
//        return new StatisticsService(1000, userService);
//    }
//}
