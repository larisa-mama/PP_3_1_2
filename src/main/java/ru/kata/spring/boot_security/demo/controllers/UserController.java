package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
//@RequestMapping("/user")
public class UserController {
private final UserDaoImpl userDao;

    public UserController(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

  /*  @GetMapping(value = "user")
    public String userPage( Principal principal, ModelMap model) {
             model.addAttribute("messages", userDao.findByName(principal.getName()));
        return "user";
    }*/
    @GetMapping(value = "/user")
    public String userPage (ModelMap modelMap, Principal principal) {
        User user1 = userDao.findByName(principal.getName());
        modelMap.addAttribute("messages", user1);
        return "user";
    }

}
