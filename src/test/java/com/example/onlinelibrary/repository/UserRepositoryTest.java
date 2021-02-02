package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Role;
import com.example.onlinelibrary.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collection;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Collection<Role> roles;

    @Test
    public void test01() {

        User testUser = new User("testFirst", "testSecond", "test@test.com", "testtest", roles);
        User savedUser = userRepository.save(testUser);
        savedUser.setRoles(Arrays.asList(new Role("ROLE_USER")));
        System.out.println(savedUser.getRoles().toString());
        System.out.println(savedUser.getFirstName());
        System.out.println(savedUser.getLastName());
        System.out.println(savedUser.getId());
        System.out.println(savedUser.getEmail());
        System.out.println(savedUser.getPassword());

        System.out.println(userRepository.findByEmail("test@test.com").getEmail());

        Assertions.assertNotNull(savedUser);

    }



}