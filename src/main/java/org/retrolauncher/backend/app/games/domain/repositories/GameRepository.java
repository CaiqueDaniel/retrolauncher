package org.retrolauncher.backend.app.games.domain.repositories;

import org.retrolauncher.backend.app._shared.domain.repository.Repository;
import org.retrolauncher.backend.app.games.domain.entities.Game;

public interface GameRepository extends Repository<Game> {
    boolean existsByPath(String path);
}
