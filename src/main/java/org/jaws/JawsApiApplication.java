package org.jaws;

import org.jaws.model.UserDO;
import org.jaws.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JawsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JawsApiApplication.class, args);
    }

    @Bean
    CommandLineRunner clr(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
           userRepository.deleteAll();
            UserDO user = new UserDO();
            user.setFirstName("test");
            user.setLastName("test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail("test@test.com");
            user.setUserName("test");
            userRepository.save(user);
        };
    }

}
