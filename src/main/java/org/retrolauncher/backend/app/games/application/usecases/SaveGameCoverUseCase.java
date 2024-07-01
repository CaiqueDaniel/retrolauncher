package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app._shared.application.services.FileSystemService;
import org.retrolauncher.backend.app.games.application.dtos.SaveGameCoverInputDto;
import org.retrolauncher.backend.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class SaveGameCoverUseCase {
    private final GameRepository repository;
    private final FileSystemService fileSystemService;

    public SaveGameCoverUseCase(GameRepository repository, FileSystemService fileSystemService) {
        this.repository = repository;
        this.fileSystemService = fileSystemService;
    }

    public void execute(SaveGameCoverInputDto input) {
        final Optional<Game> result = this.repository.findById(UUID.fromString(input.id()));

        if (result.isEmpty())
            throw new GameNotFoundException();

        final Game game = result.get();
        final Optional<Path> previousCover = game.getIconPath();
        final Path uploadedCover = this.fileSystemService.upload(input.icon());

        game.uploadIcon(uploadedCover);
        this.repository.save(game);
        previousCover.ifPresent(this.fileSystemService::remove);
    }
}
