package dev.rdomingos.testeudemy.config;

import dev.rdomingos.testeudemy.domain.User;
import dev.rdomingos.testeudemy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public List<User> startDB() {
        User u1 = new User(null, "Rafael Domingos", "Q2V0W@example.com", "123456");
        User u2 = new User(null, "Romulo Domingos", "romulo@example.com", "123456");

        return repository.saveAll(List.of(u1, u2));
    }

}
