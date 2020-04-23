package ru.itis.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.model.Room;
import ru.itis.repository.RoomRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
class RoomRepositoryJpaImpl implements RoomRepository {

    private static final String FIND_BY_NAME =
            "select room from Room room where room.name = :name";

    private static final String FIND_ALL =
            "select room from Room room";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Room> findByName(String name) {
        TypedQuery<Room> query = entityManager.createQuery(FIND_BY_NAME, Room.class);
        query.setParameter("name", name);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Room> findAll() {
        return entityManager.createQuery(FIND_ALL, Room.class).getResultList();
    }

    @Override
    public Room save(Room room) {
        return entityManager.merge(room);
    }
}
