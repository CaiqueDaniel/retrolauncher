package org.retrolauncher.gui.modules.games.viewmodels;

import org.retrolauncher.gui.modules.games.gateways.GamesGateway;
import org.retrolauncher.gui.modules.games.models.Game;

import java.util.List;

public class GamesListViewModel {
    private final GamesGateway gateway;

    public GamesListViewModel() {
        this.gateway = new GamesGateway();
    }

    public List<Game> listAll() {
        return this.gateway.listAll();
    }
}
