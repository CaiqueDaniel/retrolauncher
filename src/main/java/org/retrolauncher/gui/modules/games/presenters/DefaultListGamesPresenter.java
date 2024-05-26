package org.retrolauncher.gui.modules.games.presenters;

import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.events.EventType;
import org.retrolauncher.gui.modules.games.features.IListGames;
import org.retrolauncher.gui.modules.games.gateways.GamesGateway;
import org.retrolauncher.gui.modules.games.models.Game;

public class DefaultListGamesPresenter implements ListGamesPresenter {
    private final IListGames view;
    private final GamesGateway gateway;
    private final EventManager eventManager;

    public DefaultListGamesPresenter(IListGames view, GamesGateway gateway, EventManager eventManager) {
        this.view = view;
        this.gateway = gateway;
        this.eventManager = eventManager;

        this.registerListeners();
    }

    @Override
    public void listAll() {
        view.updateList(gateway.listAll());
    }

    public void onSelectGameItem(Game game) {
        eventManager.notify(EventType.GAME_SELECTED, game);
    }

    private void registerListeners() {
        eventManager.subscribe(EventType.REINDEX_GAMES_REQUESTED, (object) -> reindexGames());
    }

    private void reindexGames() {
        gateway.reindexGames();
        listAll();
    }
}
