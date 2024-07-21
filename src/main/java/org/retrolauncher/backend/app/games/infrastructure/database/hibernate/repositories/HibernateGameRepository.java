package org.retrolauncher.backend.app.games.infrastructure.database.hibernate.repositories;

import jakarta.persistence.*;
import org.hibernate.SessionFactory;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.mappers.HibernateGameMapper;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.models.GameModel;

import java.util.*;

public class HibernateGameRepository implements GameRepository {
    private final SessionFactory sessionFactory;

    public HibernateGameRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Game entity) {
        sessionFactory.inTransaction((session) -> session.persist(HibernateGameMapper.fromDomain(entity)));
    }

    @Override
    public Optional<Game> findById(UUID id) {
        final var result = sessionFactory.fromSession(session -> session.find(GameModel.class, id));
        if (result == null)
            return Optional.empty();
        return Optional.of(HibernateGameMapper.toDomain(result));
    }

    @Override
    public List<Game> listAll() {
        TypedQuery<GameModel> query =
                sessionFactory.createEntityManager().createQuery("FROM GameModel", GameModel.class);
        return query.getResultList().stream().map(HibernateGameMapper::toDomain).toList();
    }

    @Override
    public boolean existsByPath(String path) {
        return sessionFactory.fromSession((session) -> {
            return session.createQuery("FROM GameModel WHERE path = :path", GameModel.class)
                    .setParameter("path", path)
                    .setMaxResults(1)
                    .getSingleResultOrNull() != null;
        });
    }

    @Override
    public Optional<Game> findOneByNameAndPlatformId(String name, UUID platformId) {
        var result = sessionFactory.fromSession((session) -> {
            return session.createQuery("FROM GameModel WHERE name = :name AND platformId = :platformId", GameModel.class)
                    .setParameter("name", name)
                    .setParameter("platformId", platformId.toString())
                    .getSingleResultOrNull();
        });
        if (result == null) return Optional.empty();
        return Optional.of(HibernateGameMapper.toDomain(result));
    }

    @Override
    public List<Game> findAllByIdsNotIn(Set<String> exceptionsIds) {
        var result = sessionFactory.fromSession((session) -> {
            return session.createQuery("FROM GameModel WHERE id NOT IN (:exceptionsIds)", GameModel.class)
                    .setParameterList("exceptionsIds", exceptionsIds.toArray())
                    .getResultList();
        });
        return result.stream().map(HibernateGameMapper::toDomain).toList();
    }

    @Override
    public void delete(Game game) {
        sessionFactory.inTransaction((session) -> session.remove(HibernateGameMapper.fromDomain(game)));
    }

    public void clear() {
        sessionFactory.inTransaction((session) -> {
            session.createQuery("FROM GameModel", GameModel.class)
                    .getResultList()
                    .forEach(session::remove);
        });
    }
}
