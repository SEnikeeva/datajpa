package ru.itis.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.model.Message;
import ru.itis.model.Room;
import ru.itis.repository.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
class MessageRepositoryJpaImpl implements MessageRepository {

    public static final String FIND_BY_ROOM =
            "select message from Message message " +
            "join Room room on message.room = room " +
            "where room.id = :id " +
            "order by message.time";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Message save(Message message) {
        return entityManager.merge(message);
    }

    @Override
    public List<Message> findByRoom(Room room) {
        TypedQuery<Message> query = entityManager.createQuery(FIND_BY_ROOM, Message.class);
        query.setParameter("id", room.getId());

        return query.getResultList();
    }
}
