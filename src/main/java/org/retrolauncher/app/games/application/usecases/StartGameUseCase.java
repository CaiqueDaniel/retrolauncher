package org.retrolauncher.app.games.application.usecases;

import org.retrolauncher.app._shared.application.services.EnvConfigService;
import org.retrolauncher.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.app.games.domain.entities.Game;
import org.retrolauncher.app.games.domain.repositories.GameRepository;

import java.util.Optional;
import java.util.UUID;

public class StartGameUseCase {
    private final GameRepository repository;
    private final EnvConfigService configService;

    public StartGameUseCase(GameRepository repository, EnvConfigService configService) {
        this.repository = repository;
        this.configService = configService;
    }

    public void execute(String gameId) {
        Optional<Game> game = this.repository.findById(UUID.fromString(gameId));

        if (game.isEmpty())
            throw new GameNotFoundException();

        try {
            this.startGameProcess(game.get());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void startGameProcess(Game game) throws Exception {
        new ProcessBuilder()
                .command(
                        this.configService.getRetroArchPath(),
                        "-L",
                        game.getPlatform().getCorePath(),
                        game.getPath()
                ).start()
                .waitFor();
    }
}
