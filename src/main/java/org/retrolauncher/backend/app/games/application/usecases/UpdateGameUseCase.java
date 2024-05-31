package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app.games.application.dtos.UpdateGameInputDto;
import org.retrolauncher.backend.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import java.util.Optional;

public class UpdateGameUseCase {
    private final GameRepository repository;

    public UpdateGameUseCase(GameRepository repository) {
        this.repository = repository;
    }

    public void execute(UpdateGameInputDto input) {
        final Optional<Game> result = this.repository.findById(input.id);

        if (result.isEmpty())
            throw new GameNotFoundException();

        final Game game = result.get();
        game.setName(input.name);

        this.repository.save(game);
    }
}
