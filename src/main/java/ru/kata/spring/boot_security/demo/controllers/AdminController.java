package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;

   // @Autowired  конструктор единственный, поэтому не надо
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping
    public String sawUsers (Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "new_user")
    public String makeUser (User user, Model model) {
        model.addAttribute("roleList", userService.getAllRoles());
        return "new_user";
    }

    @PostMapping("new_user")
    public String addUser(User user) {
       List<String> list1 = user.getRoles().stream().map(role -> role.getRole()).collect(Collectors.toList());
       List<Role> list2 = userService.listByRole(list1);
       user.setRoles(list2);
       userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "change/{id}")
    public String changeUser(@PathVariable ("id") Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", userService.getAllRoles());
        return "change";
    }
@PostMapping(value = "change")
public String updateUser (User user) {
        List<String> list1 = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
        List<Role> list2 = userService.listByRole(list1);
        user.setRoles(list2);
        userService.edit(user);
        return "redirect:/admin";
 }
    @GetMapping(value = "delete/{id}")
    public String delete(@PathVariable ("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
