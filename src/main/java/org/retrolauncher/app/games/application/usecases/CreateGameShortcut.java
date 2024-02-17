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

public class CreateGameShortcut {
    private final GameRepository repository;
    private final ShortcutService shortcutService;

    public CreateGameShortcut(GameRepository repository, ShortcutService shortcutService) {
        this.repository = repository;
        this.shortcutService = shortcutService;
    }

    public void execute(String id) {
        Optional<Game> result = this.repository.findById(UUID.fromString(id));

        if (result.isEmpty())
            throw new GameNotFoundException();

        Game game = result.get();
        String appPath = new StringBuilder(Paths.get("").toAbsolutePath().toString())
                .append("/retrolauncher.exe")
                .toString();
        Shortcut shortcut = new Shortcut(game.getName(), appPath, game.getId().toString(), game.getIconPath());

        try {
            this.shortcutService.create(shortcut);
        } catch (IOException exception) {
            throw new ShortcutNotCreatedException(exception);
        }
    }
}
