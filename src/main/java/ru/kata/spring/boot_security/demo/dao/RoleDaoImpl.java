package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext                     //предназаначена для автоматического связывания менеджера сущностей с бином.
    private EntityManager entityManager;

    public void add(Role user) {
        entityManager.persist(user);            //Чтобы изменить состояние сущности с Transient (New) на Managed (Persisted)
            }

    @Override
    public Role findByIdRole(Long id) {
        return entityManager.find(Role.class, id);
    }


    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select role from Role role", Role.class).getResultList();
    }

    @Override
    public Role findByName(String name) {
        return entityManager.createQuery("select r from Role r where r.role= :id", Role.class).setParameter("id", name).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<Role> listByName(List<String> name) {
        return entityManager.createQuery("select r from Role r where r.role in (:id)", Role.class).setParameter("id", name).getResultList();
    }
}
