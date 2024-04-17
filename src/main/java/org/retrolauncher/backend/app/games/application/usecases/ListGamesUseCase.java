package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app.games.application.dtos.ListGamesUseCaseOutput;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;

import java.util.Comparator;
import java.util.List;

public class ListGamesUseCase {
    private final GameRepository repository;

    public ListGamesUseCase(GameRepository repository) {
        this.repository = repository;
    }

    public List<ListGamesUseCaseOutput> execute() {
        return this.repository.listAll()
                .stream()
                .map((game) -> new ListGamesUseCaseOutput(
                        game.getId().toString(),
                        game.getName(),
                        game.getPlatform().getName(),
                        game.getIconPath().orElse(null)
                )).sorted(Comparator.comparing(ListGamesUseCaseOutput::name))
                .toList();
    }
}
