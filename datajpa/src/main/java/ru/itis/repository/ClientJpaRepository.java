package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.model.Client;

@Repository
@Transactional
public interface ClientJpaRepository extends JpaRepository<Client, Long> {
}
