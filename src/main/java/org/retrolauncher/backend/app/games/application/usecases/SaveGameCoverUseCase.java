package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app._shared.application.services.UploaderService;
import org.retrolauncher.backend.app.games.application.dtos.SaveGameCoverInputDto;
import org.retrolauncher.backend.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class SaveGameCoverUseCase {
    private final GameRepository repository;
    private final UploaderService uploaderService;

    public SaveGameCoverUseCase(GameRepository repository, UploaderService uploaderService) {
        this.repository = repository;
        this.uploaderService = uploaderService;
    }

    public void execute(SaveGameCoverInputDto input) {
        Optional<Game> result = this.repository.findById(UUID.fromString(input.id()));

        if (result.isEmpty())
            throw new GameNotFoundException();

        Game game = result.get();

        if (game.getIconPath().isPresent()) {
            this.uploaderService.upload(input.icon(), this.getIconName(game.getIconPath().get()));
            return;
        }

        game.uploadIcon(this.uploaderService.upload(input.icon()));
        this.repository.save(game);
    }

    private String getIconName(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
}
