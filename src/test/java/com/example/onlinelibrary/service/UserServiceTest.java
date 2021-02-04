package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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
    public void test03FindAllUsersReturnUserList() {

        List<User> list = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        list.add(user1);
        list.add(user2);
        list.add(user3);

        Mockito.doReturn(list).when(userRepository).findAll();
        List<User> result = userService.findAll();
        Assertions.assertEquals(3, result.size());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void test04FindUserByIdNotInDatabaseExpectNull() {

        Long id = 456L;

        Mockito.when(userRepository.findById(id)).thenReturn(null);

        User result = userService.findUserById(id);

        Assertions.assertNull(result);

    }

    @Test
    public void test05FindUserByIdInDatabaseExpectUser() {

        Long id = 456L;

        User user = new User();
        user.setId(id);

        Mockito.when(userRepository.findUserById(id)).thenReturn(user);

        User result = userService.findUserById(id);

        Assertions.assertNotNull(result);

    }



//    @Test
//    public void test06ShouldSaveUserSuccessfully() {
//
//        User user = new User();
//        user.setFirstName("John");
//        user.setLastName("John");
//        user.setEmail("howtodoinjava@gmail.com");
//        user.setPassword("testpass1");
//        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
//
//        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
//        Mockito.when(userRepository.save(user)).thenReturn(user);
//        User result = userService.saveUser(user);
//        Assertions.assertNotNull(result);
//
//    } Null pointer setPassword

//    @Test
//    void updateUser() {
//
//        User user = new User();
//        user.setId(3L);
//        user.setFirstName("John");
//        user.setLastName("John");
//        user.setEmail("howtodoinjava@gmail.com");
//        user.setPassword("testpass1");
//
//        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
//        Mockito.when(userRepository.findUserById(user.getId())).thenReturn(user);
//
//        User result = userService.updateUser(user, user.getId());
//        Assertions.assertNotNull(result);
//
//
//    } Null pointer setPassword

//    @Test
//    void loadUserByUsername() {
//    }

    @Test
    void deleteById() {

        Long id = 456L;
        User user = new User();
        user.setId(id);


        userService.deleteById(user.getId());
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(user.getId());

    }

//    @Test
//    void search() {
//    }



}