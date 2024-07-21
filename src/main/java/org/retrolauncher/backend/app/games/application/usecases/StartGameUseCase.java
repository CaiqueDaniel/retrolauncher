package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app._shared.application.services.ProcessRunnerService;
import org.retrolauncher.backend.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.backend.app.games.application.exceptions.NotAbleToStartGameException;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class StartGameUseCase {
    private final GameRepository repository;
    private final PlatformRepository platformRepository;
    private final ProcessRunnerService processRunnerService;

    public StartGameUseCase(
            GameRepository repository,
            PlatformRepository platformRepository,
            ProcessRunnerService processRunnerService
    ) {
        this.repository = repository;
        this.platformRepository = platformRepository;
        this.processRunnerService = processRunnerService;
    }

    public void execute(String gameId) {
        final var game = getGame(UUID.fromString(gameId));
        final var platform = getPlatform(game.getPlatformId());

        try {
            this.processRunnerService.startGame(game.getPath(), Path.of(platform.getCorePath())).waitFor();
        } catch (Exception exception) {
            throw new NotAbleToStartGameException(exception);
        }
    }

    private Game getGame(UUID id) {
        final var result = this.repository.findById(id);
        if (result.isEmpty())
            throw new GameNotFoundException();
        return result.get();
    }

    private Platform getPlatform(UUID id) {
        final var result = this.platformRepository.findById(id);
        if (result.isEmpty())
            throw new RuntimeException();
        return result.get();
    }
}
