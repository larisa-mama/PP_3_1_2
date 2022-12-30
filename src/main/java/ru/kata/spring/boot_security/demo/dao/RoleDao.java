package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;

public interface RoleDao {
    Role findByIdRole (Long id);
    List<Role> getAllRoles();
    Role findByName(String name);
    List<Role> listByName(List<String> name);
}
