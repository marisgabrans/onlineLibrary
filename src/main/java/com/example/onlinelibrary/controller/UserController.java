package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.dto.UserRegistrationDto;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * TODO - add @Get and @Post mappings for different actions with users (User administration page)
 */

@Controller
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        List<User> users = userServiceImpl.findAll();
        model.addAttribute("users", users);
        return "/users-list";
    }

    @GetMapping("/users/{id}")
    public String findUserById(@PathVariable("id") Long id, Model model) {
        User user = userServiceImpl.findUserById(id);
        model.addAttribute("users", user);
        return "/users-list";
    }

    @GetMapping("/user-create")
    public String createBookForm(User user) {
        return "/user-create";
    }

    @PostMapping("/user-create")
    public String createUser(UserRegistrationDto userRegistrationDto) {
        userServiceImpl.save(userRegistrationDto);
        return "redirect:/users";
    }

    @GetMapping("/user-update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userServiceImpl.findUserById(id);
        model.addAttribute("user", user);
        return "/user-update";
    }

    @PostMapping("/user-update/{id}")
    public String updateUser(@PathVariable("id") Long id, UserRegistrationDto userRegistrationDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/user-update";
        }
        userServiceImpl.save(userRegistrationDto);
        return "/user-update";
    }

}