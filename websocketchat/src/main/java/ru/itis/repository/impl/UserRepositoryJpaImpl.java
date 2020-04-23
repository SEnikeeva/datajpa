package ru.itis.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@Transactional
class UserRepositoryJpaImpl implements UserRepository {

    private static final String FIND_BY_LOGIN =
            "select u from User u where u.login = :login";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery(FIND_BY_LOGIN, User.class);
        query.setParameter("login", login);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        return entityManager.merge(user);
    }
}
