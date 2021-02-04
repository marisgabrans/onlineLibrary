package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
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

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    public static final String URLUsers = "/users";
    public static final String URLGetById = "/users/1";
    public static final String URLCreateUserForm = "/user-create";
    public static final String URLShowUpdateForm = "/user-update";
    public static final String URLUpdateUser = "/user-update";
    public static final String URLDeleteUser = "/user-delete";

    @InjectMocks
    UserController controller;

    @Mock
    UserService userService;

    @MockBean
    private BindingResult bindingResult;

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
    public void testCreateUser_hasError() throws Exception {
        ResultActions resultActions = this.mvc.perform(post(URLCreateUserForm).flashAttr("user",getUser(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("user-create"));
    }

    @Test
    public void testCreateUser_hasNoError() throws Exception {
        when(userService.findEmail(anyString())).thenReturn(getUser(1L));
        ResultActions resultActions = this.mvc.perform(post(URLCreateUserForm).flashAttr("user",getUserForValidation(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("errMsg"))
                .andExpect(view().name("user-update"));
    }

    @Test
    public void testCreateUser_saveUser() throws Exception {
        when(userService.findEmail(anyString())).thenReturn(null);
        when(userService.saveUser(any())).thenReturn(getUser(1L));
        ResultActions resultActions = this.mvc.perform(post(URLCreateUserForm).flashAttr("user",getUserForValidation(1L)));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("redirect:/users"));
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

    @Test
    public void testUpdateUser_hasError() throws Exception {
        String user_id = "1";
        ResultActions resultActions = this.mvc.perform(post(URLUpdateUser).param("user_id", user_id).flashAttr("user",getUser(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("user-update"));
    }

    @Test
    public void testUpdateUser_hasNoError() throws Exception {
        String user_id = "1";
        when(userService.findEmail(anyString())).thenReturn(getUser(1L));
        ResultActions resultActions = this.mvc.perform(post(URLUpdateUser).param("user_id", user_id).flashAttr("user",getUserForValidation(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("errMsg"))
                .andExpect(view().name("user-update"));
    }

    @Test
    public void testUpdateUser_findUser() throws Exception {
        String user_id = "1";
        when(userService.findEmail(anyString())).thenReturn(null);
        when(userService.findAll()).thenReturn(getUsers());
        userService.updateUser(getUser(1L), 1L);
        verify(userService).updateUser(any(), anyLong());
        ResultActions resultActions = this.mvc.perform(post(URLUpdateUser).param("user_id", user_id).flashAttr("user",getUserForValidation(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("users-list"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        String user_id = "1";
        userService.deleteById(1L);
        verify(userService).deleteById(any());
        ResultActions resultActions = this.mvc.perform(get(URLDeleteUser).param("user_id", user_id));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("users-list"));
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
