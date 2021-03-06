package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;



import java.util.Map;



@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String adminPage() {
        return "admin";
    }


    //Сделать страницу!!!
    @GetMapping("/userList")
    public String getUserList(Model model) {
        model.addAttribute("user", userService.findAll());
        return "userList";
    }

    @GetMapping("/edit/{id}")
    public String userEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user, Model model) {
        model.addAttribute("roles", Role.values());
        userService.saveUser(user);
        return "redirect:/admin/userList";
    }


    @GetMapping("/add")
    public String addUserForm(Model model, User user) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "addUser";
    }


    @PostMapping("/add")
    public String addUser (@ModelAttribute User user) {
        userService.saveUser(user);

        return "redirect:/admin/userList";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/userList";
    }


}
