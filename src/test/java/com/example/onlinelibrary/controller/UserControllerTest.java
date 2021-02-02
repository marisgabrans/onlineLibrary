package com.example.onlinelibrary.controller;

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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)

public class UserControllerTest {

    public static final String URLGetById = "/users/1";
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
    public void findUserById_Success() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(getUser(1L));

        ResultActions resultActions = this.mvc.perform(get(URLGetById));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("/users-list"));

    }

    @Test
    public void testShowUpdateForm() throws Exception {
        String user_id = "1";
        when(userService.findUserById(anyLong())).thenReturn(getUser(1L));
        ResultActions resultActions = this.mvc.perform(get(URLShowUpdateForm).param("user_id", user_id));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("/user-update"));
    }

    private User getUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    private ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }





}
