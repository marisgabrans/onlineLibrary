package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationControllerTest {

    public static final String URLShowRegisterUserAccount = "/registration";
    public static final String URLUserRegistration = "/registration";
    public static final String URLRegisterUserAccount = "/registration";


    @InjectMocks
    UserRegistrationController controller;

    @Mock
    UserService userService;

    private MockMvc mvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void testUserRegistration() throws Exception {
        ResultActions resultActions = this.mvc.perform(get(URLUserRegistration).flashAttr("user",getUser(1L)));
        resultActions.andExpect(status().isOk());

    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        ResultActions resultActions = this.mvc.perform(get(URLShowRegisterUserAccount));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void testRegisterUserAccount_hasError() throws Exception {
        ResultActions resultActions = this.mvc.perform(post(URLRegisterUserAccount).flashAttr("user",getUser(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void testRegisterUserAccount_hasNoError() throws Exception {
        when(userService.findEmail(anyString())).thenReturn(getUser(1L));
        ResultActions resultActions = this.mvc.perform(post(URLRegisterUserAccount).flashAttr("user",getUserForValidation(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("errMsg"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void testRegisterUserAccount_findUser() throws Exception {
        when(userService.findEmail(anyString())).thenReturn(null);
        userService.saveUser(getUser(1L));
        verify(userService).saveUser(any());
        ResultActions resultActions = this.mvc.perform(post(URLRegisterUserAccount).flashAttr("user",getUserForValidation(1L)));
        resultActions.andExpect(status().isFound())
                .andExpect(view().name("redirect:/registration?success"));
    }


    private User getUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    private User getUserForValidation(Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail("Test@mail.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("Test12345678");
        return user;
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setId(1L);
        User user1 = new User();
        user1.setId(2L);
        users.add(user);
        users.add(user1);
        return users;
    }

    private ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }
}
