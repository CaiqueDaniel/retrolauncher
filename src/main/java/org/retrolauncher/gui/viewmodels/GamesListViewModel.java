package org.retrolauncher.gui.viewmodels;

import org.retrolauncher.gui.gateways.GamesGateway;
import org.retrolauncher.gui.models.Game;

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
