package org.retrolauncher.gui.gateways;

import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;
import org.retrolauncher.backend.facades.GamesFacade;
import org.retrolauncher.backend.facades.GamesFacadeImpl;
import org.retrolauncher.gui.models.Game;

import java.io.File;
import java.util.List;

public class GamesGateway {
    private final GamesFacade gamesFacade;

    public GamesGateway() {
        this.gamesFacade = new GamesFacadeImpl();
    }

    public List<Game> listAll() {
        return this.gamesFacade.listAll()
                .stream()
                .map((game) -> new Game(game.id(), game.name()))
                .toList();
    }

    public void saveCover(String id, File icon) {
        this.gamesFacade.saveCover(new SaveGameCoverDto(id, icon));
    }
}
