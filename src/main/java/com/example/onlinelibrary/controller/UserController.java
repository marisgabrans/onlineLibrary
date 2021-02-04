package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired UserService userService;


    // test done
    @GetMapping("/users")
    public String users(Model model, @Param("keyword") String keyword) {
        List<User> users = userService.search(keyword);
        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "users-list";
    }

    // test done
    @GetMapping("/users/{id}")
    public String findUserById(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id); //mock calling service
        model.addAttribute("users", user); // check
        return "users-list";  //check
    }
    // test done
    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "user-create";
    }
    // test done
    @PostMapping("/user-create")
    public String createUser(@ModelAttribute @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "user-create";
        } else if(userService.findEmail(user.getEmail()) != null) {
            model.addAttribute("errMsg", " This email address is already taken!");
            return "user-update";
        }
        model.addAttribute("user", userService.saveUser(user));
        return "redirect:/users";
    }
    // test done
    @GetMapping("/user-update")
    public String showUpdateForm(@RequestParam(name = "user_id", required = true) Long user_id, Model model) {
        User user = userService.findUserById(user_id);
        model.addAttribute("user", user);
        return "user-update";
    }
    // test done
    @PostMapping("/user-update")
    public String updateUser(@RequestParam (name = "user_id", required = true) Long user_id,
                             @ModelAttribute @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user-update";
        } else if(userService.findEmail(user.getEmail()) != null) {
        model.addAttribute("errMsg", " This email address is already taken!");
        return "user-update";
    }
        userService.updateUser(user, user_id);
        model.addAttribute("users", userService.findAll());
        return "users-list";
    }
    // test done
    @GetMapping("/user-delete")
    public String deleteUser(@RequestParam(name = "user_id", required = true) Long id, Model model) {
        userService.deleteById(id);
        model.addAttribute("users", userService.findAll());
        return "users-list";
    }

}