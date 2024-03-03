package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app._shared.application.services.ProcessRunnerService;
import org.retrolauncher.backend.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.backend.app.games.application.exceptions.NotAbleToStartGameException;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;

import java.util.Optional;
import java.util.UUID;

public class StartGameUseCase {
    private final GameRepository repository;
    private final ProcessRunnerService processRunnerService;

    public StartGameUseCase(GameRepository repository, ProcessRunnerService processRunnerService) {
        this.repository = repository;
        this.processRunnerService = processRunnerService;
    }

    public void execute(String gameId) {
        Optional<Game> result = this.repository.findById(UUID.fromString(gameId));

        if (result.isEmpty())
            throw new GameNotFoundException();

        try {
            this.processRunnerService.startGame(result.get()).waitFor();
        } catch (Exception exception) {
            throw new NotAbleToStartGameException(exception);
        }
    }
}
