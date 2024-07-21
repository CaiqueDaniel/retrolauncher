package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app.games.application.dtos.GameSearchResult;
import org.retrolauncher.backend.app.games.application.repositories.GameQueryRepository;

import java.util.List;

public class ListGamesUseCase {
    private final GameQueryRepository repository;

    public ListGamesUseCase(GameQueryRepository repository) {
        this.repository = repository;
    }

    public List<GameSearchResult> execute() {
        return repository.search();
    }
}
