package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Role;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @MockBean
    private Collection<Role> roles;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test01findUserByEmailNotInDatabaseExpectNull() {

        String email = "testuser@gmail.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(null);

        User result = userService.findEmail(email);

        Assertions.assertNull(result);

    }

    @Test
    public void test02findUserByEmailInDatabaseExpectUser() {

        String email = "testuser@gmail.com";

        User user = new User();
        user.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.findEmail(email);

        Assertions.assertNotNull(result);

    }

    @Test
    public void test03FindUserByIdNotInDatabaseExpectNull() {

        Long id = 456L;

        Mockito.when(userRepository.findById(id)).thenReturn(null);

        User result = userService.findUserById(id);

        Assertions.assertNull(result);

    }

    @Test
    public void test04FindUserByIdInDatabaseExpectUser() {

        Long id = 456L;

        User user = new User();
        user.setId(id);

        Mockito.when(userRepository.findUserById(id)).thenReturn(user);

        User result = userService.findUserById(id);

        Assertions.assertNotNull(result);

    }

    @Test
    public void test05FindAllUsersReturnUserList() {

        List<User> list = new ArrayList<>();
        User user1 = new User("John", "John", "howtodoinjava@gmail.com", "testpass1", roles);
        User user2 = new User("Alex", "kolenchiski", "alexk@yahoo.com", "testpass2", roles);
        User user3 = new User("Steve", "Waugh", "swaugh@gmail.com", "testpass3", roles);

        list.add(user1);
        list.add(user2);
        list.add(user3);


        Mockito.doReturn(list).when(userRepository).findAll();

        List<User> result = userService.findAll();

        Assertions.assertEquals(3, result.size());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();



    }

//    @Test
//    public void test06ShouldSaveUserSuccessfully() {
//        User user = new User("John", "John", "howtodoinjava@gmail.com", "testpass1", roles);
//
//        Mockito.when(userRepository.save(user)).thenReturn(user);
//
//        User result = userService.saveUser(user);
//
//        Assertions.assertNotNull(result);
//
//
//    }

    // Null pointer issue


}