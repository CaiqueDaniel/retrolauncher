package org.retrolauncher.gui.modules.games.presenters;

import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.events.EventType;
import org.retrolauncher.gui.modules.games.features.IListGamesFeature;
import org.retrolauncher.gui.modules.games.gateways.GamesGateway;
import org.retrolauncher.gui.modules.games.models.Game;

public class DefaultListGamesPresenter implements ListGamesPresenter {
    private final IListGamesFeature view;
    private final GamesGateway gateway;
    private final EventManager eventManager;

    public DefaultListGamesPresenter(IListGamesFeature view, GamesGateway gateway, EventManager eventManager) {
        this.view = view;
        this.gateway = gateway;
        this.eventManager = eventManager;
    }

    @Override
    public void listAll() {
        view.updateList(gateway.listAll());
    }

    public void selectGameItem(Game game) {
        eventManager.notify(EventType.GAME_SELECTED, game);
    }

    public void reindexGames() {
        gateway.reindexGames();
        listAll();
    }
}
