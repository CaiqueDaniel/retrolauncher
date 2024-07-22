package org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.repositories;

import org.hibernate.SessionFactory;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.mappers.HibernatePlatformMapper;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.models.PlatformModel;

import java.util.*;

public class HibernatePlatformRepository implements PlatformRepository {
    private final SessionFactory sessionFactory;

    public HibernatePlatformRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Platform entity) {
        sessionFactory.inTransaction((session) -> {
            final var model = HibernatePlatformMapper.fromDomain(entity);

            if (session.find(PlatformModel.class, model.getId()) == null) {
                session.persist(model);
                return;
            }
            session.merge(model);
        });
    }

    @Override
    public Optional<Platform> findById(UUID id) {
        var result = sessionFactory.fromSession(session -> session.find(PlatformModel.class, id));
        if (result == null)
            return Optional.empty();
        return Optional.of(HibernatePlatformMapper.toDomain(result));
    }

    @Override
    public List<Platform> listAll() {
        var result = sessionFactory.fromSession((session) -> session
                .createSelectionQuery("FROM PlatformModel", PlatformModel.class)
                .getResultList());
        return result.stream().map(HibernatePlatformMapper::toDomain).toList();
    }

    @Override
    public boolean existsByCore(String corePath) {
        return sessionFactory.fromSession((session -> session
                .createSelectionQuery("FROM PlatformModel WHERE corePath = :corePath", PlatformModel.class)
                .setParameter("corePath", corePath)
                .getSingleResultOrNull() != null));
    }

    public void clear() {
        sessionFactory.inTransaction((session) -> {
            session.createQuery("FROM PlatformModel", PlatformModel.class)
                    .getResultList()
                    .forEach(session::remove);
        });
    }
}
