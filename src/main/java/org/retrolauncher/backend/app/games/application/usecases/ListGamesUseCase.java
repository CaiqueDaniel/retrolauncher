package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app.games.application.dtos.*;
import org.retrolauncher.backend.app.games.application.repositories.GameQueryRepository;

import java.util.List;

public class ListGamesUseCase {
    private final GameQueryRepository repository;

    public ListGamesUseCase(GameQueryRepository repository) {
        this.repository = repository;
    }

    public List<GameSearchResult> execute(GameSearchParams params) {
        return repository.search(params);
    }
}
