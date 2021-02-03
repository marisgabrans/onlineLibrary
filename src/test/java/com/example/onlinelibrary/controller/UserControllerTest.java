package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.UserService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    public static final String URLUsers = "/users";
    public static final String URLGetById = "/users/1";
    public static final String URLCreateUserForm = "/user-create";
    public static final String URLShowUpdateForm = "/user-update";

    @InjectMocks
    UserController controller;

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
    public void testFindAll() throws Exception {
        String keyword = "Data";
        when(userService.search(keyword)).thenReturn(getUsers());
        ResultActions resultActions = this.mvc.perform(get(URLUsers).param("keyword", keyword));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("keyword"))
                .andExpect(view().name("users-list"));
    }

    @Test
    public void findUserById_Success() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(getUser(1L));
        ResultActions resultActions = this.mvc.perform(get(URLGetById));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("users-list"));

    }

    @Test
    public void testCreateUserForm() throws Exception {
        ResultActions resultActions = this.mvc.perform(get(URLCreateUserForm));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("user-create"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        String user_id = "1";
        when(userService.findUserById(anyLong())).thenReturn(getUser(1L));
        ResultActions resultActions = this.mvc.perform(get(URLShowUpdateForm).param("user_id", user_id));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user-update"));
    }

    private User getUser(Long id) {
        User user = new User();
        user.setId(id);
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
