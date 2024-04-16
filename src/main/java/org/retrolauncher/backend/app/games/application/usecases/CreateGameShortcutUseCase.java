package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app._shared.application.dtos.Shortcut;
import org.retrolauncher.backend.app._shared.application.services.ShortcutService;
import org.retrolauncher.backend.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.backend.app.games.application.exceptions.ShortcutNotCreatedException;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class CreateGameShortcutUseCase {
    private final GameRepository repository;
    private final ShortcutService shortcutService;

    public CreateGameShortcutUseCase(GameRepository repository, ShortcutService shortcutService) {
        this.repository = repository;
        this.shortcutService = shortcutService;
    }

    public void execute(String id) {
        Optional<Game> result = this.repository.findById(UUID.fromString(id));

        if (result.isEmpty())
            throw new GameNotFoundException();

        Game game = result.get();
        Shortcut shortcut = new Shortcut(game.getName(), this.getAppPath(), this.getArgs(game), this.getIconPath(game));

        try {
            this.shortcutService.create(shortcut);
        } catch (IOException exception) {
            throw new ShortcutNotCreatedException(exception);
        }
    }

    private String getArgs(Game game) {
        return new StringBuilder("game:start")
                .append(" ")
                .append(game.getId().toString())
                .toString();
    }

    private Path getAppPath() {
        return Paths.get("").resolve("retrolauncher.exe").toAbsolutePath();
    }

    private Path getIconPath(Game game) {
        Optional<Path> iconPath = game.getIconPath();

        if (iconPath.isEmpty())
            return Path.of("");

        String iconAbsolutePath = iconPath.get().toAbsolutePath().toString();
        String extension = iconAbsolutePath.substring(iconAbsolutePath.lastIndexOf('.'));

        return Path.of(iconAbsolutePath.replace(extension, ".ico"));
    }
}
