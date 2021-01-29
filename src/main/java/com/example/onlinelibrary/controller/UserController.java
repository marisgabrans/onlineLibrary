package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "/users-list";
    }

    @GetMapping("/users/{id}")
    public String findUserById(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("users", user);
        return "/users-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "/user-create";
    }

    @PostMapping("/user-create")
    public String createUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "/user-create";
        }
        userService.saveUser(user);
        model.addAttribute("user", user);
        return "redirect:/users";
    }

    @GetMapping("/user-update")
    public String showUpdateForm(@RequestParam(name = "user_id", required = true) Long user_id, Model model) {
        User user = userService.findUserById(user_id);
        model.addAttribute("user", user);
        return "/user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@RequestParam(name = "user_id", required = true) Long user_id, User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(user_id);
            return "/user-update";
        }
        userService.updateUser(user, user_id);
        model.addAttribute("users", userService.findAll());
        return "/users-list";
    }
    @GetMapping("/user-delete")
    public String deleteUser(@RequestParam(name = "user_id", required = true) Long id, Model model) {
        userService.deleteById(id);
        model.addAttribute("users", userService.findAll());
        return "/users-list";
    }
}