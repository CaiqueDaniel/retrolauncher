package org.retrolauncher.backend.app.games.domain.repositories;

import org.retrolauncher.backend.app._shared.domain.repository.Repository;
import org.retrolauncher.backend.app.games.domain.entities.Game;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface GameRepository extends Repository<Game> {
    boolean existsByPath(String path);

    Optional<Game> findOneByNameAndPlatformId(String name, UUID platformId);

    List<Game> findAllByIdsNotIn(Set<String> exceptionsIds);

    void delete(Game game);
}
