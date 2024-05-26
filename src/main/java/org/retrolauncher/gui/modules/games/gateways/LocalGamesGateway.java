package org.retrolauncher.gui.modules.games.gateways;

import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;
import org.retrolauncher.backend.facades.GamesFacade;
import org.retrolauncher.backend.facades.GamesFacadeImpl;
import org.retrolauncher.gui.modules.games.models.Game;

import java.util.List;
import java.util.UUID;

public class LocalGamesGateway implements GamesGateway {
    private final GamesFacade facade = new GamesFacadeImpl();

    @Override
    public List<Game> listAll() {
        return facade.listAll().stream().map((item) -> new Game(
                UUID.fromString(item.id()),
                item.name(),
                item.platformName(),
                item.iconPath().orElse(null)
        )).toList();
    }

    @Override
    public void updateGame(Game game) {
        if (game.getIconPath().isPresent())
            facade.saveCover(new SaveGameCoverDto(game.getId().toString(), game.getIconPath().get().toFile()));
    }

    @Override
    public void createShortcut(Game game) {
        facade.createShortcut(game.getId().toString());
    }

    @Override
    public void reindexGames() {
        facade.updateList();
    }
}
