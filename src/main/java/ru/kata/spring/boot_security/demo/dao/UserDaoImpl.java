package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);

    }

    @Override
    public void edit(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findByName(String email) {      // username меняем на email
        return entityManager.createQuery("select u from User u join fetch u.roles where u.email = :email", User.class).setParameter("email", email)
                .getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void delete(Long id) {
entityManager.remove(getUser(id));
    }
}
