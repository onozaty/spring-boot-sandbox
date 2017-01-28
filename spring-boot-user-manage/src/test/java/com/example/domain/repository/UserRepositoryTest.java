package com.example.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:log4jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE" })
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void insertAndSelect() {

        User user1 = new User(
                "admin", "password", "山田", "太郎", "admin@exmaple.com");

        User user2 = new User(
                "user1", "password", "鈴木", "一郎", "user@exmaple.com");

        repository.create(user1);
        assertThat(user1.getId()).isNotNull();

        repository.create(user2);
        assertThat(user2.getId()).isNotNull();

        {
            User result = repository.find(user1.getId());
            assertThat(result).isEqualTo(user1);
        }
        {
            User result = repository.find(user2.getId());
            assertThat(result).isEqualTo(user2);
        }
        {
            List<User> result = repository.findAll();
            assertThat(result).isEqualTo(Arrays.asList(user1, user2));
        }
    }

}
