package org.retrolauncher.gui.gateways;

import org.retrolauncher.backend.facades.GamesFacade;
import org.retrolauncher.backend.facades.GamesFacadeImpl;
import org.retrolauncher.gui.models.Game;

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
}
