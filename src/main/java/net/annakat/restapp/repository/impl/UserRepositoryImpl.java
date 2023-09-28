package net.annakat.restapp.repository.impl;

import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.UserRepository;
import net.annakat.restapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User create(User object) {
        User savedUser;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(object);
            savedUser = session.get(User.class, object.getId());
            transaction.commit();
        }
        return savedUser;
    }

    @Override
    public User get(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }

    }

    @Override
    public User update(User object) {
        User merge;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            merge = session.merge(object);
            transaction.commit();
        }
        return merge;
    }

    @Override
    public void delete(Integer id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(id);
            transaction.commit();
        }

    }

    @Override
    public List<User> getAll() {
        List<User> users;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> from_user = session.createQuery("From User", User.class);
            users = from_user.list();
        }
        return users;
    }
}
