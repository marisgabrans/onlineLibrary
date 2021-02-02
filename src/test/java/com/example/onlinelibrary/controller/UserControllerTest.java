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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    public static final String URLGetById = "/users/1";

    @InjectMocks
    UserController controller;

    @Mock
    UserService userService;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void findUserById_Success() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(getUser(1L));

        ResultActions resultActions = this.mvc.perform(get(URLGetById));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("/users-list"));

    }

    private User getUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

}
