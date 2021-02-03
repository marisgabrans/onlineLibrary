package com.example.onlinelibrary.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    public static final String URLLogin = "/login";
    public static final String URLHome = "/";

    @InjectMocks
    LoginController controller;

    private MockMvc mvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void testLogin() throws Exception {
        ResultActions resultActions = this.mvc.perform(get(URLLogin));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testHome() throws Exception {
        ResultActions resultActions = this.mvc.perform(get(URLHome));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    private ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }
}
