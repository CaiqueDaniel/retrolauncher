package org.retrolauncher.backend.app.games.infrastructure.database.hibernate.repositories;

import org.hibernate.SessionFactory;
import org.retrolauncher.backend.app.games.application.dtos.*;
import org.retrolauncher.backend.app.games.application.repositories.GameQueryRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.models.GameModel;

import java.util.List;

public class HibernateGameQueryRepository implements GameQueryRepository {
    private final SessionFactory sessionFactory;

    public HibernateGameQueryRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<GameSearchResult> search(GameSearchParams params) {
        return sessionFactory.fromTransaction(session -> {
            var criteriaBuilder = session.getCriteriaBuilder();
            var queryBuilder = criteriaBuilder.createQuery(GameSearchResult.class);
            var gameRoot = queryBuilder.from(GameModel.class);
            var platformJoin = gameRoot.join("platform");

            queryBuilder.select(criteriaBuilder.construct(
                    GameSearchResult.class,
                    gameRoot.get("id"),
                    gameRoot.get("name"),
                    platformJoin.get("name"),
                    gameRoot.get("iconPath")
            ));

            if (!params.name().isEmpty()) {
                queryBuilder.where(criteriaBuilder.like(
                        criteriaBuilder.lower(gameRoot.get("name")),
                        "%" + params.name().toLowerCase() + "%"
                ));
            }

            return session.createQuery(queryBuilder).getResultList();
        });
    }
}
