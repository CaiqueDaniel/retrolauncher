package org.retrolauncher.gui.modules.games.presenters;

import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.events.EventType;
import org.retrolauncher.gui.modules.games.features.IListGames;
import org.retrolauncher.gui.modules.games.gateways.GamesGateway;
import org.retrolauncher.gui.modules.games.models.Game;

public class DefaultListGamesPresenter implements ListGamesPresenter {
    private final IListGames view;
    private final GamesGateway gateway;

    public DefaultListGamesPresenter(IListGames view, GamesGateway gateway) {
        this.view = view;
        this.gateway = gateway;
    }

    @Override
    public void listAll() {
        view.updateList(gateway.listAll());
    }

    public void onSelectGameItem(Game game) {
        EventManager.getInstance().notify(EventType.GAME_SELECTED, game);
    }
}
