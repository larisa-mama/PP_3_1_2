package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserDao {
    User getUser(Long id);

    List<User> getAllUsers();

    void add(User user);                 // пришлось изменить метод,

    void edit(User user);

    User findByName(String email);

    void delete(Long id);
}
