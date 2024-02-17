package org.retrolauncher.app.games.application.usecases;

import org.retrolauncher.app._shared.application.dtos.Shortcut;
import org.retrolauncher.app._shared.application.services.ShortcutService;
import org.retrolauncher.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.app.games.application.exceptions.ShortcutNotCreatedException;
import org.retrolauncher.app.games.domain.entities.Game;
import org.retrolauncher.app.games.domain.repositories.GameRepository;

import java.io.IOException;
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
        String args = this.getArgs(game);
        String appPath = this.getAppPath();
        Shortcut shortcut = new Shortcut(game.getName(), appPath, args, game.getIconPath());

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

    private String getAppPath() {
        return new StringBuilder(Paths.get("").toAbsolutePath().toString())
                .append("/retrolauncher.exe")
                .toString();
    }
}
