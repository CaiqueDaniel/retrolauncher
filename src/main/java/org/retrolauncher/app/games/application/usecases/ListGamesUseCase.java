package org.retrolauncher.app.games.application.usecases;

import org.retrolauncher.app.games.application.dtos.GameInfoOutputDto;
import org.retrolauncher.app.games.domain.repositories.GameRepository;

import java.util.List;

public class ListGamesUseCase {
    private final GameRepository repository;

    public ListGamesUseCase(GameRepository repository) {
        this.repository = repository;
    }

    public List<GameInfoOutputDto> execute() {
        return this.repository.listAll()
                .stream()
                .map((game) -> new GameInfoOutputDto(game.getId().toString(), game.getName()))
                .toList();
    }
}
