package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User userRegistration() {
        return new User();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") User user, Model model) {
        if(userService.findEmail(user.getEmail()) != null) {
            model.addAttribute("errMsg", " This email address is already taken!");
            return "/registration";
        }
        userService.saveUser(user);
        return "redirect:/registration?success";
    }


}
