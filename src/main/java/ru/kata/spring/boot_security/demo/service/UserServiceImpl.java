package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final RoleDaoImpl roleDao;
    private final UserDaoImpl userDao;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Autowired
    public UserServiceImpl(RoleDaoImpl roleDao, UserDaoImpl userDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public boolean addRole(Role role) {
        Role userOne = roleDao.findByName(role.getRole());
        if (userOne != null) {
            return false;
        }
        roleDao.add(role);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByNameRole(String name) {
        return roleDao.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByIdRole(Long id) {
        return roleDao.findByIdRole(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> listByRole(List<String> name) {
        return roleDao.listByName(name);
    }

    @Override
    @Transactional
    public void add(User user) {                                        // изменения метода для правильного сохранения в БД, иначе пароль не кодировался
        User userOne = userDao.findByName(user.getUsername());
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userDao.add(user);

    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void edit(User user) {
        userDao.edit(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.findByName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userOne = findByUsername(username);
        if (userOne == null) {
            throw new UsernameNotFoundException(username + " не найден");
        }
        UserDetails user = new org.springframework.security.core.userdetails.User(userOne.getUsername(),
                userOne.getPassword(), authority(userOne.getRoles()));
        return userOne;
    }

    private Collection<? extends GrantedAuthority> authority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}